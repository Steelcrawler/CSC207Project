package use_case.movie_search;
import entity.Movie;
import java.util.List;


/**
 * The Movie Search Interactor.
 */
public class MovieSearchInteractor implements MovieSearchInputBoundary {
    private final MovieSearchDataAccessInterface TMDBDataAccessObject;
    private final MovieSearchOutputBoundary movieSearchPresenter;

    public MovieSearchInteractor(MovieSearchDataAccessInterface movieSearchDataAccessInterface,
                                 MovieSearchOutputBoundary movieSearchOutputBoundary) {
        this.TMDBDataAccessObject = movieSearchDataAccessInterface;
        this.movieSearchPresenter = movieSearchOutputBoundary;
    }

    @Override
    public void execute(MovieSearchInputData movieSearchInputData) {
        if (TMDBDataAccessObject != null) {
            System.out.println("Not null");
            String title = parseTitle(movieSearchInputData.getMovieTitle());
            String genre = parseGenre(movieSearchInputData.getGenre());
            Integer rating = parseRating(movieSearchInputData.getRating());
            System.out.println("Title: " + title);
    
            if (title == null && genre == null && rating == null) {
                this.movieSearchPresenter.prepareFailView("No search criteria provided.");
                return;
            }
            List<Movie> moviesList;
            if (title != null && TMDBDataAccessObject.existsByTitle(title)) {
                System.out.println("movie title exists");
                moviesList = this.TMDBDataAccessObject.searchMoviesByTitle(title);
            } 
            else if (title == null) {
                moviesList = this.TMDBDataAccessObject.searchMovies(title, genre, rating);
            } 
            else {
                this.movieSearchPresenter.prepareFailView("No movies with that title.");
                return;
            }

            MovieSearchOutputData movieSearchOutputData = new MovieSearchOutputData(moviesList, false);
            this.movieSearchPresenter.prepareSuccessView(movieSearchOutputData);
        } else {
            System.out.println("Other Error");
            this.movieSearchPresenter.prepareFailView("No movies with that title.");
        }
    }

    private String parseTitle(String title) {
        if (!"".equals(title)) {
            return title;
        }
        else {
            return null;
        }
    }

    private String parseGenre(String genre) {
        if (!"None".equals(genre)) {
            return genre;
        }
        else {
            return null;
        }
    }

    private Integer parseRating(String rating) {
        System.out.println("Rating: " + rating);
        if (rating != null && !rating.isEmpty() && !"None".equals(rating)) {
            try {
                char lastChar = rating.charAt(rating.length() - 1);
                return Integer.parseInt(String.valueOf(lastChar));
            } catch (NumberFormatException e) {
                this.movieSearchPresenter.prepareFailView("Rating must be an integer.");
                return null;
            }
        } else {
            return null;
        }
    }
}

