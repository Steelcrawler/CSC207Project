package interface_adapter.movieinfo;

/**
 * The state for the Movie Info View Model.
 */
public class MovieInfoState {
    private String movieTitle = "";
    private double ratingInfo = 0;
    private String plotInfo = "";
    private String trailerLink = "";

    public String getMovieTitle() {return movieTitle;}
    public double getRatingInfo() {return ratingInfo;}
    public String getPlotInfo() {return plotInfo;}
    public String getTrailerLink() {return trailerLink;}

    public void setMovieTitle(String movieTitle) {this.movieTitle = movieTitle;}
    public void setRatingInfo(double ratingInfo) {this.ratingInfo = ratingInfo;}
    public void setPlotInfo(String plotInfo) {this.plotInfo = plotInfo;}

    public void setTrailerLink(String trailerLink) {this.trailerLink = trailerLink;}

}
