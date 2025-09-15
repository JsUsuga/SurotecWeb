package model.domain.news;

import model.domain.classification.NewsCategory;

public class News {
    private Long id;
    private Long authorId;
    private String title;
    private NewsCategory category;

    public News(Long id, Long authorId, String title, NewsCategory category) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.category = category;
    }

    public News(long id, long authorId, String title, model.domain.news.NewsCategory category) {
    }

    // Getters & setters
    public Long getId() { return id; }
    public Long getAuthorId() { return authorId; }
    public String getTitle() { return title; }
    public NewsCategory getCategory() { return category; }

    public void setId(Long id) { this.id = id; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    public void setTitle(String title) { this.title = title; }
    public void setCategory(NewsCategory category) { this.category = category; }

    @Override
    public String toString() {
        return "News{id=" + id +
                ", authorId=" + authorId +
                ", title='" + title + '\'' +
                ", category=" + category + '}';
    }
}

