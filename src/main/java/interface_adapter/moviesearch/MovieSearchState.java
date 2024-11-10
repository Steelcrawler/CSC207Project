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

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getRating() {
        return rating;
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
    @Override
    public String toString() {
        return "MovieSearchState{"
                + "title='" + title + '\''
                + ", genre='" + genre + '\''
                + ", rating='" + rating + '\''
                + '}';
    }
}

