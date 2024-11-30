package use_case.movie_justif;


import entity.Movie;
import use_case.movie_info.MovieInfoDataAccessInterface;
import interface_adapter.movie_justif.MovieJustifState;
import interface_adapter.movie_search.MovieSearchState;
import view.ButtonEditor;
import view.ButtonRenderer;

public class MovieJustifInteractor {


/**
 * The Movie Justif Interactor.
 */
public class MovieJustifInteractor implements MovieJustifInputBoundary {
    private final MovieInfoDataAccessInterface TMDBDataAccessObject;
    private final MovieJustifDataAccessInterface GeminiDataAccessObject;
    private final MovieJustifOutputBoundary movieJustifPresenter;

    public MovieJustifInteractor(MovieInfoDataAccessInterface TMDBDataAccessObject,
            MovieJustifDataAccessInterface movieJustifDataAccessInterface,
                               MovieJustifOutputBoundary movieJustifOutputBoundary) {
        this.TMDBDataAccessObject = TMDBDataAccessObject;
        this.GeminiDataAccessObject = movieJustifDataAccessInterface;
        this.movieJustifPresenter = movieJustifOutputBoundary;
    }

    @Override
    public void execute(MovieJustifInputData movieJustifInputData) {
        int rec = movieJustifInputData.getRecommended();
        Movie movie = TMDBDataAccessObject.getMovieByID(rec);
        String justif = "Justification not available yet.";
        List<String> titles = new ArrayList<>();
        for (int id: movieJustifInputData.getWantFrom()) {
            Movie m = TMDBDataAccessObject.getMovieByID(id);
            titles.add(m.getTitle());
        }
        try {
            String justification = GeminiDataAccessObject.getJustification(titles, movie.getTitle());
            justif = justification;

        } catch (Exception e) {
            e.printStackTrace();
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
