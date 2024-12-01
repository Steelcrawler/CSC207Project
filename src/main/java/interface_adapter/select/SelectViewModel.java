package interface_adapter.select;

import interface_adapter.ViewModel;

/**
 * The view model for the SelectView.
 */
public class SelectViewModel extends ViewModel<SelectState> {
    public static final String TITLE_LABEL = "Select";
    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String DELETE_BUTTON_LABEL = "Delete";
    public static final String REC_BUTTON_LABEL = "Get a Recommendation";
    public static final Integer FRAME_DIMENSION = 800;
    public static final Integer SPACER_DIMENSION = 20;
    public static final Integer MOVIE_PANEL_ROW = 0;
    public static final Integer MOVIE_PANEL_COLUMN = 3;
    public static final Integer UNIT_INCREMENT = 15;
    public static final Integer MOVIE_BUTTON_WIDTH = 110;
    public static final Integer MOVIE_BUTTON_HEIGHT = 140;

    public SelectViewModel() {
        super("Select");
        setState(new SelectState());
    }
}
