package com.mjc.school.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AuthorRequestDTO {
    @NotNull(message = "The id of the author is required")
    @Positive(message = "The id of the author must be a positive value")
    private Long id;

    @NotEmpty
    @Size(min = 5, message = "The name of the author must be at least 5 characters")
    @Size(max = 15, message = "The name of the author must be no more than 15 characters")
    private String name;

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof AuthorRequestDTO other)) {
            return false;
        }

        return Objects.equals(this.name, other.name)
                && Objects.equals(this.id, other.id);
    }

    @JsonCreator
    public AuthorRequestDTO(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name
    ) {
        this.id = id;
        this.name = name;
    }
}
