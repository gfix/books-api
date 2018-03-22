package no.gfix.books.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "authors")
@Value
@Builder
@JsonDeserialize(builder = Author.AuthorBuilder.class)
public class Author {
    @Id
    private final String id;
    private final String name;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class AuthorBuilder {

    }
}
