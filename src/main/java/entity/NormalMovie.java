package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple implementation of the Movie interface.
 */
public class NormalMovie implements Movie {

    private final String title;
    private final int movieID;
    private final List<String> genres;
    private final Date releaseDate;
    private final double rating;
    private final String plot;
    private final String posterPath;
    private final List<String> userReviews;
    private final String trailerLink;

    public NormalMovie(String title, int movieID, List<String> genres, Date releaseDate,
                       double rating, String plot, String posterPath, List<String> userReviews, String trailerLink) {
        this.title = title;
        this.movieID = movieID;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.plot = plot;
        this.posterPath = posterPath;
        this.userReviews = new ArrayList<>(userReviews);
        this.trailerLink = trailerLink;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getMovieID() {
        return movieID;
    }

    @Override
    public List<String> getGenres() {
        return genres;
    }

    @Override
    public Date getReleaseDate() {
        return releaseDate;
    }

    @Override
    public double getRating() {
        return rating;
    }

    @Override
    public String getPlot() {
        return plot;
    }

    @Override
    public String getPosterPath() {
        return posterPath;
    }

    @Override
    public List<String> getUserReviews() {
        return new ArrayList<>(userReviews);
    }

    @Override
    public String getTrailerLink() {
        return trailerLink;
    }
}
