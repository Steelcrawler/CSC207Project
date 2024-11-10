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
    private final String description;
    private final String plot;
    
    public Movie(String title, int movieID, List<String> genres, Date releaseDate, double rating, String description, String plot) {
        this.title = title;
        this.movieID = movieID;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.description = description;
        this.plot = plot;
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

    public String getDescription() {
        return description;
    }

    public String getPlot() {
        return plot;
    }

    public int getMovieID() {
        return movieID;
    }
}
