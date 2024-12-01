package interface_adapter.watchlist;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Watchlist View.
 */
public class WatchlistViewModel extends ViewModel<WatchlistState> {
    public static final String TITLE_LABEL = "Your Watchlist";
    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String SELECT_BUTTON_LABEL = "Select";
    public static final Integer FRAME_DIMENSION = 800;
    public static final Integer SPACER_DIMENSION = 20;
    public static final Integer MOVIE_PANEL_ROW = 10;
    public static final Integer MOVIE_PANEL_COLUMN = 5;
    public static final Integer UNIT_INCREMENT = 15;
    public static final Integer MOVIE_BUTTON_WIDTH = 110;
    public static final Integer MOVIE_BUTTON_HEIGHT = 140;

    public WatchlistViewModel() {
        super("Watchlist");
        setState(new WatchlistState());
    }
}
