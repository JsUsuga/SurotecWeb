package service.news;

import model.domain.news.News;
import model.domain.news.NewsCategory;
import repository.news.NewsRepository;
import repository.news.NewsRepositoryImpl;

import java.util.List;

public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    public NewsServiceImpl() {
        this.newsRepository = new NewsRepositoryImpl(); // se conecta al repositorio
    }

    @Override
    public void create(News news) {
        if (news.getTitle() == null || news.getTitle().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (news.getContent() == null || news.getContent().isEmpty()) {
            throw new IllegalArgumentException("El contenido no puede estar vacío");
        }
        newsRepository.create(news);
    }

    @Override
    public List<News> readAll() {
        return newsRepository.readAll();
    }

    @Override
    public News readById(Long id) {
        return newsRepository.readById(id);
    }

    public void createNews(News news) {
    }

    public List<News> getAllNews() {
        return List.of();
    }

    public List<News> getNewsByCategory(NewsCategory category) {
        return newsRepository.getNewsByCategory(category);
    }
}

