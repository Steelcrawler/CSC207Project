package use_case.movieinfo;

import entity.Movie;

import java.util.List;

/**
 * The Movie Info Interactor.
 */
public class MovieInfoInteractor implements MovieInfoInputBoundary {
    private final MovieInfoDataAccessInterface TMDBDataAccessObject;
    private final MovieInfoOutputBoundary movieInfoPresenter;

    public MovieInfoInteractor(MovieInfoDataAccessInterface movieInfoDataAccessInterface,
                                 MovieInfoOutputBoundary movieInfoOutputBoundary) {
        this.TMDBDataAccessObject = movieInfoDataAccessInterface;
        this.movieInfoPresenter = movieInfoOutputBoundary;
    }

    @Override
    public void execute(MovieInfoInputData movieInfoInputData) {
        if (TMDBDataAccessObject != null) {
            Movie movie = TMDBDataAccessObject.getMovieByID(movieInfoInputData.getMovieID());
            String movieTitle = movie.getTitle();
            double movieRating = movie.getRating();
            String moviePlot = movie.getPlot();
            String moviePoster = movie.getPosterPath();
            String movieTrailer = movie.getTrailerLink();
            List<String> movieReviews = movie.getUserReviews();
            final MovieInfoOutputData movieInfoOutputData = new MovieInfoOutputData(movieTitle, movieRating, moviePlot, moviePoster, movieTrailer, movieReviews);
            this.movieInfoPresenter.prepareSuccessView(movieInfoOutputData);
        }
        else {
            this.movieInfoPresenter.prepareFailView("Could not load info page for this movie");
        }
    }
}

