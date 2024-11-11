package data_access;

import entity.Movie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import io.github.cdimascio.dotenv.Dotenv;
import use_case.movie_search.MovieSearchDataAccessInterface;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TMDBDataAccessObject implements MovieSearchDataAccessInterface {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String TMDB_API_KEY = dotenv.get("TMDB_API_KEY");
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String SEARCH_MOVIE_ENDPOINT = "/search/movie";
    private static final String GENRE_LIST_ENDPOINT = "/genre/movie/list";

    private final OkHttpClient client = new OkHttpClient();
    private final Map<Integer, String> genreMap = new HashMap<>();

    public TMDBDataAccessObject() {
        populateGenreMap();
        System.out.println("Genre Map: " + genreMap);
    }

    private void populateGenreMap() {
        Request request = new Request.Builder()
                .url(BASE_URL + GENRE_LIST_ENDPOINT + "?api_key=" + TMDB_API_KEY)
                .get()
                .addHeader("accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                
                // Debugging: Print the raw response body
                System.out.println("Response Body: " + responseBody);
                
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray genres = jsonObject.getJSONArray("genres");

                for (int i = 0; i < genres.length(); i++) {
                    JSONObject genreJson = genres.getJSONObject(i);
                    String genreName = genreJson.getString("name");
                    int genreId = genreJson.getInt("id");
                    genreMap.put(genreId, genreName);
                }
            } else {
                // Debugging: Print the response code and message
                System.out.println("Response Code: " + response.code());
                System.out.println("Response Message: " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch genres", e);
        }
    }

    public String getGenreName(int genreId) {
        return genreMap.get(genreId);
    }


    @Override
    public boolean existsByTitle(String title) {
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

                return results.length() > 0;
            } else {
                // Debugging: Print the response code and message
                System.out.println("Response Code: " + response.code());
                System.out.println("Response Message: " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to check if movie exists by title", e);
        }
        return false;
    }

    @Override
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
                    List<String> genreTitles = new ArrayList<>();
                    JSONArray genreIdsJson = movieJson.getJSONArray("genre_ids");
                    for (int j = 0; j < genreIdsJson.length(); j++) {
                        int genreId = genreIdsJson.getInt(j);
                        String genreTitle = genreMap.get(genreId);
                        if (genreTitle != null) {
                            genreTitles.add(genreTitle);
                        }
                    }
                    String releaseDateString = movieJson.optString("release_date", null);
                    Date releaseDate = releaseDateString != null && !releaseDateString.isEmpty() ? parseDate(releaseDateString) : null;
                    double rating = movieJson.getDouble("vote_average");
                    String description = movieJson.getString("overview");
                    String plot = movieJson.getString("overview");

                    movies.add(new Movie(movieTitle, movieID, genreTitles, releaseDate, rating, description, plot));
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
}