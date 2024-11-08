package entity;

import java.util.Date;
import java.util.List;

/**
 * A class for Movie objects.
 */
public class Movie {
    private final String title;
    private final int movieID;
    private final String genre;
    private final Date releaseDate;
    private final double rating;
    private final String description;
    private final List<String> notableCast;
    private final String director;
    private final String plot;

    public Movie(String title) {
        this.title = title;
        // populate the attributes using the API here
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
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

    public List<String> getNotableCast() {
        return notableCast;
    }

    public String getDirector() {
        return director;
    }

    public String getPlot() {
        return plot;
    }

    public int getMovieID() {
        return movieID;
    }
}
