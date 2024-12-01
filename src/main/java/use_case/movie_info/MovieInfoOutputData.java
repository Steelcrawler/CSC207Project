package use_case.movie_info;

import java.util.List;

/**
 * Output Data for the Movie Info Use Case.
 */
public class MovieInfoOutputData {

    private final String movieTitle;
    private final double movieRating;
    private final String moviePlot;
    private final String moviePosterPath;
    private final String movieTrailer;
    private final List<String> movieReviews;

    public MovieInfoOutputData(String movieTitle, double movieRating, String moviePlot, String moviePoster,
                               String movieTrailer, List<String> movieReviews) {
        this.movieTitle = movieTitle;
        this.movieRating = movieRating;
        this.moviePlot = moviePlot;
        this.moviePosterPath = moviePoster;
        this.movieTrailer = movieTrailer;
        this.movieReviews = movieReviews;
    }

    /**
     * Returns movie title.
     * @return movie title string.
     */
    public String getMovieTitle() {
        return movieTitle;
    }

    /**
     * Returns movie rating.
     * @return movie rating double.
     */
    public double getMovieRating() {
        return movieRating;
    }

    /**
     * Returns movie plot.
     * @return movie plot string.
     */
    public String getMoviePlot() {
        return moviePlot;
    }

    /**
     * Returns movie trailer.
     * @return movie trailer string.
     */
    public String getMovieTrailer() {
        return movieTrailer;
    }

    /**
     * Returns movie poster path.
     * @return movie poster path string.
     */
    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    /**
     * Returns movie reviews.
     * @return movie reviews list of strings.
     */
    public List<String> getMovieReviews() {
        return movieReviews;
    }

}
