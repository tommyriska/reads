package no.badask.reads.repository.book;

import no.badask.reads.models.Book;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BooksResultSetExtractor implements ResultSetExtractor<List<Book>> {
    @Override
    public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getString("id"));
            book.setTitle(rs.getString("title"));
            book.setSubtitle(rs.getString("subtitle"));
            book.setDescription(rs.getString("description"));
            book.setPageCount(rs.getInt("pageCount"));
            book.setPublisher(rs.getString("publisher"));
            book.setPublishedDate(rs.getDate("publishedDate"));
            book.setIsbn(rs.getString("isbn"));
            book.setIsbn13(rs.getString("isbn13"));
            book.setSmallThumbnail(rs.getString("smallThumbnail"));
            book.setThumbnail(rs.getString("thumbnail"));
            book.setDateAdded(rs.getDate("dateAdded"));
            book.setAddedByUserId(rs.getString("addedByUserId"));
            books.add(book);
        }
        return books;
    }
}
