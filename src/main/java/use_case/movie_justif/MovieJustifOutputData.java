package use_case.movie_justif;

import entity.Movie;
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

    public MovieJustifOutputData(String movieTitle, double movieRating, String justification, String moviePosterPath, String movieTrailer, List<String> movieReviews) {
        this.movieTitle = movieTitle;
        this.movieRating = movieRating;
        this.justification = justification;
        this.moviePosterPath = moviePosterPath;
        this.movieTrailer = movieTrailer;
        this.movieReviews = movieReviews;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public double getMovieRating() {
        return movieRating;
    }

    public String getJustification() {
        return justification;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public String getMovieTrailer() {
        return movieTrailer;
    }

    public List<String> getMovieReviews() {
        return movieReviews;
    }
}
