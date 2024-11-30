package use_case.movie_info;

import java.util.List;

import entity.Movie;

/**
 * The Movie Info Interactor.
 */
public class MovieInfoInteractor implements MovieInfoInputBoundary {
    private final MovieInfoDataAccessInterface tmdbDataAccessObject;
    private final MovieInfoOutputBoundary movieInfoPresenter;

    public MovieInfoInteractor(MovieInfoDataAccessInterface movieInfoDataAccessInterface,
                                 MovieInfoOutputBoundary movieInfoOutputBoundary) {
        this.tmdbDataAccessObject = movieInfoDataAccessInterface;
        this.movieInfoPresenter = movieInfoOutputBoundary;
    }

    @Override
    public void execute(MovieInfoInputData movieInfoInputData) {
        Movie movie = tmdbDataAccessObject.getMovieByID(movieInfoInputData.getMovieID());
        String movieTitle = movie.getTitle();
        double movieRating = movie.getRating();
        String moviePlot = movie.getPlot();
        String moviePoster = movie.getPosterPath();
        String movieTrailer = movie.getTrailerLink();
        List<String> movieReviews = movie.getUserReviews();
        final MovieInfoOutputData movieInfoOutputData = new MovieInfoOutputData(movieTitle, movieRating, moviePlot,
                moviePoster, movieTrailer, movieReviews);
        this.movieInfoPresenter.prepareSuccessView(movieInfoOutputData);
    }

    @Override
    public void switchView() {
        this.movieInfoPresenter.switchToWatchlistView();
    }
}

