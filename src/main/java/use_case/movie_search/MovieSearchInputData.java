package use_case.movie_search;

/**
 * The Input Data for the Login Use Case.
 */
public class MovieSearchInputData {

    private final String movieTitle;
    private final String genre;
    private final String rating;

    public MovieSearchInputData(String movieTitle, String genre, String rating) {
        this.movieTitle = movieTitle;
        this.genre = genre;
        this.rating = rating;

    }

    String getMovieTitle() {
        return movieTitle;
    }

    String getGenre() {
        return genre;
    }

    String getRating() {
        return rating;
    }

}
