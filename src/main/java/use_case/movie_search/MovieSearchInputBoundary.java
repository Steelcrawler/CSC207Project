package use_case.movie_search.;

/**
 * The Movie Search Use Case.
 */
public interface MovieSearchInputBoundary {

    /**
     * Execute the MovieSearch Use Case.
     * @param movieSearchInputData the input data for this use case
     */
    void execute(MovieSearchInputData movieSearchInputData);

}
