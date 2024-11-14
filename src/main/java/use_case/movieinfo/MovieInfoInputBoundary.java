package use_case.movieinfo;

import use_case.movieinfo.MovieInfoInputData;

/**
 * The Movie Info Use Case.
 */
public interface MovieInfoInputBoundary {

    /**
     * Execute the MovieInfoUse Case.
     * @param movieInfoInputData the input data for this use case
     */
    void execute(MovieInfoInputData movieInfoInputData);

}
