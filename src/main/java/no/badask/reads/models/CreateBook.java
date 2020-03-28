package no.badask.reads.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class CreateBook {
    @NotEmpty(message = "title cannot be null")
    private String title;
    @NotEmpty(message = "subtitle cannot be null")
    private String subtitle;
    @NotEmpty(message = "description cannot be null")
    private String description;
    @NotNull(message = "pageCount cannot be null")
    private Integer pageCount;
    @NotEmpty(message = "publisher cannot be null")
    private String publisher;
    @NotNull(message = "publishedDate cannot be null")
    private Date publishedDate;
    @NotEmpty(message = "isbn cannot be null")
    private String isbn;
    @NotEmpty(message = "isbn13 cannot be null")
    private String isbn13;
    @NotEmpty(message = "smallThumbnail cannot be null")
    private String smallThumbnail;
    @NotEmpty(message = "thumbnail cannot be null")
    private String thumbnail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "CreateBook{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", description='" + description + '\'' +
                ", pageCount=" + pageCount +
                ", publisher='" + publisher + '\'' +
                ", publishedDate=" + publishedDate +
                ", isbn='" + isbn + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", smallThumbnail='" + smallThumbnail + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
