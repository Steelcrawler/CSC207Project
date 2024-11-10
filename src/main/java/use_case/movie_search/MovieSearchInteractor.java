package use_case.movie_search;
import entity.Movie;
import java.util.List;


/**
 * The Movie Search Interactor.
 */
public class MovieSearchInteractor implements MovieSearchInputBoundary {
    private final MovieSearchDataAccessInterface TMDBDataAccessObject;
    private final MovieSearchOutputBoundary movieSearchPresenter;

    public MovieSearchInteractor(MovieSearchDataAccessInterface movieSearchDataAccessInterface,
                                 MovieSearchOutputBoundary movieSearchOutputBoundary) {
        this.TMDBDataAccessObject = movieSearchDataAccessInterface;
        this.movieSearchPresenter = movieSearchOutputBoundary;
    }

    @Override
    public void execute(MovieSearchInputData movieSearchInputData) {
        if (TMDBDataAccessObject != null) {
            System.out.println("Not null");
            if (TMDBDataAccessObject.existsByTitle(movieSearchInputData.getMovieTitle())) {
                System.out.println("movie title exists");
                final List<Movie> moviesList = this.TMDBDataAccessObject.searchMoviesByTitle(movieSearchInputData.getMovieTitle());
                final MovieSearchOutputData movieSearchOutputData = new MovieSearchOutputData(moviesList, false);
                this.movieSearchPresenter.prepareSuccessView(movieSearchOutputData);
            }
            else {
                this.movieSearchPresenter.prepareFailView("No movies with that title.");
            }
        }
        else {
            System.out.println("Its null");
            this.movieSearchPresenter.prepareFailView("No movies with that title.");
        }

    }
}

