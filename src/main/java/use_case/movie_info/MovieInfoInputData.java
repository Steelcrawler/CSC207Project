package use_case.movie_info;

/**
 * The Input Data for the Movie Info Use Case.
 */
public class MovieInfoInputData {
    private final int movieID;

    public MovieInfoInputData(int movieID) {
        this.movieID = movieID;
    }

    public int getMovieID() {
        return movieID;
    }
}
