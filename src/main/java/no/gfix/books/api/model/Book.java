package no.gfix.books.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "books")
@Data
@Builder
@Setter
@JsonDeserialize(builder = Book.BookBuilder.class)
public class Book {
    @Id
    private final String id;
    private final String title;
    @DBRef
    private final Author author;
    private final String description;
    private LocalDateTime created;

    public Book withAuthor(Author author) {
        return new Book(
                this.getId(),
                this.getTitle(),
                author,
                this.getDescription(),
                this.getCreated());
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class BookBuilder {
    }
}
