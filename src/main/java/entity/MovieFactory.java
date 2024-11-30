package entity;

import java.util.Date;
import java.util.List;

/**
 * Factory for creating NormalMovie objects.
 */
public class MovieFactory {
    /**
     * Creates a movie object.
     * @param title movie title.
     * @param movieID movie id.
     * @param genres movie genres.
     * @param releaseDate movie release date.
     * @param rating movie rating.
     * @param plot movie plot.
     * @param posterPath movie posterpath.
     * @param userReviews movie userreviews.
     * @param trailerLink movie trailerlink.
     * @return returns the movie object
     */
    public Movie create(String title, int movieID, List<String> genres, Date releaseDate, double rating,
                        String plot, String posterPath, List<String> userReviews, String trailerLink) {
        return new NormalMovie(title, movieID, genres, releaseDate, rating, plot, posterPath, userReviews, trailerLink);
    }
}
