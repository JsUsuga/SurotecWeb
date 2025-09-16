package model.domain.news;

import java.util.Date;

public class News {
    private Long id;
    private Long authorId;
    private String title;
    private String content;
    private Date publishedDate;
    private NewsCategory category;

    // Constructor completo
    public News(Long id, Long authorId, String title, String content, Date publishedDate, NewsCategory category) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.publishedDate = publishedDate;
        this.category = category;
    }

    // Constructor vacío (opcional, útil para frameworks como Hibernate/JPA)
    public News() {}

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getPublishedDate() { return publishedDate; }
    public void setPublishedDate(Date publishedDate) { this.publishedDate = publishedDate; }

    public NewsCategory getCategory() { return category; }
    public void setCategory(NewsCategory category) { this.category = category; }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publishedDate=" + publishedDate +
                ", category=" + category +
                '}';
    }
}

