package com.mjc.school.repository.datasource;

import com.mjc.school.repository.datasource.impl.AuthorDataSource;
import com.mjc.school.repository.exception.CustomRepositoryException;
import com.mjc.school.repository.model.AuthorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@DisplayName("AuthorDataSourceTest")
class AuthorDataSourceTest {
    private AuthorDataSource dataSource;

    private AuthorEntity findRandomAuthor() {
        Random random = new Random(System.currentTimeMillis());
        List<AuthorEntity> authors = dataSource.readAll(random.nextLong(dataSource.count()), 1L);
        return authors.get(0);
    }

    @BeforeEach()
    void testSetup() {
        this.dataSource = new AuthorDataSource();
    }

    @Test
    @DisplayName("The data from the file was read correctly")
    void readDataFromFile_wasReadThreeAuthorEntities() {
        List<AuthorEntity> actualAuthors = dataSource.readAll();
        assertThat(actualAuthors)
                .extracting("id", "name")
                .containsOnly(
                        tuple(1L, "Author 1 name"),
                        tuple(2L, "Author 2 name"),
                        tuple(3L, "Author 3 name")
                );
    }

    @Test
    @DisplayName("When deleting one entity, only one entity was deleted")
    void delete_EntityHasBeenRemovedFromDataSource_true() throws CustomRepositoryException {
        long expectedAuthorCountAfterDelete = dataSource.count() - 1;

        AuthorEntity authorToDelete = findRandomAuthor();
        dataSource.deleteById(authorToDelete.getId());

        assertThat(dataSource.count()).isEqualTo(expectedAuthorCountAfterDelete);
        assertThat(dataSource.readById(authorToDelete.getId())).isEmpty();
    }

    @Test
    @DisplayName("After adding a new entity, the number of records increased by 1")
    void save_add_NumberOfRecordIncreasedByOneAfterAddingTheEntity_true() throws CustomRepositoryException {
        long expectedAuthorCount = dataSource.count() + 1;

        AuthorEntity newAuthorEntity = new AuthorEntity("New Author");
        dataSource.create(newAuthorEntity);

        assertThat(dataSource.count()).isEqualTo(expectedAuthorCount);
    }

    @Test
    @DisplayName("After adding a new entity, the author's data obtained from the dataSource is equal to the added data")
    void save_add_newAuthorHasBeenAddedIntoDataSource() throws CustomRepositoryException {
        AuthorEntity newAuthorEntity = new AuthorEntity("New Author");
        AuthorEntity addedAuthorEntity = dataSource.create(newAuthorEntity);
        Optional<AuthorEntity> newAuthorEntityDataReceivedFromDataSource = dataSource.readById(addedAuthorEntity.getId());

        assertThat(newAuthorEntityDataReceivedFromDataSource)
                .isPresent()
                .usingRecursiveComparison()
                .isEqualTo(addedAuthorEntity);

        assertThat(newAuthorEntity.getName())
                .isEqualTo(newAuthorEntityDataReceivedFromDataSource.get().getName());
    }

    @Test
    @DisplayName("When changing the data of an entity issued by a dataSource, the entity data in the dataSource does not change")
    void save_update_OnSavingAndFetchingNewInstanceOfEntityCreatedNotDependOfEachOther_true() throws CustomRepositoryException {
        AuthorEntity authorForCheck = findRandomAuthor();

        AuthorEntity firstFetchEntity = dataSource.readById(authorForCheck.getId()).get();
        AuthorEntity secondFetchEntity_ForChange = dataSource.readById(authorForCheck.getId()).get();

        secondFetchEntity_ForChange.setName("Change author name");

        AuthorEntity actualEntity = dataSource.readById(authorForCheck.getId()).get();

        assertThat(actualEntity)
                .isNotSameAs(firstFetchEntity)
                .isEqualTo(firstFetchEntity)

                .isNotSameAs(secondFetchEntity_ForChange)
                .isNotEqualTo(secondFetchEntity_ForChange);
    }

    @Test
    @DisplayName("After saving the changes to the entity in the dataSource, the data is returned changed when the request is repeated")
    void save_update_savedEntityIsEqualsToFetchedEntity_true() throws CustomRepositoryException {
        AuthorEntity authorForFetch = findRandomAuthor();
        AuthorEntity expectedAuthorEntity = dataSource.readById(authorForFetch.getId()).get();

        expectedAuthorEntity.setName("Changed author name");

        dataSource.update(expectedAuthorEntity);

        Optional<AuthorEntity> actualAuthorEntity = dataSource.readById(expectedAuthorEntity.getId());

        assertThat(actualAuthorEntity)
                .isPresent()
                .get()
                .isNotSameAs(expectedAuthorEntity)
                .isEqualTo(expectedAuthorEntity);
    }

    @Test
    @DisplayName("If you try to search by a non-existent id, null will be returned")
    void findById_notFound_nullAsResult() throws CustomRepositoryException {
        assertThat(dataSource.readById(-1L)).isEmpty();
    }

    @Test
    @DisplayName("If you try to search for an existing id, an entity with the corresponding id will be returned")
    void findById_found() throws CustomRepositoryException {
        AuthorEntity authorForFetch = findRandomAuthor();
        Optional<AuthorEntity> authorEntity = dataSource.readById(authorForFetch.getId());
        assertThat(authorEntity)
                .isPresent()
                .get()
                .extracting("id")
                .isEqualTo(authorForFetch.getId());
    }

    @Test
    @DisplayName("When checking for the presence of an entity by an existing id, it will return true")
    void existsById_exists_true() throws CustomRepositoryException {
        AuthorEntity authorForCheck = findRandomAuthor();
        assertThat(dataSource.existsById(authorForCheck.getId())).isTrue();
    }
}
