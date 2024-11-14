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

    public MovieInfoOutputData(Movie movie) {
        this.movieTitle = movie.getTitle();
        this.movieRating = movie.getRating();
        this.moviePlot = movie.getPlot();

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

}
