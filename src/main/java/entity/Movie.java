package entity;

import java.util.Date;
import java.util.List;

/**
 * A class for Movie objects.
 */
public class Movie {
    private final String title;
    private final int movieID;
    private final List<String> genres;
    private final Date releaseDate;
    private final double rating;
    private final String plot;
    private final String posterPath;
    private final List<String> userReviews;
    private final String trailerLink;

    public Movie(String title, int movieID, List<String> genres, Date releaseDate, double rating, String plot, String posterPath, List<String> userReviews, String trailerLink) {
        this.title = title;
        this.movieID = movieID;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.plot = plot;
        this.posterPath = posterPath;
        this.userReviews = userReviews;
        this.trailerLink = trailerLink;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public List<String> getUserReviews() {
        return userReviews;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public String getPlot() {
        return plot;
    }

    public int getMovieID() {
        return movieID;
    }
}
