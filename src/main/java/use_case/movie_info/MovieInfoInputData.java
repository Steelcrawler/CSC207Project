package use_case.movie_info;

/**
 * The Input Data for the Movie Info Use Case.
 */
public class MovieInfoInputData {
    private final int movieID;

    public MovieInfoInputData(int movieID) {
        this.movieID = movieID;
    }

    /**
     * Get the movie id.
     * @return integer movie id.
     */
    public int getMovieID() {
        return movieID;
    }
}

