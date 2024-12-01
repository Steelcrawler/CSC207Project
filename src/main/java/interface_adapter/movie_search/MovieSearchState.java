package interface_adapter.movie_search;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Movie Search View Model.
 */
public class MovieSearchState {

    private String title = "";
    private String genre = "";
    private String rating = "";
    private String errorMessage = "";
    private Boolean searchFound = false;
    private Object[][] moviesInfo;
    private int rowOfATWButtonClicked = -1;
    private ArrayList<Integer> moviesIds;
    private String movieAddedToWatchlist = "";
    private List<String> keywords = new ArrayList<String>();

    /**
     * Returns the title of the movie.
     * @return the title of the movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the genre of the movie.
     * @return the genre of the movie.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Returns the rating of the movie.
     * @return the rating of the movie.
     */
    public String getRating() {
        return rating;
    }

    /**
     * Returns the keywords of the movie.
     * @return the keywords of the movie.
     */
    public List<String> getKeywords() {
        return keywords;
    }

    /**
     * Returns the error message.
     * @return the error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Returns the movies info.
     * @return the movies info.
     */
    public Object[][] getMoviesInfo() {
        return moviesInfo;
    }

    /**
     * Returns whether the search was found.
     * @return whether the search was found.
     */
    public Boolean getSearchFound() {
        return searchFound;
    }

    /**
     * Sets the title of the movie.
     * @param title the title of the movie.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the genre of the movie.
     * @param genre the genre of the movie.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Sets the rating of the movie.
     * @param rating the rating of the movie.
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * Sets the error message.
     * @param errorMessage the error message.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Sets the movies info.
     * @param moviesInfo the movies info.
     */
    public void setMoviesInfo(Object[][] moviesInfo) {
        this.moviesInfo = moviesInfo;
    }

    /**
     * Sets whether the search was found.
     * @param searchFound whether the search was found.
     */
    public void setSearchFound(boolean searchFound) {
        this.searchFound = searchFound;
    }

    /**
     * Sets the keywords of the movie.
     * @param keywords the keywords of the movie.
     */
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "MovieSearchState{"
                + "title='" + title + '\''
                + ", genre='" + genre + '\''
                + ", rating='" + rating + '\''
                + ", keywords=" + keywords + '\''
                + '}';
    }

    /**
     * Returns the row of the ATW button clicked.
     * @return the row of the ATW button clicked.
     */
    public ArrayList<Integer> getMoviesIDs() {
        return moviesIds;
    }

    /**
     * Sets the row of the ATW button clicked.
     * @param moviesIDs the row of the ATW button clicked.
     */
    public void setMoviesIDs(ArrayList<Integer> moviesIDs) {
        this.moviesIds = moviesIDs;
    }

    /**
     * Returns the row of the ATW button clicked.
     * @return the row of the ATW button clicked.
     */
    public int getRowOfATWButtonClicked() {
        return rowOfATWButtonClicked;
    }

    /**
     * Sets the row of the ATW button clicked.
     * @param rowOfATWButtonClicked the row of the ATW button clicked.
     */
    public void setRowOfATWButtonClicked(int rowOfATWButtonClicked) {
        this.rowOfATWButtonClicked = rowOfATWButtonClicked;
    }

    /**
     * Sets the message for when a movie is added to the watchlist.
     * @param addedMessage the message for when a movie is added to the watchlist.
     */
    public void setMovieAddedToWatchlist(String addedMessage) {
        movieAddedToWatchlist = addedMessage;
    }

    /**
     * Returns the message for when a movie is added to the watchlist.
     * @return the message for when a movie is added to the watchlist.
     */
    public String getMovieAddedToWatchlist() {
        return movieAddedToWatchlist;
    }
}

