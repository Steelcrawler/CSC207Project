package interface_adapter.moviesearch;

import entity.Movie;
import interface_adapter.ViewManagerModel;
import use_case.movie_search.MovieSearchOutputBoundary;
import use_case.movie_search.MovieSearchOutputData;

import java.util.ArrayList;
import java.util.List;

/**
 * The Presenter for the Movie Search Use Case.
 */
public class MovieSearchPresenter implements MovieSearchOutputBoundary {

    private final MovieSearchViewModel movieSearchViewModel;
    private final ViewManagerModel viewManagerModel;
    private final List<Movie> movieList;

    public MovieSearchPresenter(ViewManagerModel viewManagerModel,
                           MovieSearchViewModel movieSearchViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.movieSearchViewModel = movieSearchViewModel;
        this.movieList = new ArrayList<>();
    }

    @Override
    public void prepareSuccessView(MovieSearchOutputData outputData) {
        final MovieSearchState movieSearchState = movieSearchViewModel.getState();
        ArrayList<ArrayList<Object>> moviesInfo = new ArrayList<>();
        movieSearchState.setSearchFound(true);
        addAllMovies(outputData.getMovies());
        ArrayList<Integer> moviesIDs = new ArrayList<>();
        for (Movie movie : this.movieList) {
            ArrayList<Object> movieInfo = new ArrayList<>();
            movieInfo.add(movie.getTitle());
            movieInfo.add(movie.getGenres());
            movieInfo.add(movie.getRating());
            movieInfo.add(movie.getPlot());
            moviesInfo.add(movieInfo);
            moviesIDs.add(movie.getMovieID());
        }
        Object[][] newmoviesInfo = new Object[moviesInfo.size()][4];
        for (int i = 0; i < moviesInfo.size(); i++) {
            for (int j = 0; j < 4; j++) {
                newmoviesInfo[i][j] = moviesInfo.get(i).get(j);
            }
        }
        movieSearchState.setMoviesInfo(newmoviesInfo);
        movieSearchState.setMoviesIDs(moviesIDs);
        movieSearchViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final MovieSearchState movieSearchState = movieSearchViewModel.getState();
        movieSearchState.setSearchFound(false);
        movieSearchState.setErrorMessage(errorMessage);
        movieSearchViewModel.firePropertyChanged();
    }
    public void addAllMovies(List<Movie> movies) {
        this.movieList.addAll(movies);
    }
}

