package use_case.movie_justif;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.Movie;
import use_case.movie_info.MovieInfoDataAccessInterface;

/**
 * The Movie Justif Interactor.
 */
public class MovieJustifInteractor implements MovieJustifInputBoundary {
    private final MovieInfoDataAccessInterface tmdbDataAccessObject;
    private final MovieJustifDataAccessInterface geminiDataAccessObject;
    private final MovieJustifOutputBoundary movieJustifPresenter;

    public MovieJustifInteractor(MovieInfoDataAccessInterface TMDBDataAccessObject,
            MovieJustifDataAccessInterface movieJustifDataAccessInterface,
                               MovieJustifOutputBoundary movieJustifOutputBoundary) {
        this.tmdbDataAccessObject = TMDBDataAccessObject;
        this.geminiDataAccessObject = movieJustifDataAccessInterface;
        this.movieJustifPresenter = movieJustifOutputBoundary;
    }

    @Override
    public void execute(MovieJustifInputData movieJustifInputData) {
        int rec = movieJustifInputData.getRecommended();
        Movie movie = tmdbDataAccessObject.getMovieByID(rec);
        String justif = "Justification not available yet.";
        List<String> titles = new ArrayList<>();
        for (int id: movieJustifInputData.getWantFrom()) {
            Movie m = tmdbDataAccessObject.getMovieByID(id);
            titles.add(m.getTitle());
        }
        try {
            String justification = geminiDataAccessObject.getJustification(titles, movie.getTitle());
            justif = justification;

        }
        catch (IOException exE) {
            // Handle I/O issues
            System.err.println("I/O error: " + exE.getMessage());
        }
        System.out.println(justif);
        String movieTitle = movie.getTitle();
        double movieRating = movie.getRating();
        String moviePoster = movie.getPosterPath();
        String movieTrailer = movie.getTrailerLink();
        List<String> movieReviews = movie.getUserReviews();
        final MovieJustifOutputData movieJustifOutputData = new MovieJustifOutputData(movieTitle, movieRating,
                justif, moviePoster, movieTrailer, movieReviews);
        this.movieJustifPresenter.prepareSuccessView(movieJustifOutputData);
    }

    @Override
    public void switchView() {
        this.movieJustifPresenter.switchToRecView();
    }
}
