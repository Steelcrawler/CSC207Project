package use_case.movie_justif;

import entity.Movie;

import java.util.List;

public class MovieJustifInputData {
    private final List<Movie> movies;

    public MovieJustifInputData(List<Movie> movies) {
        this.movies = movies;
    }

}
