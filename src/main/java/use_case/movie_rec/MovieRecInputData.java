package use_case.movie_rec;

import entity.Movie;

import java.util.List;

public class MovieRecInputData {
    private final List<Movie> movies;

    public MovieRecInputData(List<Movie> movies) {
        this.movies = movies;
    }

}
