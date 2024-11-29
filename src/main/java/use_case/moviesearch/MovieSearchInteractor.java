package use_case.moviesearch;
import entity.Movie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;


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
            // System.out.println("Not null");
            String title = parseTitle(movieSearchInputData.getMovieTitle());
            String genre = parseGenre(movieSearchInputData.getGenre());
            Integer rating = parseRating(movieSearchInputData.getRating());
            List<Integer> keywordIds = parseKeywords(movieSearchInputData.getKeywords());
            System.out.println("Title: " + title);
    
            if (title == null && genre == null && rating == null && keywordIds.isEmpty()) {
                System.out.println("case 1");
                this.movieSearchPresenter.prepareFailView("No search criteria provided.");
                return;
            }
            List<Movie> moviesList;
            if (title != null && TMDBDataAccessObject.existsByTitle(title)) {
                System.out.println("movie title exists");
                moviesList = this.TMDBDataAccessObject.searchMoviesByTitle(title);
            } 
            else if (title == null) {
                System.out.println("case 3");
                moviesList = this.TMDBDataAccessObject.searchMovies(title, genre, rating, keywordIds);
            } 
            else {
                System.out.println("case 4");
                this.movieSearchPresenter.prepareFailView("No movies with that title.");
                return;
            }
            if (moviesList.isEmpty()) {
                System.out.println("case 5");
                this.movieSearchPresenter.prepareFailView("No movies found with the given criteria.");
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

    private List<Integer> parseKeywords(List<String> keywords) {
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

    private Map<String, Integer> loadKeywordMap() {
        Map<String, Integer> keywordMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("persistent_data/keyword_ids_11_22_2024.json"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(line);
                for (String key : jsonObject.keySet()) {
                    keywordMap.put(key, jsonObject.getInt(key));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keywordMap;
    }
}

