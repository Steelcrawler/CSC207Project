package use_case.movie_justif;

/**
 * The Movie Justif Use Case.
 */
public interface MovieJustifInputBoundary {

    /**
     * Execute the MovieJustif Use Case.
     * @param movieJustifInputData the input data for this use case
     */
    void execute(MovieJustifInputData movieJustifInputData);


    void switchView();
}
