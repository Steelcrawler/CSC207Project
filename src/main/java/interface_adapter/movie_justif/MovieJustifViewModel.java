package interface_adapter.movie_justif;

import interface_adapter.ViewModel;

/**
 * Movie justification use case view model.
 */
public class MovieJustifViewModel extends ViewModel<MovieJustifState> {

    public static final String MOVIE_TITLE_INFO = "Movie Title: ";
    public static final String RATING_INFO = "Rating: ";
    public static final String JUSTIF_INFO = "Justification: ";
    public static final String TRAILER_INFO = "Trailer Link: ";
    public static final String BACK_BUTTON_LABEL = "Back";

    public MovieJustifViewModel() {
        super("movie justification");
        setState(new MovieJustifState());
    }
}
