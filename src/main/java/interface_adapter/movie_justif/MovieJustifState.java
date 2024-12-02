package interface_adapter.movie_justif;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Movie Info View Model.
 */
public class MovieJustifState {
    private int movieID;
    private String movieTitle = "";
    private double ratingInfo = 0;
    private String justifInfo = "";
    private String trailerLink = "";
    private String posterPath = "";
    private List<String> userReviews = new ArrayList<>();

    public int getMovieID() {
        return movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public double getRatingInfo() {
        return ratingInfo;
    }

    public String getJustifInfo() {
        return justifInfo;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public List<String> getUserReviews() {
        return userReviews;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setRatingInfo(double ratingInfo) {
        this.ratingInfo = ratingInfo;
    }

    public void setJustifInfo(String justifInfo) {
        this.justifInfo = justifInfo;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setUserReviews(List<String> userReviews) {
        this.userReviews = userReviews;
    }
}
