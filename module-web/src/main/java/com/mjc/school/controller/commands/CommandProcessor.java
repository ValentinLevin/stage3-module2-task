package com.mjc.school.controller.commands;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.commands.annotations.CommandHandler;
import com.mjc.school.controller.commands.command.Command;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommandProcessor {
    private final ApplicationContext applicationContext;
    private final Map<String, Map<String, Method>> commandHandlers = new HashMap<>();

    public CommandProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        loadCommandHandlers();
    }

    private void loadCommandHandlers() {
        String[] beanNames = applicationContext.getBeanNamesForType(BaseController.class);
        for (String beanName: beanNames) {
            Object bean = applicationContext.getBean(beanName);
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method: methods) {
                if (method.isAnnotationPresent(CommandHandler.class)) {
                    CommandHandler commandHandlerAnnotation = method.getAnnotation(CommandHandler.class);

                    Map<String, Method> entityCommandHandlers = commandHandlers.get(commandHandlerAnnotation.entity());
                    if (entityCommandHandlers == null) {
                        entityCommandHandlers = new HashMap<>();
                        commandHandlers.put(commandHandlerAnnotation.entity(), entityCommandHandlers);
                    }

                    entityCommandHandlers.put(commandHandlerAnnotation.command(), method);
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    public <R> void processCommand(Command<R> command) throws InvocationTargetException, IllegalAccessException {
        Map<String, Method> entityCommandHandlers = commandHandlers.get(command.getEntity());
        if (entityCommandHandlers == null) {
            throw new RuntimeException("Not found handlers for entity ".concat(command.getEntity()));
        }

        Method method = entityCommandHandlers.get(command.getCommandName());

        if (method == null) {
            throw new RuntimeException("Not found method for commandName ".concat(command.getCommandName()));
        }

        Object bean = applicationContext.getBean(method.getDeclaringClass());

        R result = (R) method.invoke(bean, command.getArgs());

        command.setResult(result);
    }
}
