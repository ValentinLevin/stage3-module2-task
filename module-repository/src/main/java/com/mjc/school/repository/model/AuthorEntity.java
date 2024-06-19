package com.mjc.school.repository.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class AuthorEntity implements BaseEntity<Long> {
    @NotNull(message = "Id is required")
    @Positive(message = "The id value must be a positive value")
    private Long id;

    @NotEmpty(message = "Author's name is required")
    @Size(min = 3, message = "The author's name must be at least 3 characters")
    @Size(max = 15, message = "The author's name must be no more than 15 characters")
    private String name;

    public AuthorEntity() {

    }

    public AuthorEntity(String name) {
        this.setId(0L);
        this.name = name;
    }

    @JsonCreator()
    public AuthorEntity(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name
    ) {
        this.setId(id);
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof AuthorEntity a)) {
            return false;
        }

        return Objects.equals(this.getId(), a.getId())
                && Objects.equals(this.getName(), a.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), name);
    }

    @Override
    public AuthorEntity copy() {
        return new AuthorEntity(this.getId(), this.getName());
    }
}
