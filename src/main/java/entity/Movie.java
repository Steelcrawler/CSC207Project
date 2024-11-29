package entity;

import java.util.Date;
import java.util.List;

/**
 * The representation of a movie in our program.
 */
public interface Movie {

    /**
     * Returns the title of the movie.
     * @return the title of the movie.
     */
    String getTitle();

    /**
     * Returns the ID of the movie.
     * @return the ID of the movie.
     */
    int getMovieID();

    /**
     * Returns the genres of the movie.
     * @return the genres of the movie.
     */
    List<String> getGenres();

    /**
     * Returns the release date of the movie.
     * @return the release date of the movie.
     */
    Date getReleaseDate();

    /**
     * Returns the rating of the movie.
     * @return the rating of the movie.
     */
    double getRating();

    /**
     * Returns the plot of the movie.
     * @return the plot of the movie.
     */
    String getPlot();

    /**
     * Returns the poster path of the movie.
     * @return the poster path of the movie.
     */
    String getPosterPath();

    /**
     * Returns the user reviews of the movie.
     * @return the user reviews of the movie.
     */
    List<String> getUserReviews();

    /**
     * Returns the trailer link of the movie.
     * @return the trailer link of the movie.
     */
    String getTrailerLink();
}