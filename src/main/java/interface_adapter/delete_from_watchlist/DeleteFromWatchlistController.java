package interface_adapter.delete_from_watchlist;

import use_case.delete_from_watchlist.DeleteFromWatchlistInputBoundary;
import use_case.delete_from_watchlist.DeleteFromWatchlistInputData;

import java.util.List;

/**
 * Controller for the Delete From Watchlist Use Case.
 */
public class DeleteFromWatchlistController {
    private final DeleteFromWatchlistInputBoundary deleteFromWatchlistUseCaseInteractor;

    public DeleteFromWatchlistController(DeleteFromWatchlistInputBoundary deleteFromWatchlistUseCaseInteractor) {
        this.deleteFromWatchlistUseCaseInteractor = deleteFromWatchlistUseCaseInteractor;
    }

    /**
     * Executes the Delete From Watchlist Use Case.
     */
    public void execute(List<Integer> selectedMovies) {
        final DeleteFromWatchlistInputData deleteFromWatchlistInputData = new DeleteFromWatchlistInputData(selectedMovies);

        deleteFromWatchlistUseCaseInteractor.execute(deleteFromWatchlistInputData);
    }
}