package use_case.movie_search;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import entity.Movie;

/**
 * The Movie Search Interactor.
 */
public class MovieSearchInteractor implements MovieSearchInputBoundary {
    private final MovieSearchDataAccessInterface tmdbDataAccessObject;
    private final MovieSearchOutputBoundary movieSearchPresenter;

    public MovieSearchInteractor(MovieSearchDataAccessInterface movieSearchDataAccessInterface,
                                 MovieSearchOutputBoundary movieSearchOutputBoundary) {
        this.tmdbDataAccessObject = movieSearchDataAccessInterface;
        this.movieSearchPresenter = movieSearchOutputBoundary;
    }

    @Override
    public void execute(MovieSearchInputData movieSearchInputData) {
        boolean hasError = false;
        String failMessage = null;
        List<Movie> moviesList = null;

        if (tmdbDataAccessObject == null) {
            failMessage = "No movies with that title.";
            hasError = true;
        } 
        else {
            String title = parseTitle(movieSearchInputData.getMovieTitle());
            String genre = parseGenre(movieSearchInputData.getGenre());
            Integer rating = parseRating(movieSearchInputData.getRating());
            List<Integer> keywordIds = parseKeywords(movieSearchInputData.getKeywords());

            failMessage = validateInput(title, genre, rating, keywordIds);
            if (failMessage == null) {
                moviesList = searchMovies(title, genre, rating, keywordIds);
                if (moviesList == null || moviesList.isEmpty()) {
                    failMessage = "No movies found with the given criteria.";
                    hasError = true;
                }
            } 
            else {
                hasError = true;
            }
        }

        if (hasError) {
            handleFailure(failMessage);
        } 
        else {
            handleSuccess(moviesList);
        }
    }

    /**
     * Validates the input.
     * @param title the title of the movie.
     * @param genre the genre of the movie.
     * @param rating the rating of the movie.
     * @param keywordIds the list of keyword ids.
     * @return the error message if the input is invalid; null otherwise.
     */
    public String validateInput(String title, String genre, Integer rating, List<Integer> keywordIds) {
        if (title == null && genre == null && rating == null && keywordIds.isEmpty()) {
            System.out.println("case 1");
            return "No search criteria provided.";
        }
        return null;
    }

    /**
     * Searches for movies based on the given criteria.
     * @param title the title of the movie.
     * @param genre the genre of the movie.
     * @param rating the rating of the movie.
     * @param keywordIds the list of keyword ids.
     * @return the list of movies that satisfy the given criteria.
     */
    public List<Movie> searchMovies(String title, String genre, Integer rating, List<Integer> keywordIds) {
        List<Movie> moviesList = null;
        if (title != null && tmdbDataAccessObject.existsByTitle(title)) {
            System.out.println("movie title exists");
            moviesList = this.tmdbDataAccessObject.searchMoviesByTitle(title);
        } 
        else if (title == null) {
            System.out.println("case 3");
            moviesList = this.tmdbDataAccessObject.searchMovies(title, genre, rating, keywordIds);
        } 
        else {
            System.out.println("case 4");
        }
        return moviesList;
    }

    /**
     * Handles the failure of the search.
     * @param failMessage the message to display.
     */
    public void handleFailure(String failMessage) {
        System.out.println(failMessage);
        this.movieSearchPresenter.prepareFailView(failMessage);
    }

    /**
     *  Handles the success of the search.
     * @param moviesList the list of movies that satisfy the search criteria.
     */
    public void handleSuccess(List<Movie> moviesList) {
        MovieSearchOutputData movieSearchOutputData = new MovieSearchOutputData(moviesList, false);
        this.movieSearchPresenter.prepareSuccessView(movieSearchOutputData);
    }

    /**
     * Parses the title of the movie.
     * @param title the title of the movie.
     * @return the parsed title of the movie.
     */
    public String parseTitle(String title) {
        if (!"".equals(title)) {
            return title;
        } 
        else {
            return null;
        }
    }

    /**
     * Parses the genre of the movie.
     * @param genre the genre of the movie.
     * @return the parsed genre of the movie.
     */
    public String parseGenre(String genre) {
        if (!"None".equals(genre)) {
            return genre;
        } 
        else {
            return null;
        }
    }

    /**
     * Parses the rating of the movie.
     * @param rating the rating of the movie.
     * @return the parsed rating of the movie.
     */
    public Integer parseRating(String rating) {
        System.out.println("Rating: " + rating);
        Integer result = null;
    
        if (rating != null && !rating.isEmpty() && !"None".equals(rating)) {
            try {
                char lastChar = rating.charAt(rating.length() - 1);
                result = Integer.parseInt(String.valueOf(lastChar));
            } 
            catch (NumberFormatException exception) {
                this.movieSearchPresenter.prepareFailView("Rating must be an integer.");
            }
        }
    
        return result;
    }

    /**
     * Parses the keywords of the movie.
     * @param keywords the keywords of the movie.
     * @return the parsed keywords of the movie.
     */
    public List<Integer> parseKeywords(List<String> keywords) {
        Map<String, Integer> keywordMap = loadKeywordMap();
        List<Integer> keywordIds = new ArrayList<>();
        if (keywords != null && !keywords.isEmpty()) {
            for (String keyword : keywords) {
                Integer id = keywordMap.get(keyword);
                if (id != null) {
                    keywordIds.add(id);
                }
            }
        }
        return keywordIds;
    }

    /**
     * Loads the keyword map from the file.
     * @return the keyword map.
     */
    public Map<String, Integer> loadKeywordMap() {
        return loadKeywordMap(false);
    }

    /**
     * Loads the keyword map from the file.
     * @param fail whether to fail the loading or not.
     * @return the keyword map.
     */
    public Map<String, Integer> loadKeywordMap(boolean fail) {
        Map<String, Integer> keywordMap = new HashMap<>();
        String filePath;
        if (fail) {
            filePath = "aosfa[oifjads";
        }
        else {
            filePath = "persistent_data/keyword_ids_11_22_2024.json";
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(line);
                for (String key : jsonObject.keySet()) {
                    keywordMap.put(key, jsonObject.getInt(key));
                }
            }
        } 
        catch (IOException exception) {
            exception.printStackTrace();
        }
        return keywordMap;
    }

    /**
     * Creates a BufferedReader for the given file path.
     * @param filePath the file path.
     * @return the BufferedReader for the given file path.
     * @throws IOException if the file is not found.
     */
    public BufferedReader createBufferedReader(String filePath) throws IOException {
        return new BufferedReader(new FileReader(filePath));
    }
    
}

