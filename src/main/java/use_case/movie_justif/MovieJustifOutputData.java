package use_case.movie_justif;

import java.util.List;

/**
 * Output Data for Movie Justif Use Case.
 */
public class MovieJustifOutputData {
    private final String movieTitle;
    private final double movieRating;
    private final String justification;
    private final String moviePosterPath;
    private final String movieTrailer;
    private final List<String> movieReviews;

    public MovieJustifOutputData(String movieTitle, double movieRating, String justification, String moviePosterPath,
                                 String movieTrailer, List<String> movieReviews) {
        this.movieTitle = movieTitle;
        this.movieRating = movieRating;
        this.justification = justification;
        this.moviePosterPath = moviePosterPath;
        this.movieTrailer = movieTrailer;
        this.movieReviews = movieReviews;
    }

    /**
     * Get the movie title.
     * @return movie title.
     */
    public String getMovieTitle() {
        return movieTitle;
    }

    /**
     * Get the movie rating.
     * @return movie rating.
     */
    public double getMovieRating() {
        return movieRating;
    }

    /**
     * Get the movie justification.
     * @return movie justification.
     */
    public String getJustification() {
        return justification;
    }

    /**
     * Get the movie poster.
     * @return movie poster path.
     */
    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    /**
     * Get the trailer link of the movie.
     * @return trailer link.
     */
    public String getMovieTrailer() {
        return movieTrailer;
    }

    /**
     * Get the movie reviews.
     * @return movie reviews.
     */
    public List<String> getMovieReviews() {
        return movieReviews;
    }
}
