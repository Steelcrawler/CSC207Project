package interface_adapter.moviesearch;

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
    private ArrayList<Integer> moviesIDs;
    private String movieAddedToWatchlist = "";
    private List<String> keywords = new ArrayList<String>();

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getRating() {
        return rating;
    }

    public List<String> getKeywords() {
        return keywords;
    }


    public String getErrorMessage() {return errorMessage;};

    public Object[][] getMoviesInfo() {return moviesInfo;}

    public Boolean getSearchFound() {return searchFound;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setMoviesInfo(Object[][] moviesInfo) {
        this.moviesInfo = moviesInfo;
    }

    public void setSearchFound(boolean searchFound) {
        this.searchFound = searchFound;
    }

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

    public ArrayList<Integer> getMoviesIDs() {
        return moviesIDs;
    }

    public void setMoviesIDs(ArrayList<Integer> moviesIDs) {
        this.moviesIDs = moviesIDs;
    }

    public int getRowOfATWButtonClicked() {
        return rowOfATWButtonClicked;
    }

    public void setRowOfATWButtonClicked(int rowOfATWButtonClicked) {
        this.rowOfATWButtonClicked = rowOfATWButtonClicked;
    }

    public void setMovieAddedToWatchlist(String addedMessage) {
        movieAddedToWatchlist = addedMessage;
    }

    public String getMovieAddedToWatchlist() {
        return movieAddedToWatchlist;
    }
}

