package repository.news;

import model.domain.news.News;
import model.domain.news.NewsCategory;
import java.util.List;

public interface NewsRepository  {
    void create(News news);
    List<News> readAll();
    News readById(Long id);
    List<News> readByCategory(NewsCategory cat);

    List<News> getNewsByCategory(NewsCategory category);
}