package use_case.movie_search;

/**
 * The Movie Search Interactor.
 */
public class MovieSearchInteractor implements MovieSearchInputBoundary {
    private MovieSearchDataAccessInterface TMDBDataAccessObject;
    private MovieSearchOutputBoundary movieSearchPresenter;

    public MovieSearchInteractor(MovieSearchDataAccessInterface movieSearchDataAccessInterface,
                                 MovieSearchOutputBoundary movieSearchOutputBoundary) {
        this.TMDBDataAccessObject = movieSearchDataAccessInterface;
        this.movieSearchPresenter = movieSearchOutputBoundary;
    }

    @Override
    public void execute(MovieSearchInputData movieSearchInputData) {
        if (TMDBDataAccessObject.existsByTitle(movieSearchInputData.getMovieTitle())) {
            final List<Movies> moviesList = this.TMDBDataAccessObject.searchMoviesByTitle(movieSearchInputData.getMovieTitle());
            final movieSearchOutputData movieSearchOutputData = new movieSearchOutputData(moviesList, false);
            this.movieSearchPresenter.prepareSuccessView(movieSearchOutputData);
        }
        else {
            this.movieSearchPresenter.prepareFailView("No movies by that title.");
        }
    }
}

