package service.news;

import model.domain.news.News;
import java.util.List;

public interface NewsService {
    void create(News news);          // Crear noticia
    List<News> readAll();            // Consultar todas
    News readById(Long id);          // Consultar por id
}

