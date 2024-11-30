package interface_adapter.movie_search;

import interface_adapter.ViewModel;

/**
 * The movie search view model class.
 */
public class MovieSearchViewModel extends ViewModel<MovieSearchState> {

    public static final String TITLE_LABEL = "Movie Search View";
    public static final String MOVIE_TITLE_LABEL = "Enter a movie title";
    public static final String GENRE_LABEL = "Choose a genre";
    public static final String RATING_LABEL = "Choose a rating";

    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String WATCHLIST_BUTTON_LABEL = "Watchlist";
    public static final String ADD_TO_WATCHLIST_BUTTON_LABEL = "Add to Watchlist";

    public MovieSearchViewModel() {
        super("movie search");
        setState(new MovieSearchState());
    }

}
