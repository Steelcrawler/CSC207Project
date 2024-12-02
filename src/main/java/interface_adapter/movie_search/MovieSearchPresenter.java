package interface_adapter.movie_search;

import java.util.ArrayList;
import java.util.List;

import entity.Movie;
import interface_adapter.ViewManagerModel;
import use_case.movie_search.MovieSearchOutputBoundary;
import use_case.movie_search.MovieSearchOutputData;

/**
 * The Presenter for the Movie Search Use Case.
 */
public class MovieSearchPresenter implements MovieSearchOutputBoundary {
    private static final int MOVIE_INFO_COLUMNS = 4;

    private final MovieSearchViewModel movieSearchViewModel;
    private final ViewManagerModel viewManagerModel;
    private List<Movie> movieList;

    public MovieSearchPresenter(ViewManagerModel viewManagerModel,
                           MovieSearchViewModel movieSearchViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.movieSearchViewModel = movieSearchViewModel;
        this.movieList = new ArrayList<>();
    }

    /**
     * Prepares the success view for the Movie Search Use Case.
     * @param outputData the output data, can be null
     */
    @Override
    public void prepareSuccessView(MovieSearchOutputData outputData) {
        this.movieList = new ArrayList<>();
        final MovieSearchState movieSearchState = movieSearchViewModel.getState();
        ArrayList<ArrayList<Object>> moviesInfo = new ArrayList<>();
        movieSearchState.setSearchFound(true);
        addAllMovies(outputData.getMovies());
        ArrayList<Integer> moviesIds = new ArrayList<>();
        for (Movie movie : this.movieList) {
            ArrayList<Object> movieInfo = new ArrayList<>();
            movieInfo.add(movie.getTitle());
            movieInfo.add(movie.getGenres());
            movieInfo.add(movie.getRating());
            movieInfo.add(movie.getPlot());
            moviesInfo.add(movieInfo);
            moviesIds.add(movie.getMovieID());
        }
        Object[][] newmoviesInfo = new Object[moviesInfo.size()][MOVIE_INFO_COLUMNS];
        for (int i = 0; i < moviesInfo.size(); i++) {
            for (int j = 0; j < MOVIE_INFO_COLUMNS; j++) {
                newmoviesInfo[i][j] = moviesInfo.get(i).get(j);
            }
        }
        movieSearchState.setMoviesInfo(newmoviesInfo);
        movieSearchState.setMoviesIDs(moviesIds);
        movieSearchViewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the Movie Search Use Case.
     * @param errorMessage the explanation of the failure, can be null
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final MovieSearchState movieSearchState = movieSearchViewModel.getState();
        movieSearchState.setSearchFound(false);
        movieSearchState.setErrorMessage(errorMessage);
        movieSearchViewModel.firePropertyChanged();
    }

    /**
     * Adds a movie to the list of movies.
     * @param movies the list of movies to add.
     */
    public void addAllMovies(List<Movie> movies) {
        this.movieList.addAll(movies);
    }
}

