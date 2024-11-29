package interface_adapter.recommendation;

import interface_adapter.ViewModel;
import interface_adapter.watchlist.WatchlistState;

public class RecommendationViewModel extends ViewModel<RecommendationState> {
    public static final String TITLE_LABEL = "Your Recommendation";
    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String MOVIE_TITLE_INFO = "Movie Title: ";
    public static final String JUST_BUTTON = "Justification";
    public static final String NEXT_BUTTON = "Get Another Recommendation";


    public RecommendationViewModel() {
        super("Recommendation");
        setState(new RecommendationState());
    }
}
