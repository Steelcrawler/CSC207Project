package entity;

import java.util.Date;
import java.util.List;

/**
 * Factory for creating NormalMovie objects.
 */
public class MovieFactory {
    public Movie create(String title, int movieID, List<String> genres, Date releaseDate, double rating, String plot, String posterPath, List<String> userReviews, String trailerLink) {
        return new NormalMovie(title, movieID, genres, releaseDate, rating, plot, posterPath, userReviews, trailerLink);
    }
}