package no.gfix.books.api;

import lombok.RequiredArgsConstructor;
import no.gfix.books.api.model.Author;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;

    public Mono<Author> find(String id) {
        return repository.findById(id);
    }

    public Mono<Author> save(Author author) {
        return repository.save(author);
    }

    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }

}
