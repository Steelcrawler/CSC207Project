package use_case.movie_search;

/**
 * The Input Data for the Login Use Case.
 */
public class MovieSearchInputData {

    private final String movieTitle;

    public MovieSearchInputData(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    String getMovieTitle() {
        return movieTitle;
    }

}
