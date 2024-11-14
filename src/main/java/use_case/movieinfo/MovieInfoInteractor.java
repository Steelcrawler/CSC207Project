package use_case.movieinfo;

import entity.Movie;
import use_case.movie_search.*;

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
    public void execute(Movie movie) {
        final MovieInfoOutputData movieInfoOutputData = new MovieInfoOutputData(movie);
        this.movieInfoPresenter.prepareSuccessView(movieInfoOutputData);

    }
}

