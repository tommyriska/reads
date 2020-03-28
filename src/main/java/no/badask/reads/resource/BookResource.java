package no.badask.reads.resource;

import no.badask.reads.models.Book;
import no.badask.reads.models.CreateBook;
import no.badask.reads.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Service
@Path("books")
@Produces(MediaType.APPLICATION_JSON)
// TODO: Add swagger documentation to API
public class BookResource {

    private BookService bookService;

    @Autowired
    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    // Search for books. Returns all books if no query param is used
    @GET
    public Response searchBook(@QueryParam("title") String title) {
        if (StringUtils.isNotBlank(title)) {
            return Response.ok(bookService.searchBookByTitle(title)).build();
        } else {
            return Response.ok(bookService.getAllBooks()).build();
        }
    }

    // Search for a book by bookId
    @GET
    @Path("{id}")
    public Response searchBookById(@PathParam("id") String bookId) {
        return bookService.searchBookById(bookId)
                .map(b -> Response.ok(b).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    // Add a new book to the database
    @POST
    //FIXME: Fix user userId here
    public Response addBook(@Valid CreateBook createBook) {
        String tempUserId = "google-oauth2|testuserid";
        Book book = bookService.createBook(createBook, tempUserId);
        if (book != null) {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();
            return Response.created(uri).entity(book).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // Delete a book from the database
    @DELETE
    @Path("{id}")
    public Response deleteBook(@PathParam("id") String bookId) {
        bookService.deleteBookById(bookId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
