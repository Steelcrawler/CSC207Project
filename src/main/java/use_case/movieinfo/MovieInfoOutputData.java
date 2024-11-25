package use_case.movieinfo;

import entity.Movie;

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

    public MovieInfoOutputData(String movieTitle, double movieRating, String moviePlot, String moviePoster, String movieTrailer, List<String> movieReviews) {
        this.movieTitle = movieTitle;
        this.movieRating = movieRating;
        this.moviePlot = moviePlot;
        this.moviePosterPath = moviePoster;
        this.movieTrailer = movieTrailer;
        this.movieReviews = movieReviews;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public double getMovieRating() {
        return movieRating;
    }

    public String getMoviePlot() {
        return moviePlot;
    }

    public String getMovieTrailer() {return movieTrailer;}

    public String getMoviePosterPath() {return moviePosterPath;}

    public List<String> getMovieReviews() {return movieReviews;}

}
