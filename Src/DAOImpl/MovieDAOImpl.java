package Src.DAOImpl;

import Src.DAO.MovieDAO;
import Src.DbConnection;
import Src.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MovieDAOImpl implements MovieDAO {

    public static void main(String[] args) {
        MovieDAO movieDAO = new MovieDAOImpl();
        Movie movie = new Movie("Psycho", 1960);
        ArrayList<String> actors = movieDAO.getDirectorsForMovie(movie);
        for(String actor : actors) {
            System.out.println(actor);
        }
        System.out.println(movieDAO.getMovieRating(movie));
        ArrayList<Movie> movies = movieDAO.getTopMoviesInTimeInterval(1950, 1960);
        for(Movie m : movies) {
            System.out.println(m.getTitle());
        }
        ArrayList<String> reviews = movieDAO.getAllMovieReviews(movie);
        for(String review : reviews) {
            System.out.println(review);
        }
    }

    public ArrayList<String> getActorsForMovie(Movie movie) {
        String query = "SELECT aps.name\n" +
                "FROM CONTRACT c\n" +
                "JOIN ActorProfileStock aps ON c.stock_symbol = aps.stock_symbol\n" +
                "WHERE c.title = ? AND c.year = ? AND c.actor_role IN ('Actor', 'both')";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, movie.getTitle());
            statement.setInt(2, movie.getYear());
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> actors = new ArrayList<>();
            while(resultSet.next()) {
                String name = resultSet.getString("name");
                actors.add(name);
            }
            return actors;
        } catch (Exception e) {
            System.out.println("ERROR: getActorsForMovie failed.");
            System.out.println(e);
        }

        return new ArrayList<>();
    }

    public ArrayList<String> getDirectorsForMovie(Movie movie) {
        String query = "SELECT aps.name\n" +
                "FROM CONTRACT c\n" +
                "JOIN ActorProfileStock aps ON c.stock_symbol = aps.stock_symbol\n" +
                "WHERE c.title = ? AND c.year = ? AND c.actor_role IN ('Director', 'both', 'Both')";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, movie.getTitle());
            statement.setInt(2, movie.getYear());
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> directors = new ArrayList<>();
            while(resultSet.next()) {
                String name = resultSet.getString("name");
                directors.add(name);
            }
            return directors;
        } catch (Exception e) {
            System.out.println("ERROR: getActorsForMovie failed.");
            System.out.println(e);
        }

        return new ArrayList<>();
    }

    public float getMovieRating(Movie movie) {
        String query = "SELECT avg(rating)\n" +
                "FROM Review\n" +
                "WHERE title = ? AND year = ?";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, movie.getTitle());
            statement.setInt(2, movie.getYear());
            ResultSet resultSet = statement.executeQuery();
            float rating = 0;
            while(resultSet.next()) {
                rating = resultSet.getFloat("avg(rating)");
            }
            return rating;
        } catch (Exception e) {
            System.out.println("ERROR: getMovieRating failed.");
            System.out.println(e);
        }
        return -1;
    }

    public ArrayList<String> getAllMovieReviews(Movie movie) {
        String query = "SELECT review FROM Review WHERE title = ? and year = ?";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, movie.getTitle());
            statement.setInt(2, movie.getYear());
            ResultSet resultSet = statement.executeQuery();
            ArrayList<String> reviews = new ArrayList<>();
            while(resultSet.next()) {
                String review = resultSet.getString("review");
                reviews.add(review);
            }
            return reviews;
        } catch (Exception e) {
            System.out.println("ERROR: getAllMovieReviews failed.");
            System.out.println(e);
        }

        return new ArrayList<>();
    }

    public ArrayList<Movie> getTopMoviesInTimeInterval(int startYear, int endYear) {
        String query = "SELECT avg(rating) as avg_rating, title, year\n" +
                "FROM Review\n" +
                "GROUP BY (title, year)\n" +
                "HAVING avg(rating) >= 10 and year >= ? and year <= ?";
        try {
            Connection connection = DbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, startYear);
            statement.setInt(2, endYear);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Movie> movies = new ArrayList<>();
            while(resultSet.next()) {
                String title = resultSet.getString("title");
                int year = resultSet.getInt("year");
                Movie movie = new Movie(title, year);
                movies.add(movie);
            }
            return movies;
        } catch (Exception e) {
            System.out.println("ERROR: getTopMoviesInTimeInterval failed.");
            System.out.println(e);
        }
        return new ArrayList<>();
    }
}
