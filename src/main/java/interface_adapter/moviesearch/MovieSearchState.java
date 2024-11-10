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
    private Object[][] moviesInfo = new Object[3][4];

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

    public void setMoviesInfo(ArrayList<List> moviesInfo) {
        this.moviesInfo[0][0] = moviesInfo.get(0).get(0);
        this.moviesInfo[0][1] = moviesInfo.get(0).get(1);
        this.moviesInfo[0][2] = moviesInfo.get(0).get(2);
        this.moviesInfo[0][3] = moviesInfo.get(0).get(3);
        this.moviesInfo[1][0] = moviesInfo.get(1).get(0);
        this.moviesInfo[1][1] = moviesInfo.get(1).get(1);
        this.moviesInfo[1][2] = moviesInfo.get(1).get(2);
        this.moviesInfo[1][3] = moviesInfo.get(1).get(3);
        this.moviesInfo[2][0] = moviesInfo.get(2).get(0);
        this.moviesInfo[2][1] = moviesInfo.get(2).get(1);
        this.moviesInfo[2][2] = moviesInfo.get(2).get(2);
        this.moviesInfo[2][3] = moviesInfo.get(2).get(3);

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

