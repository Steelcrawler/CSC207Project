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

    /**
     * Returns the title of the movie being searched for.
     * @return the title of the movie being searched for.
     */
    String getMovieTitle() {
        return movieTitle;
    }

    /**
     * Returns the genre of the movie being searched for.
     * @return the genre of the movie being searched for.
     */
    String getGenre() {
        return genre;
    }

    /**
     * Returns the rating limit of the movie being searched for.
     * @return the rating limit of the movie being searched for.
     */
    String getRating() {
        return rating;
    }

    /**
     * Returns the list of keyword ids to search for.
     * @return the list of keyword ids to search for.
     */
    List<String> getKeywords() {
        return keywords;
    }

}
