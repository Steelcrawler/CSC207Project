package interface_adapter.Select;

import interface_adapter.ViewModel;

public class SelectViewModel extends ViewModel<SelectState> {
    public static final String TITLE_LABEL = "Watchlist";
    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String DELETE_BUTTON_LABEL = "Delete";
    public static final String REC_BUTTON_LABEL = "Get a Recommendation";

    public SelectViewModel() {
        super("Watchlist");
        setState(new SelectState());
    }
}
