package interface_adapter.movie_justif;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Movie Info View Model.
 */
public class MovieJustifState {
    private int movieID;
    private String movieTitle = "";
    private double ratingInfo;
    private String justifInfo = "";
    private String trailerLink = "";
    private String posterPath = "";
    private List<String> userReviews = new ArrayList<>();

    /**
     * Get the id of the movie.
     * @return movie id.
     */
    public int getMovieID() {
        return movieID;
    }

    /**
     * Get the title of the movie.
     * @return movie title
     */
    public String getMovieTitle() {
        return movieTitle;
    }

    /**
     * Get the rating of the movie.
     * @return movie rating.
     */
    public double getRatingInfo() {
        return ratingInfo;
    }

    /**
     * Get the Justification of the movie.
     * @return movie justification.
     */
    public String getJustifInfo() {
        return justifInfo;
    }

    /**
     * Get the trailer link of the movie.
     * @return youtube link.
     */
    public String getTrailerLink() {
        return trailerLink;
    }

    /**
     * Get the poster of the movie.
     * @return movie poster path.
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Get the reviews from users who watched the movie before.
     * @return reviews.
     */
    public List<String> getUserReviews() {
        return userReviews;
    }

    /**
     * Set the movie id.
     * @param movieID movie id.
     */
    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    /**
     * Set the title of the movie.
     * @param movieTitle movie title.
     */
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    /**
     * Set the rating of the movie.
     * @param ratingInfo rating.
     */
    public void setRatingInfo(double ratingInfo) {
        this.ratingInfo = ratingInfo;
    }

    /**
     * Set the justification of the movie.
     * @param justifInfo movie justification made by LLM.
     */
    public void setJustifInfo(String justifInfo) {
        this.justifInfo = justifInfo;
    }

    /**
     * Set the trailer link of the movie.
     * @param trailerLink link to the movie.
     */
    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    /**
     * Set the poster for the movie.
     * @param posterPath movie poster path.
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * Set the reviews for the movie.
     * @param userReviews movie reviews.
     */
    public void setUserReviews(List<String> userReviews) {
        this.userReviews = userReviews;
    }

}
