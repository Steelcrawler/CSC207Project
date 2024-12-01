package use_case.delete_from_watchlist;

import java.util.List;

/**
 * The Input Data for the Delete From Watchlist Use Case.
 */
public class DeleteFromWatchlistInputData {

    private final List<Integer> selectedMovies;

    public DeleteFromWatchlistInputData(List<Integer> selectedMovies) {
        this.selectedMovies = selectedMovies;
    }

    public List<Integer> getSelectedMovies() {
        return selectedMovies;
    }
}
