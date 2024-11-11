package interface_adapter.watchlist;

import interface_adapter.ViewModel;
import interface_adapter.moviesearch.MovieSearchState;

public class WatchlistViewModel extends ViewModel {
    public static final String TITLE_LABEL = "Watchlist";
    public static final String MOVIE_TITLE_LABEL = "Enter a movie title";
    public static final String GENRE_LABEL = "Choose a genre";
    public static final String RATING_LABEL = "Choose a rating";

    public static final String SEARCH_BUTTON_LABEL = "Search";

    public WatchlistViewModel() {
        super("movie search");
        setState(new MovieSearchState());
    }
}
