package use_case.movie_justif;

/**
 * The Movie Search Use Case.
 */
public interface MovieRecInputBoundary {

    /**
     * Execute the MovieRec Use Case.
     * @param movieRecInputData the input data for this use case
     */
    void execute(MovieRecInputData movieRecInputData);

}
