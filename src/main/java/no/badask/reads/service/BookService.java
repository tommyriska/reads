package no.badask.reads.service;

import no.badask.reads.models.Book;
import no.badask.reads.models.CreateBook;
import no.badask.reads.repository.book.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private Logger log = LoggerFactory.getLogger(getClass());

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        try {
            log.debug("Fetching all books");
            return bookRepository.getAllBooks();
        } catch (DataAccessException ex) {
            log.error("{}", ex.getMessage());
            throw new WebApplicationException(Response.serverError().build());
        }
    }

    public Optional<Book> searchBookById(String id) {
        try {
            log.debug("Searching for book with id {}", id);
            return bookRepository.getBookById(id);
        } catch (RuntimeException ex) {
            log.error("{}", ex.getMessage());
            throw new WebApplicationException(Response.serverError().build());
        }
    }

    public List<Book> searchBookByTitle(String title) {
        try {
            log.debug("Searching for book with title {}", title);
            return bookRepository.searchBooksByTitle(title);
        } catch (RuntimeException ex) {
            log.error("{}", ex.getMessage());
            throw new WebApplicationException(Response.serverError().build());
        }
    }

    public Book createBook(CreateBook createBook, String userId) {
        try {
            log.debug("Creating new book");
            return bookRepository.createBook(createBook, userId);
        } catch (RuntimeException ex) {
            log.error("{}", ex.getMessage());
            throw new WebApplicationException(Response.serverError().build());
        }
    }

    public void deleteBookById(String id) {
        try {
            log.debug("Deleting book with id {}", id);
            bookRepository.deleteBook(id);
        } catch (RuntimeException ex) {
            log.error("{}", ex.getMessage());
            throw new WebApplicationException(Response.serverError().build());
        }
    }
}
