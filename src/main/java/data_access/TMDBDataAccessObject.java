package data_access;

import entity.Movie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TMDBDataAccessObject {
    private static final String TMDB_API_READ_KEY = System.getenv("TMDB_API_READ_KEY");
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String SEARCH_MOVIE_ENDPOINT = "/search/movie";

    public List<Movie> searchMoviesByTitle(String title) {
        List<Movie> movies = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL + SEARCH_MOVIE_ENDPOINT + "?query=" + title + "&include_adult=false&language=en-US&page=1")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + TMDB_API_READ_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray results = jsonObject.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject movieJson = results.getJSONObject(i);
                    String movieTitle = movieJson.getString("title");
                    int movieID = movieJson.getInt("id");
                    List<Integer> genreIDs = new ArrayList<>();
                    JSONArray genreIdsJson = movieJson.getJSONArray("genre_ids");
                    for (int j = 0; j < genreIdsJson.length(); j++) {
                        genreIDs.add(genreIdsJson.getInt(j));
                    }
                    Date releaseDate = parseDate(movieJson.getString("release_date"));
                    double rating = movieJson.getDouble("vote_average");
                    String description = movieJson.getString("overview");
                    String plot = movieJson.getString("overview");

                    movies.add(new Movie(movieTitle, movieID, genreIDs, releaseDate, rating, description, plot));
                }
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
}