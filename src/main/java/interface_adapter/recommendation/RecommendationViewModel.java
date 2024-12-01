package interface_adapter.recommendation;

import interface_adapter.ViewModel;

/**
 * The view model for the recommendation view.
 */
public class RecommendationViewModel extends ViewModel<RecommendationState> {
    public static final String TITLE_LABEL = "Your Recommendation";
    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String MOVIE_TITLE_INFO = "Movie Title: ";
    public static final String JUST_BUTTON = "Justification";
    public static final String NEXT_BUTTON = "Get Another Recommendation";
    public static final Integer PANE_SIZE = 800;
    public static final Integer TAREA_WIDTH = 400;
    public static final Integer TAREA_HEIGHT = 200;
    public static final Integer MAX_WIDTH = 150;
    public static final Integer MAX_HEIGHT = 250;

    public RecommendationViewModel() {
        super("Recommendation");
        setState(new RecommendationState());
    }
}
