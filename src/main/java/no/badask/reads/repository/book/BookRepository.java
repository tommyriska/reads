package no.badask.reads.repository.book;

import no.badask.reads.models.Book;
import no.badask.reads.models.CreateBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BookRepository {

    private Logger log = LoggerFactory.getLogger(getClass());

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Retryable(include = {DataAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1500))
    public List<Book> getAllBooks() {
        // TODO: Add some pagination functionality here
        log.debug("Fetching all books");
        String sqlQuery = "SELECT id, title, subtitle, description, pageCount, publisher, publishedDate, isbn, isbn13," +
                " smallThumbnail, thumbnail, dateAdded, addedByUserId FROM Book";

        long start = System.currentTimeMillis();
        List<Book> results = jdbcTemplate.query(sqlQuery, new BooksResultSetExtractor());
        logDuration("getAllBooks", System.currentTimeMillis() - start, "all books");

        return results;
    }

    private void logDuration(String method, Long duration, String query) {
        log.debug("{} took {}ms searching for {}", method, duration, query);
    }

    @Retryable(include = {DataAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1500))
    public Optional<Book> getBookById(String id) {
        log.debug("Searching for book by id {}", id);
        String sqlQuery = "SELECT id, title, subtitle, description, pageCount, publisher, publishedDate, isbn, isbn13," +
                " smallThumbnail, thumbnail, dateAdded, addedByUserId FROM Book WHERE id = :id";

        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);

        long start = System.currentTimeMillis();

        List<Book> results = jdbcTemplate.query(sqlQuery, paramSource, new BooksResultSetExtractor());

        logDuration("getBookById", System.currentTimeMillis() - start, id);
        if (results.size() > 1) {
            log.debug("Found {}, but expected 1. Returning first element in set", results.size());
        }

        return Optional.ofNullable(results.get(0));
    }

    @Retryable(include = {DataAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1500))
    public List<Book> searchBooksByTitle(String title) {
        log.debug("Searching for book by title {}", title);
        String sqlQuery = "SELECT id, title, subtitle, description, pageCount, publisher, publishedDate, isbn, isbn13," +
                " smallThumbnail, thumbnail, dateAdded, addedByUserId FROM Book WHERE MATCH(title) against(:title in boolean mode) LIMIT 25";

        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("title", title + "*");

        long start = System.currentTimeMillis();
        List<Book> results = jdbcTemplate.query(sqlQuery, paramSource, new BooksResultSetExtractor());
        logDuration("searchBooksByTitle", System.currentTimeMillis() - start, title);

        return results;
    }

    @Retryable(include = {DataAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1500))
    public Book createBook(CreateBook book, String userId) {
        log.debug("Adding new book:\n{}", book);

        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("uuid", UUID.randomUUID().toString());
        paramSource.addValue("title", book.getTitle());
        paramSource.addValue("subtitle", book.getSubtitle());
        paramSource.addValue("description", book.getDescription());
        paramSource.addValue("pageCount", book.getPageCount());
        paramSource.addValue("publisher", book.getPublisher());
        paramSource.addValue("publishedDate", book.getPublishedDate());
        paramSource.addValue("isbn", book.getIsbn());
        paramSource.addValue("isbn13", book.getIsbn13());
        paramSource.addValue("smallThumbnail", book.getSmallThumbnail());
        paramSource.addValue("thumbnail", book.getThumbnail());
        paramSource.addValue("dateAdded", Date.valueOf(LocalDate.now()));
        paramSource.addValue("addedByUserId", userId);

        String query = new StringBuilder("INSERT INTO Book (id, title, subtitle, description, pageCount," +
                " publisher, publishedDate, isbn, isbn13, smallThumbnail, thumbnail, dateAdded, addedByUserId)" +
                " VALUES (:uuid, :title, :subtitle, :description, :pageCount, :publisher, :publishedDate, :isbn," +
                " :isbn13, :smallThumbnail, :thumbnail, :dateAdded, :addedByUserId)").toString();
        KeyHolder kh = new GeneratedKeyHolder();
        int updated = jdbcTemplate.update(query, paramSource, kh);

        if(updated > 0) {
            return getBookById(paramSource.getValue("uuid").toString()).orElse(null);
        }

        log.error("Failed to create book");
        throw new WebApplicationException(Response.serverError().build());
    }

    @Retryable(include = {DataAccessException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1500))
    public void deleteBook(String id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);
        String query = "DELETE FROM Book WHERE id = :id";
        int updated = jdbcTemplate.update(query, paramSource);
        if(updated < 1) {
            // Something went wrong while deleting the record
            log.error("Could not delete book with id {} from database", id);
            throw new WebApplicationException(Response.serverError().build());
        }
    }


}
