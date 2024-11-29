package use_case.recommendation;

import java.util.List;

public class RecommendationInputData {

    private List<Integer> selectedMoviesList;

    public RecommendationInputData(List<Integer> selectedMoviesList) {
        this.selectedMoviesList = selectedMoviesList;
    }

    public List<Integer> getSelectedMoviesList() {return selectedMoviesList;}
}
