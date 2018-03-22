package no.gfix.books.api;

import no.gfix.books.api.model.Book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.core.publisher.Hooks;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootApplication
public class BooksApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksApiApplication.class, args);
        Hooks.onOperatorDebug();
    }

    @Bean
    RouterFunction routes(BookService bookService) {
        return RouterFunctions
                .route(RequestPredicates.GET("/books"), request -> ok().body(bookService.all(), Book.class))
                .andRoute(RequestPredicates.GET("/books/{id}"), request -> ok().body(bookService.find(request.pathVariable("id")), Book.class))
                .andRoute(RequestPredicates.POST("/books"), request -> ok().body(request.bodyToMono(Book.class).flatMap(bookService::save), Book.class))
                .andRoute(RequestPredicates.DELETE("/books/{id}"), request -> ok().body(bookService.delete(request.pathVariable("id")), Void.class));
//                .andRoute(RequestPredicates.GET("/new/books"), request -> ok().contentType(MediaType.TEXT_EVENT_STREAM).body(bookService.fluxOfNewBooks(), Book.class));
    }
}
