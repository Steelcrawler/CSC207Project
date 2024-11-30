package interface_adapter.movie_info;

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

    /**
     * Get movie id.
     * @return movie id integer.
     */
    public int getMovieID() {
        return movieID;
    }

    /**
     * Get movie title.
     * @return movie title string.
     */
    public String getMovieTitle() {
        return movieTitle;
    }

    /**
     * Get movie rating.
     * @return movie rating double.
     */
    public double getRatingInfo() {
        return ratingInfo;
    }

    /**
     * Get movie ploy.
     * @return movie plot string.
     */
    public String getPlotInfo() {
        return plotInfo;
    }

    /**
     * Get movie trailer.
     * @return movie trailer string.
     */
    public String getTrailerLink() {
        return trailerLink;
    }

    /**
     * Get movie poster.
     * @return movie poster path string.
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Get movie reviews.
     * @return movie reviews list of strings.
     */
    public List<String> getUserReviews() {
        return userReviews;
    }

    /**
     * Set movie id.
     * @param movieID integer of movie id.
     */
    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    /**
     * Set movie title.
     * @param movieTitle string of movie title.
     */
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    /**
     * Set movie rating.
     * @param ratingInfo double of movie rating.
     */
    public void setRatingInfo(double ratingInfo) {
        this.ratingInfo = ratingInfo;
    }

    /**
     * Set movie plot.
     * @param plotInfo string of movie plot.
     */
    public void setPlotInfo(String plotInfo) {
        this.plotInfo = plotInfo;
    }

    /**
     * Set movie trailer.
     * @param trailerLink string of movie title.
     */
    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    /**
     * Set movie poster path.
     * @param posterPath string of movie poster path.
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * Set movie reviews.
     * @param userReviews list of strings of user reviews.
     */
    public void setUserReviews(List<String> userReviews) {
        this.userReviews = userReviews;
    }

}
