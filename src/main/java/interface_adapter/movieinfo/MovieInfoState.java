package interface_adapter.movieinfo;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Movie Info View Model.
 */
public class MovieInfoState {
    private int movieID;
    private String movieTitle;
    private double ratingInfo;
    private String plotInfo;
    private String trailerLink;
    private String posterPath;
    private List<String> userReviews;

    public int getMovieID() {return movieID;}
    public String getMovieTitle() {return movieTitle;}
    public double getRatingInfo() {return ratingInfo;}
    public String getPlotInfo() {return plotInfo;}
    public String getTrailerLink() {return trailerLink;}
    public String getPosterPath() {return posterPath;}
    public List<String> getUserReviews() {return userReviews;}

    public void setMovieID(int movieID) {this.movieID = movieID;}
    public void setMovieTitle(String movieTitle) {this.movieTitle = movieTitle;}
    public void setRatingInfo(double ratingInfo) {this.ratingInfo = ratingInfo;}
    public void setPlotInfo(String plotInfo) {this.plotInfo = plotInfo;}
    public void setTrailerLink(String trailerLink) {this.trailerLink = trailerLink;}
    public void setPosterPath(String posterPath) {this.posterPath = posterPath;}
    public void setUserReviews(List<String> userReviews) {this.userReviews = userReviews;}

}
