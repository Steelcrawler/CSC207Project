package interface_adapter.watchlist;

import interface_adapter.ViewModel;
import interface_adapter.moviesearch.MovieSearchState;

public class WatchlistViewModel extends ViewModel<WatchlistState> {
    public static final String TITLE_LABEL = "Your Watchlist";
    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String SELECT_BUTTON_LABEL = "Select";

    public WatchlistViewModel() {
        super("Watchlist");
        setState(new WatchlistState());
    }
}
