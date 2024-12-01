package data_access;

// STANDARD_JAVA_PACKAGE (java.*)
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Movie;
import entity.MovieFactory;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import use_case.movie_info.MovieInfoDataAccessInterface;
import use_case.movie_search.MovieSearchDataAccessInterface;
import use_case.recommendation.RecommendationDataAccessInterface;

/**
 * The TMDB API DAO class.
 */
public class TMDBDataAccessObject implements MovieSearchDataAccessInterface, MovieInfoDataAccessInterface,
        RecommendationDataAccessInterface {
    private static final Dotenv DOTENV = Dotenv.load();
    private static final String TMDB_API_KEY = DOTENV.get("TMDB_API_KEY");
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String SEARCH_MOVIE_ENDPOINT = "/search/movie";
    private static final String GENRE_LIST_ENDPOINT = "/genre/movie/list";
    private static final String MOVIE_REVIEWS_ENDPOINT = "/movie/{movie_id}/reviews";
    private static final String VIDEO_ENDPOINT = "/movie/{movie_id}/videos";
    private static final String DETAILS_ENDPOINT = "/movie/{movie_id}";
    private static final String DISCOVER_MOVIE_ENDPOINT = "/discover/movie";
    private static final String SIMILAR_MOVIE_ENDPOINT = "/movie/{movie_id}/similar";
    private static final String KEYWORDS_MOVIE_ENDPOINT = "/movie/{movie_id}/keywords";
    private static final String POSTER_ENDPOINT = "https://image.tmdb.org/t/p/w500";
    private static final String ACCEPT = "accept";
    private static final String VALUE = "value";
    private static final String ID_STRING = "id";
    private static final String NAME = "name";
    private static final String RESPONSE_MESSAGE = "Response Message: ";
    private static final String API_KEY_STRING = "?api_key=";
    private static final String RESPONSE_CODE = "Response Code: ";
    private static final String RESULTS = "results";
    private static final String MOVIE_ID_TO_REPLACE = "{movie_id}";
    private static final String TITLE_STRING = "title";
    private static final String RELEASE_DATE = "release_date";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String OVERVIEW = "overview";
    private static final String POSTER_PATH_STRING = "poster_path";
    private static final String GENRE_IDS = "genre_ids";
    private static final int MAX_RESULTS = 10;

    private final OkHttpClient client = new OkHttpClient();
    private final Map<Integer, String> genreMap = new HashMap<>();
    private final MovieFactory movieFactory = new MovieFactory();

    public TMDBDataAccessObject() {
        populateGenreMap();
    }

    /**
     * Downloads the keywords from the TMDB API.
     * @param date the date to dowload the keywords from.
     * @throws RuntimeException if the keywords cannot be downloaded.
     */
    public void downloadKeywords(String date) {
        String url = "http://files.tmdb.org/p/exports/keyword_ids_" + date + ".json.gz";
        System.out.println("Downloading keywords from " + url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(ACCEPT, VALUE)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                InputStream inputStream = response.body().byteStream();
                GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
                String outputFilePath = "keyword_ids_" + date + ".json";
                String keywordsFilePath = "keywords_" + date + ".txt";
                Map<String, Integer> keywordMap = new TreeMap<>(); 
                List<String> keywordsList = new ArrayList<>();

                try (FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);
                    BufferedWriter keywordsWriter = new BufferedWriter(new FileWriter(keywordsFilePath));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        JSONObject jsonObject = new JSONObject(line);
                        int id = jsonObject.getInt(ID_STRING);
                        String name = jsonObject.getString(NAME);
                        keywordMap.put(name, id);
                        keywordsList.add(name);
                    }

                    // Write the keyword map to the JSON file
                    fileOutputStream.write(new JSONObject(keywordMap).toString().getBytes());

                    // Sort the keywords list alphabetically
                    Collections.sort(keywordsList);

                    // Write the sorted keywords list to the text file
                    for (String keyword : keywordsList) {
                        keywordsWriter.write(keyword);
                        keywordsWriter.newLine();
                    }
                }
                System.out.println("Keywords downloaded and saved to " + outputFilePath);
                System.out.println("Keywords list saved to " + keywordsFilePath);
            }
            else {
                System.out.println("Failed to download keywords. Response Code: " + response.code());
                System.out.println(RESPONSE_MESSAGE + response.message());
            }
        } 
        catch (IOException exception) {
            throw new RuntimeException("Failed to download keywords", exception);
        }
    }

    /**
     * Populates the genre map with the genres from the TMDB API.
     * @throws RuntimeException if the genres cannot be fetched.
     */
    private void populateGenreMap() {
        Request request = new Request.Builder()
                .url(BASE_URL + GENRE_LIST_ENDPOINT + API_KEY_STRING + TMDB_API_KEY)
                .get()
                .addHeader(ACCEPT, VALUE)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray genres = jsonObject.getJSONArray("genres");

                for (int i = 0; i < genres.length(); i++) {
                    JSONObject genreJson = genres.getJSONObject(i);
                    String genreName = genreJson.getString(NAME);
                    int genreId = genreJson.getInt(ID_STRING);
                    genreMap.put(genreId, genreName);
                }
            }
            else {
                System.out.println(RESPONSE_CODE + response.code());
                System.out.println(RESPONSE_MESSAGE + response.message());
            }
        }
        catch (IOException exception) {
            throw new RuntimeException("Failed to fetch genres", exception);
        }
    }

    /**
     * Returns the genre name for the given genre ID.
     * @param genreId the genre ID.
     * @return the genre name.
     */
    public String getGenreName(int genreId) {
        return genreMap.get(genreId);
    }

    /**
     * Returns the genre ID for the given genre name.
     * @param genreName the genre name.
     * @return the genre ID.
     */
    public Integer getGenreId(String genreName) {
        for (Map.Entry<Integer, String> entry : genreMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(genreName)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Checks if a movie exists by title.
     * @param title the movie title.
     * @return true if the movie exists, false otherwise.
     * @throws RuntimeException if the movie existence cannot be checked.
     */
    @Override
    public boolean existsByTitle(String title) {
        Request request = new Request.Builder()
                .url(BASE_URL + SEARCH_MOVIE_ENDPOINT + "?query=" + title
                        + "&include_adult=false&language=en-US&page=1&api_key=" + TMDB_API_KEY)
                .get()
                .addHeader(ACCEPT, VALUE)
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray results = jsonObject.getJSONArray(RESULTS);

                return results.length() > 0;
            } 
            else {
                System.out.println(RESPONSE_CODE + response.code());
                System.out.println(RESPONSE_MESSAGE + response.message());
            }
        } 
        catch (IOException exception) {
            throw new RuntimeException("Failed to check if movie exists by title", exception);
        }
        return false;
    }

    /**
     * Returns the user reviews for the given movie ID.
     * @param movieID the movie ID.
     * @return the user reviews.
     * @throws RuntimeException if the user reviews cannot be fetched.
     */
    public List<String> getUserReviews(int movieID) {
        List<String> userReviews = new ArrayList<>();

        String url = BASE_URL + MOVIE_REVIEWS_ENDPOINT.replace(MOVIE_ID_TO_REPLACE, String.valueOf(movieID))
                + API_KEY_STRING + TMDB_API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(ACCEPT, VALUE)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray results = jsonObject.getJSONArray(RESULTS);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject reviewJson = results.getJSONObject(i);
                    String reviewContent = reviewJson.getString("content");
                    userReviews.add(reviewContent);
                }
            } 
            else {
                System.out.println(RESPONSE_CODE + response.code());
                System.out.println(RESPONSE_MESSAGE + response.message());
            }
        } 
        catch (IOException exception) {
            throw new RuntimeException("Failed to fetch user reviews", exception);
        }

        return userReviews;
    }

    /**
     * Returns the trailer for the given movie ID.
     * @param movieID the movie ID.
     * @return the trailer.
     * @throws IOException if there is an error fetching it
     * @throws RuntimeException if there is an error fetching it.
     */
    private String getTrailer(int movieID) {
        String url = BASE_URL + VIDEO_ENDPOINT.replace(MOVIE_ID_TO_REPLACE, String.valueOf(movieID))
                + API_KEY_STRING + TMDB_API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(ACCEPT, VALUE)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray results = jsonObject.getJSONArray(RESULTS);
                if (results.length() > 0) {
                    JSONObject videoJson = results.getJSONObject(0);
                    String videoKey = videoJson.getString("key");
                    return "https://www.youtube.com/watch?v=" + videoKey;
                }
                else {
                    System.out.println("No videos found for movie ID: " + movieID);
                }
            } 
            else {
                System.out.println(RESPONSE_CODE + response.code());
                System.out.println(RESPONSE_MESSAGE + response.message());
            }
        } 
        catch (IOException exception) {
            throw new RuntimeException("Failed to fetch videos", exception);
        }

        return "";
    }

    /**
     * Returns the movie by ID.
     * @param id the movie ID.
     * @return the movie.
     * @throws RuntimeException if the movie cannot be fetched.
     */
    public Movie getMovieByID(int id) {
        String url = BASE_URL + DETAILS_ENDPOINT.replace(MOVIE_ID_TO_REPLACE, String.valueOf(id))
                + API_KEY_STRING + TMDB_API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(ACCEPT, VALUE)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                JSONObject jsonObject = new JSONObject(responseBody);
                String movieTitle = jsonObject.getString(TITLE_STRING);
                int movieID = jsonObject.getInt(ID_STRING);

                List<String> genreTitles = new ArrayList<>();
                JSONArray genreIdsJson = jsonObject.getJSONArray("genres");
                for (int j = 0; j < genreIdsJson.length(); j++) {
                    JSONObject genreObject = genreIdsJson.getJSONObject(j);
                    String genreName = genreObject.getString(NAME);
                    genreTitles.add(genreName);
                }
                String releaseDateString = jsonObject.optString(RELEASE_DATE, null);
                Date releaseDate = releaseDateString != null
                        && !releaseDateString.isEmpty() ? parseDate(releaseDateString) : null;
                double rating = jsonObject.getDouble(VOTE_AVERAGE);
                String plot = jsonObject.getString(OVERVIEW);
                String posterPath = POSTER_ENDPOINT
                        + jsonObject.optString(POSTER_PATH_STRING, "");
                List<String> userReviews = getUserReviews(movieID);
                String trailerLink = getTrailer(movieID);
                return movieFactory.create(movieTitle, movieID, genreTitles, releaseDate, rating,
                        plot, posterPath, userReviews, trailerLink);
            } 
            else {
                System.out.println(RESPONSE_CODE + response.code());
                System.out.println(RESPONSE_MESSAGE + response.message());
            }
        } 
        catch (IOException | ParseException exception) {
            throw new RuntimeException("Failed to search movies by title", exception);
        }
        return null;
    }

    @Override
    public List<Movie> searchMovies(String title, String genre, Integer rating, List<Integer> keywordIDs) {
        List<Movie> movies = new ArrayList<>();
        StringBuilder urlBuilder = new StringBuilder(BASE_URL + DISCOVER_MOVIE_ENDPOINT
                + API_KEY_STRING + TMDB_API_KEY);

        if (title != null && !title.isEmpty()) {
            urlBuilder.append("&query=").append(title);
        }
        if (genre != null && !genre.isEmpty()) {
            Integer genreId = getGenreId(genre);
            if (genreId != null) {
                urlBuilder.append("&with_genres=").append(genreId);
            }
        }
        if (rating != null && rating >= 0 && rating <= MAX_RESULTS) {
            urlBuilder.append("&vote_average.gte=").append(rating);
        }
        if (keywordIDs != null && !keywordIDs.isEmpty()) {
            String keywordIDsString = keywordIDs.stream()
                                                .map(String::valueOf)
                                                .collect(Collectors.joining(","));
            urlBuilder.append("&with_keywords=").append(keywordIDsString);
        }

        Request request = new Request.Builder()
                .url(urlBuilder.toString())
                .get()
                .addHeader(ACCEPT, VALUE)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray results = jsonObject.getJSONArray(RESULTS);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject movieJson = results.getJSONObject(i);
                    String movieTitle = movieJson.getString(TITLE_STRING);
                    int movieID = movieJson.getInt(ID_STRING);
                    List<String> genreTitles = new ArrayList<>();
                    JSONArray genreIdsJson = movieJson.getJSONArray(GENRE_IDS);
                    for (int j = 0; j < genreIdsJson.length(); j++) {
                        int genreId = genreIdsJson.getInt(j);
                        String genreTitle = genreMap.get(genreId);
                        if (genreTitle != null) {
                            genreTitles.add(genreTitle);
                        }
                    }
                    String releaseDateString = movieJson.optString(RELEASE_DATE, null);
                    Date releaseDate = releaseDateString != null
                            && !releaseDateString.isEmpty() ? parseDate(releaseDateString) : null;
                    double ratingValue = movieJson.getDouble(VOTE_AVERAGE);
                    String plot = movieJson.getString(OVERVIEW);
                    String posterPath = movieJson.optString(POSTER_PATH_STRING, "");
                    List<String> userReviews = getUserReviews(movieID);
                    String trailerLink = getTrailer(movieID);
    
                    movies.add(movieFactory.create(movieTitle, movieID, genreTitles, releaseDate,
                            ratingValue, plot, posterPath, userReviews, trailerLink));
                }
            } 
            else {
                System.out.println(RESPONSE_CODE + response.code());
                System.out.println(RESPONSE_MESSAGE + response.message());
            }
        } 
        catch (IOException | ParseException exception) {
            throw new RuntimeException("Failed to search movies", exception);
        }
    
        return movies;
    }

    @Override
    public List<Movie> searchMoviesByTitle(String title) {
        List<Movie> movies = new ArrayList<>();

        Request request = new Request.Builder()
                .url(BASE_URL + SEARCH_MOVIE_ENDPOINT + "?query=" + title
                        + "&include_adult=false&language=en-US&page=1&api_key=" + TMDB_API_KEY)
                .get()
                .addHeader(ACCEPT, VALUE)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray results = jsonObject.getJSONArray(RESULTS);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject movieJson = results.getJSONObject(i);
                    String movieTitle = movieJson.getString(TITLE_STRING);
                    int movieID = movieJson.getInt(ID_STRING);
                    List<String> genreTitles = new ArrayList<>();
                    JSONArray genreIdsJson = movieJson.getJSONArray(GENRE_IDS);
                    for (int j = 0; j < genreIdsJson.length(); j++) {
                        int genreId = genreIdsJson.getInt(j);
                        String genreTitle = getGenreName(genreId);
                        if (genreTitle != null) {
                            genreTitles.add(genreTitle);
                        }
                    }
                    String releaseDateString = movieJson.optString(RELEASE_DATE, null);
                    Date releaseDate = releaseDateString != null
                            && !releaseDateString.isEmpty() ? parseDate(releaseDateString) : null;
                    double rating = movieJson.getDouble(VOTE_AVERAGE);
                    String plot = movieJson.getString(OVERVIEW);
                    String posterPath = movieJson.optString(POSTER_PATH_STRING, "");
                    List<String> userReviews = getUserReviews(movieID);
                    String trailerLink = getTrailer(movieID);
                    movies.add(movieFactory.create(movieTitle, movieID, genreTitles, releaseDate,
                            rating, plot, posterPath, userReviews, trailerLink));
                }
            } 
            else {
                System.out.println(RESPONSE_CODE + response.code());
                System.out.println(RESPONSE_MESSAGE + response.message());
            }
        } 
        catch (IOException | ParseException exception) {
            throw new RuntimeException("Failed to search movies by title", exception);
        }

        return movies;
    }

    @Override
        public List<Movie> searchRecommendations(List<Integer> movies) {
        List<Movie> moviesOutputList = new ArrayList<>();

        Request request = new Request.Builder()
                .url(BASE_URL + SIMILAR_MOVIE_ENDPOINT.replace(MOVIE_ID_TO_REPLACE,
                        String.valueOf(movies.get(0))) + API_KEY_STRING + TMDB_API_KEY)
                .get()
                .addHeader(ACCEPT, VALUE)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray results = jsonObject.getJSONArray(RESULTS);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject movieJson = results.getJSONObject(i);
                    String movieTitle = movieJson.getString(TITLE_STRING);
                    int movieID = movieJson.getInt(ID_STRING);
                    List<String> genreTitles = new ArrayList<>();
                    JSONArray genreIdsJson = movieJson.getJSONArray(GENRE_IDS);
                    for (int j = 0; j < genreIdsJson.length(); j++) {
                        int genreId = genreIdsJson.getInt(j);
                        String genreTitle = getGenreName(genreId);
                        if (genreTitle != null) {
                            genreTitles.add(genreTitle);
                        }
                    }
                    String releaseDateString = movieJson.optString(RELEASE_DATE, null);
                    Date releaseDate = releaseDateString != null
                            && !releaseDateString.isEmpty() ? parseDate(releaseDateString) : null;
                    double rating = movieJson.getDouble(VOTE_AVERAGE);
                    String plot = movieJson.getString(OVERVIEW);
                    String posterPath = POSTER_ENDPOINT + movieJson.optString(POSTER_PATH_STRING, "");
                    List<String> userReviews = getUserReviews(movieID);
                    String trailerLink = getTrailer(movieID);
                    moviesOutputList.add(movieFactory.create(movieTitle, movieID, genreTitles, releaseDate,
                            rating, plot, posterPath, userReviews, trailerLink));
                }
            }
            else {
                // Debugging: Print the response code and message
                System.out.println(RESPONSE_CODE + response.code());
                System.out.println(RESPONSE_MESSAGE + response.message());
            }
        }
        catch (IOException | ParseException exception) {
            throw new RuntimeException("Failed to search for similar movies", exception);
        }
        return moviesOutputList;
    }

    /**
     * Returns the keyword IDs for the given movie ID.
     * @param movieID the movie ID.
     * @return the keyword IDs.
     * @throws RuntimeException if the keyword IDs cannot be fetched.
     */
    public List<Integer> getKeywordIDs(int movieID) {
        List<Integer> keywordIDs = new ArrayList<>();

        String url = BASE_URL + KEYWORDS_MOVIE_ENDPOINT.replace(MOVIE_ID_TO_REPLACE, String.valueOf(movieID))
                + API_KEY_STRING + TMDB_API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader(ACCEPT, VALUE)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                // Debugging: Print the raw response body
                System.out.println("Response Body: " + responseBody);

                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray results = jsonObject.getJSONArray("keywords");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject keywordsJson = results.getJSONObject(i);
                    Integer keywordID = (Integer) keywordsJson.get("id");
                    keywordIDs.add(keywordID);
                }
            } 
            else {
                // Debugging: Print the response code and message
                System.out.println(RESPONSE_CODE + response.code());
                System.out.println(RESPONSE_MESSAGE + response.message());
            }
        } 
        catch (IOException exception) {
            throw new RuntimeException("Failed to fetch user reviews", exception);
        }
        return keywordIDs;
    }

    @Override
    public List<Movie> searchMoviesByKeywordsHelper(List<Integer> keywords) {
        List<Movie> movies = new ArrayList<>();
        StringBuilder urlBuilder = new StringBuilder(BASE_URL + DISCOVER_MOVIE_ENDPOINT
                + API_KEY_STRING + TMDB_API_KEY);

        if (keywords != null && !keywords.isEmpty()) {
            String keywordIDsString = keywords.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("|"));
            urlBuilder.append("&with_keywords=").append(keywordIDsString);
        }

        Request request = new Request.Builder()
                .url(urlBuilder.toString())
                .get()
                .addHeader(ACCEPT, VALUE)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray results = jsonObject.getJSONArray(RESULTS);

                for (int i = 0; i < results.length(); i++) {
                    JSONObject movieJson = results.getJSONObject(i);
                    String movieTitle = movieJson.getString(TITLE_STRING);
                    int movieID = movieJson.getInt(ID_STRING);
                    List<String> genreTitles = new ArrayList<>();
                    JSONArray genreIdsJson = movieJson.getJSONArray(GENRE_IDS);
                    for (int j = 0; j < genreIdsJson.length(); j++) {
                        int genreId = genreIdsJson.getInt(j);
                        String genreTitle = genreMap.get(genreId);
                        if (genreTitle != null) {
                            genreTitles.add(genreTitle);
                        }
                    }
                    String releaseDateString = movieJson.optString(RELEASE_DATE, null);
                    Date releaseDate = releaseDateString != null
                            && !releaseDateString.isEmpty() ? parseDate(releaseDateString) : null;
                    double ratingValue = movieJson.getDouble(VOTE_AVERAGE);
                    String plot = movieJson.getString(OVERVIEW);
                    String posterPath = POSTER_ENDPOINT + movieJson.optString(POSTER_PATH_STRING, "");
                    List<String> userReviews = getUserReviews(movieID);
                    String trailerLink = getTrailer(movieID);

                    movies.add(movieFactory.create(movieTitle, movieID, genreTitles, releaseDate,
                            ratingValue, plot, posterPath, userReviews, trailerLink));
                }
            } 
            else {
                System.out.println(RESPONSE_CODE + response.code());
                System.out.println(RESPONSE_MESSAGE + response.message());
            }
        } 
        catch (IOException | ParseException exception) {
            throw new RuntimeException("Failed to search movies", exception);
        }

        return movies;
    }

    /**
     * Parses the date string.
     * @param dateString the date string.
     * @return the date.
     * @throws ParseException if the date string cannot be parsed.
     */
    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateString);
    }

    /**
     * This downloads the keywords list.
     * @param args java tings.
     */
    public static void main(String[] args) {
        TMDBDataAccessObject tmdbDataAccessObject = new TMDBDataAccessObject();
        tmdbDataAccessObject.downloadKeywords("11_22_2024");
    }
}
