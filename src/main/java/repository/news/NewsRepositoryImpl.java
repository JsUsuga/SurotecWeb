package repository.news;

import model.domain.news.News;
import model.domain.news.NewsCategory;
import repository.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsRepositoryImpl implements NewsRepository {

    @Override
    public void create(News news) {
        String sql = "INSERT INTO news (id, author_id, title, category) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, news.getId());
            stmt.setLong(2, news.getAuthorId());
            stmt.setString(3, news.getTitle());
            stmt.setString(4, news.getCategory().name()); // enum como String

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<News> readAll() {
        List<News> newsList = new ArrayList<>();
        String sql = "SELECT * FROM news";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                News news = new News(
                        rs.getLong("id"),
                        rs.getLong("author_id"),
                        rs.getString("title"),
                        NewsCategory.valueOf(rs.getString("category"))
                );
                newsList.add(news);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    @Override
    public News readById(Long id) {
        String sql = "SELECT * FROM news WHERE id = ?";
        News news = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                news = new News(
                        rs.getLong("id"),
                        rs.getLong("author_id"),
                        rs.getString("title"),
                        NewsCategory.valueOf(rs.getString("category"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return news;
    }

    @Override
    public List<News> readByCategory(NewsCategory category) {
        List<News> newsList = new ArrayList<>();
        String sql = "SELECT * FROM news WHERE category = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.name());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                News news = new News(
                        rs.getLong("id"),
                        rs.getLong("author_id"),
                        rs.getString("title"),
                        NewsCategory.valueOf(rs.getString("category"))
                );
                newsList.add(news);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsList;
    }
}
