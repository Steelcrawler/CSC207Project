package interface_adapter.movie_info;

import interface_adapter.ViewModel;

/**
 * View Model for Movie Info Use Case.
 */
public class MovieInfoViewModel extends ViewModel<MovieInfoState> {
    public static final String MOVIE_TITLE_INFO = "Movie Title: ";
    public static final String RATING_INFO = "Rating: ";
    public static final String PLOT_INFO = "Plot: ";
    public static final String TRAILER_INFO = "Trailer Link: ";
    public static final String BACK_BUTTON_LABEL = "Back";

    public MovieInfoViewModel() {
        super("movie info");
        setState(new MovieInfoState());
    }
}
