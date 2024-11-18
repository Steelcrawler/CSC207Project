package use_case.movieinfo;
import entity.Movie;

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

