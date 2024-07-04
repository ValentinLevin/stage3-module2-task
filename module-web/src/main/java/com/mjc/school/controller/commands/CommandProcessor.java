package com.mjc.school.controller.commands;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.commands.annotations.CommandHandler;
import com.mjc.school.controller.commands.command.Command;
import com.mjc.school.controller.commands.constant.ACTIONS;
import com.mjc.school.controller.commands.constant.ENTITIES;
import com.mjc.school.controller.commands.constant.RESULT_CODE;
import com.mjc.school.controller.exception.NotFoundHandlerForCommandWebException;
import com.mjc.school.controller.exception.NotFoundHandlerForEntityWebException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
public class CommandProcessor {
    private final ApplicationContext applicationContext;
    private final Map<ENTITIES, Map<ACTIONS, Method>> commandHandlers = new EnumMap<>(ENTITIES.class);

    public CommandProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        loadCommandHandlers();
    }

    private void loadCommandHandlers() {
        String[] beanNames = applicationContext.getBeanNamesForType(BaseController.class);
        for (String beanName : beanNames) {
            Object bean = applicationContext.getBean(beanName);
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(CommandHandler.class)) {
                    CommandHandler commandHandlerAnnotation = method.getAnnotation(CommandHandler.class);

                    Map<ACTIONS, Method> entityCommandHandlers =
                            commandHandlers.computeIfAbsent(commandHandlerAnnotation.entity(), k -> {
                                Map<ACTIONS, Method> map = new EnumMap<>(ACTIONS.class);
                                commandHandlers.put(k, map);
                                return map;
                            });

                    entityCommandHandlers.put(commandHandlerAnnotation.command(), method);
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    public <R> void processCommand(Command<R> command) throws InvocationTargetException, IllegalAccessException {
        Map<ACTIONS, Method> entityCommandHandlers = commandHandlers.get(command.getEntity());
        if (entityCommandHandlers == null) {
            throw new NotFoundHandlerForEntityWebException(
                    RESULT_CODE.NOT_FOUND_HANDLER_FOR_ENTITY,
                    command.getEntity().getName()
            );
        }

        Method method = entityCommandHandlers.get(command.getAction());

        if (method == null) {
            throw new NotFoundHandlerForCommandWebException(
                    RESULT_CODE.NOT_FOUND_HANDLER_FOR_ACTION,
                    command.getEntity().getName(),
                    command.getAction().getName()
            );
        }

        Object bean = applicationContext.getBean(method.getDeclaringClass());

        R result = (R) method.invoke(bean, command.getArgs());
        command.setResult(result);
    }
}
