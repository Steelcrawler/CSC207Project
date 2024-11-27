package use_case.movie_search;

import java.util.List;

/**
 * The Input Data for the Movie Search Use Case.
 */
public class MovieSearchInputData {

    private final String movieTitle;
    private final String genre;
    private final String rating;
    private final List<String> keywords;

    public MovieSearchInputData(String movieTitle, String genre, String rating, List<String> keywords) {
        this.movieTitle = movieTitle;
        this.genre = genre;
        this.rating = rating;
        this.keywords = keywords;

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

    List<String> getKeywords() {
        return keywords;
    }

}
