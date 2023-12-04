package Src.DAO;

import Src.Movie;

import java.util.ArrayList;

public interface MovieDAO {
    ArrayList<String> getActorsForMovie(Movie movie);
    ArrayList<String> getDirectorsForMovie(Movie movie);
    float getMovieRating(Movie movie);
    ArrayList<String> getAllMovieReviews(Movie movie);
    ArrayList<Movie> getTopMoviesInTimeInterval(int startYear, int endYear);
}
