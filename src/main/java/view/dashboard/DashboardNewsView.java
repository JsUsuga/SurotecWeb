package view.dashboard;

import model.domain.news.News;
import model.domain.news.NewsCategory;
import service.news.NewsServiceImpl;

import java.util.List;
import java.util.Scanner;

public class DashboardNewsView {
    private final NewsServiceImpl newsService;

    public DashboardNewsView(NewsServiceImpl newsService) {
        this.newsService = newsService;
    }

    public void showDashboard() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("=== News Dashboard ===");
            System.out.println("1. Ver Todas las Noticias");
            System.out.println("2. Crear Noticia");
            System.out.println("3. Filtrar Noticias por Categoría");
            System.out.println("4. regresar al Menú Principal");
            System.out.print("seleccionar opcion: ");
            int option;
            try {
                option = Integer.parseInt(scanner.nextLine()); // si no es número, lanza excepción
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor ingresa un número (1-4).");
                continue; // vuelve al inicio del while sin romper el programa
            }
            switch (option) {
                case 1:
                    viewAllNews();
                    break;
                case 2:
                    createNews(scanner);
                    break;
                case 3:
                    filterNewsByCategory(scanner);
                    break;
                case 4:
                    return; // Exit to main menu
                default:
                    System.out.println("Opcion invalida, ingresa otra opción.");
            }
        }
    }

    private void filterNewsByCategory(Scanner scanner) {
        try {
            System.out.print("Ingrese la categoría (CULTURA, SPORTS, TECHNOLOGIA, INNOVACIÓN): ");
            String categoryStr = scanner.nextLine();

            NewsCategory category = NewsCategory.valueOf(categoryStr.toUpperCase());

            List<News> filteredNews = newsService.getNewsByCategory(category);

            if (filteredNews.isEmpty()) {
                System.out.println("No se encontraron noticias en la categoría: " + category);
            } else {
                System.out.println("Noticias en la categoría " + category + ":");
                for (News news : filteredNews) {
                    System.out.println(news);
                }
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Categoría inválida. Intente nuevamente.");
        }
    }


    private void viewAllNews() {
        List<News> newsList = newsService.getAllNews();
        if (newsList.isEmpty()) {
            System.out.println("No hay noticias disponible.");
        } else {
            for (News news : newsList) {
                System.out.println(news);
            }
        }
    }

    private void createNews(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del Autor: ");
            Long authorId = Long.parseLong(scanner.nextLine());

            System.out.print("Ingresa el Titulo: ");
            String title = scanner.nextLine();

            System.out.print("Ingresa el Contenido: ");
            String content = scanner.nextLine();

            System.out.print("Ingresa la fecha de Publicación (YYYY-MM-DD): ");
            String dateStr = scanner.nextLine();
            java.util.Date publishedDate = java.sql.Date.valueOf(dateStr);

            System.out.print("Ingrese la Categoria (CULTURA, SPORTS, TECHNOLOGIA, INNOVACIÓN): ");
            String categoryStr = scanner.nextLine();
            NewsCategory category = NewsCategory.valueOf(categoryStr.toUpperCase());

            News news = new News(null, authorId, title, content, publishedDate, category);
            newsService.createNews(news);
            System.out.println("Nueva Noticia Creada.");

        } catch (Exception e) {
            System.out.println("Error al crear la Noticia: " + e.getMessage());
        }
    }

    public static void main (String[] args) {
        NewsServiceImpl newsService = new NewsServiceImpl();
        DashboardNewsView dashboard = new DashboardNewsView(newsService);
        dashboard.showDashboard();
    }
}
