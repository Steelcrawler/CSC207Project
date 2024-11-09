package data_access;

import entity.Movie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TMDBDataAccessObject {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String TMDB_API_KEY = dotenv.get("TMDB_API_KEY");
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String SEARCH_MOVIE_ENDPOINT = "/search/movie";

    private final OkHttpClient client = new OkHttpClient();

    public List<Movie> searchMoviesByTitle(String title) {
        List<Movie> movies = new ArrayList<>();

        Request request = new Request.Builder()
                .url(BASE_URL + SEARCH_MOVIE_ENDPOINT + "?query=" + title + "&include_adult=false&language=en-US&page=1&api_key=" + TMDB_API_KEY)
                .get()
                .addHeader("accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                
                // Debugging: Print the raw response body
                System.out.println("Response Body: " + responseBody);
                
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray results = jsonObject.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject movieJson = results.getJSONObject(i);
                    String movieTitle = movieJson.getString("title");
                    int movieID = movieJson.getInt("id");
                    List<Integer> genreIds = new ArrayList<>();
                    JSONArray genreIdsJson = movieJson.getJSONArray("genre_ids");
                    for (int j = 0; j < genreIdsJson.length(); j++) {
                        genreIds.add(genreIdsJson.getInt(j));
                    }
                    String releaseDateString = movieJson.optString("release_date", null);
                    Date releaseDate = releaseDateString != null && !releaseDateString.isEmpty() ? parseDate(releaseDateString) : null;
                    double rating = movieJson.getDouble("vote_average");
                    String description = movieJson.getString("overview");
                    String plot = movieJson.getString("overview");

                    movies.add(new Movie(movieTitle, movieID, genreIds, releaseDate, rating, description, plot));
                }
            } else {
                // Debugging: Print the response code and message
                System.out.println("Response Code: " + response.code());
                System.out.println("Response Message: " + response.message());
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Failed to search movies by title", e);
        }

        return movies;
    }

    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateString);
    }

//     public static void main(String[] args) {
//         // Debugging: Print the API key
//         System.out.println("TMDB API Key: " + TMDB_API_KEY);

//         TMDBDataAccessObject tmdbDataAccessObject = new TMDBDataAccessObject();
//         List<Movie> movies = tmdbDataAccessObject.searchMoviesByTitle("Harry Potter");

//         for (Movie movie : movies) {
//             System.out.println("Title: " + movie.getTitle());
//             System.out.println("Movie ID: " + movie.getMovieID());
//             System.out.println("Genre IDs: " + movie.getGenre());
//             System.out.println("Release Date: " + movie.getReleaseDate());
//             System.out.println("Rating: " + movie.getRating());
//             System.out.println("Description: " + movie.getDescription());
//             System.out.println("Plot: " + movie.getPlot());
//             System.out.println();
//         }
//     } 
}