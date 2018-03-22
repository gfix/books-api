package no.gfix.books.api;

import lombok.RequiredArgsConstructor;
import no.gfix.books.api.model.Author;
import no.gfix.books.api.model.Book;
import org.springframework.stereotype.Service;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final DirectProcessor<Book> bookProcessor = DirectProcessor.create();
    private FluxSink<Book> bookSink = null;

    public Mono<Book> find(String id) {
        return bookRepository.findById(id);
    }

    public Flux<Book> all() {
        return bookRepository.findAll();
    }

    public Mono<Book> save(Book book) {
        book.setCreated(LocalDateTime.now());

        if (bookSink == null) {
            bookSink = bookProcessor.sink();
        }

        if (book.getAuthor() != null) {
            Mono<Author> authorMono = book.getAuthor().getId() != null ? authorService.find(book.getAuthor().getId()) : authorService.save(book.getAuthor());
            return authorMono.map(book::withAuthor).flatMap(bookRepository::save).doOnNext(bookSink::next);
        }

        return bookRepository.save(book).doOnNext(bookSink::next);
    }

    public Mono<Void> delete(String id) {
        return bookRepository.deleteById(id);
    }

    public Flux<Book> fluxOfNewBooks() {
        return bookProcessor;
    }
}
