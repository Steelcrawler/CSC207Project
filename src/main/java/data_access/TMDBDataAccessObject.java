package data_access;

import entity.Movie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import io.github.cdimascio.dotenv.Dotenv;
import use_case.movie_search.MovieSearchDataAccessInterface;
import use_case.movieinfo.MovieInfoDataAccessInterface;
import use_case.recommendation.RecommendationDataAccessInterface;

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

public class TMDBDataAccessObject implements MovieSearchDataAccessInterface, MovieInfoDataAccessInterface, RecommendationDataAccessInterface {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String TMDB_API_KEY = dotenv.get("TMDB_API_KEY");
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String SEARCH_MOVIE_ENDPOINT = "/search/movie";
    private static final String GENRE_LIST_ENDPOINT = "/genre/movie/list";
    private static final String MOVIE_REVIEWS_ENDPOINT = "/movie/{movie_id}/reviews";
    private static final String VIDEO_ENDPOINT = "/movie/{movie_id}/videos";
    private static final String DETAILS_ENDPOINT = "/movie/{movie_id}";
    private static final String DISCOVER_MOVIE_ENDPOINT = "/discover/movie";
    private static final String SIMILAR_MOVIE_ENDPOINT = "/movie/{movie_id}/similar";
    private static final String KEYWORDS_MOVIE_ENDPOINT = "/movie/{movie_id}/keywords";

    private final OkHttpClient client = new OkHttpClient();
    private final Map<Integer, String> genreMap = new HashMap<>();

    public TMDBDataAccessObject() {
        populateGenreMap();
        // System.out.println("Genre Map: " + genreMap);
    }

    public void downloadKeywords(String date) {
        String url = "http://files.tmdb.org/p/exports/keyword_ids_" + date + ".json.gz";
        System.out.println("Downloading keywords from " + url);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                InputStream inputStream = response.body().byteStream();
                GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
                String outputFilePath = "keyword_ids_" + date + ".json";
                String keywordsFilePath = "keywords_" + date + ".txt";
                Map<String, Integer> keywordMap = new TreeMap<>(); // Use TreeMap for sorted keys
                List<String> keywordsList = new ArrayList<>();

                try (FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);
                    BufferedWriter keywordsWriter = new BufferedWriter(new FileWriter(keywordsFilePath));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(gzipInputStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        JSONObject jsonObject = new JSONObject(line);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
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
            } else {
                System.out.println("Failed to download keywords. Response Code: " + response.code());
                System.out.println("Response Message: " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to download keywords", e);
        }
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
                // System.out.println("Response Body: " + responseBody);
                
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

    public Integer getGenreId(String genreName) {
        for (Map.Entry<Integer, String> entry : genreMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(genreName)) {
                return entry.getKey();
            }
        }
        return null;
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
                // System.out.println("Response Body: " + responseBody);
                
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

    public List<String> getUserReviews(int movieID) {
        List<String> userReviews = new ArrayList<>();

        String url = BASE_URL + MOVIE_REVIEWS_ENDPOINT.replace("{movie_id}", String.valueOf(movieID)) + "?api_key=" + TMDB_API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                
                // Debugging: Print the raw response body
                // System.out.println("Response Body: " + responseBody);
                
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray results = jsonObject.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject reviewJson = results.getJSONObject(i);
                    String reviewContent = reviewJson.getString("content");
                    userReviews.add(reviewContent);
                }
            } else {
                // Debugging: Print the response code and message
                System.out.println("Response Code: " + response.code());
                System.out.println("Response Message: " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch user reviews", e);
        }

        return userReviews;
    }

    private String getTrailer(int movieID) {
        String url = BASE_URL + VIDEO_ENDPOINT.replace("{movie_id}", String.valueOf(movieID)) + "?api_key=" + TMDB_API_KEY;

        // System.out.println("Request URL: " + url);

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                
                // Debugging: Print the raw response body
                // System.out.println("Response Body: " + responseBody);
                
                JSONObject jsonObject = new JSONObject(responseBody);
                JSONArray results = jsonObject.getJSONArray("results");
                if (results.length() > 0) {
                    JSONObject videoJson = results.getJSONObject(0);
                    String videoKey = videoJson.getString("key");
                    return "https://www.youtube.com/watch?v=" + videoKey;
                } else {
                    System.out.println("No videos found for movie ID: " + movieID);
                }
            } else {
                // Debugging: Print the response code and message
                System.out.println("Response Code: " + response.code());
                System.out.println("Response Message: " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch videos", e);
        }

        return "";

    }

    public Movie getMovieByID(int iD) {
        String url = BASE_URL + DETAILS_ENDPOINT.replace("{movie_id}", String.valueOf(iD)) + "?api_key=" + TMDB_API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                // Debugging: Print the raw response body
                 System.out.println("Response Body: " + responseBody);

                JSONObject jsonObject = new JSONObject(responseBody);
                String movieTitle = jsonObject.getString("title");
                int movieID = jsonObject.getInt("id");

                List<String> genreTitles = new ArrayList<>();
                JSONArray genreIdsJson = jsonObject.getJSONArray("genres");
                for (int j = 0; j < genreIdsJson.length(); j++) {
                    JSONObject genreObject = genreIdsJson.getJSONObject(j);
                    String genreName = genreObject.getString("name");
                    genreTitles.add(genreName);
                }
                String releaseDateString = jsonObject.optString("release_date", null);
                Date releaseDate = releaseDateString != null && !releaseDateString.isEmpty() ? parseDate(releaseDateString) : null;
                double rating = jsonObject.getDouble("vote_average");
                String plot = jsonObject.getString("overview");
                String posterPath = "https://image.tmdb.org/t/p/w500" + jsonObject.optString("poster_path", "");
                List<String> userReviews = getUserReviews(movieID);
                String trailerLink = getTrailer(movieID);
                Movie movie = new Movie(movieTitle, movieID, genreTitles, releaseDate, rating, plot, posterPath, userReviews, trailerLink);
                return movie;
            }
            else {
                // Debugging: Print the response code and message
                    System.out.println("Response Code: " + response.code());
                    System.out.println("Response Message: " + response.message());
            }
        }
        catch (IOException | ParseException e) {
            throw new RuntimeException("Failed to search movies by title", e);
        }
        return null;
    }

    @Override
    public List<Movie> searchMovies(String title, String genre, Integer rating, List<Integer> keywordIDs) {
        List<Movie> movies = new ArrayList<>();
        StringBuilder urlBuilder = new StringBuilder(BASE_URL + DISCOVER_MOVIE_ENDPOINT + "?api_key=" + TMDB_API_KEY);

        if (title != null && !title.isEmpty()) {
            urlBuilder.append("&query=").append(title);
        }
        if (genre != null && !genre.isEmpty()) {
            Integer genreId = getGenreId(genre);
            if (genreId != null) {
                urlBuilder.append("&with_genres=").append(genreId);
            }
        }
        if (rating != null && rating >= 0 && rating <= 10) {
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
                .addHeader("accept", "application/json")
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
                    double ratingValue = movieJson.getDouble("vote_average");
                    String plot = movieJson.getString("overview");
                    String posterPath = movieJson.optString("poster_path", "");
                    List<String> userReviews = getUserReviews(movieID);
                    String trailerLink = getTrailer(movieID);
    
                    movies.add(new Movie(movieTitle, movieID, genreTitles, releaseDate, ratingValue, plot, posterPath, userReviews, trailerLink));
                }
            } else {
                System.out.println("Response Code: " + response.code());
                System.out.println("Response Message: " + response.message());
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Failed to search movies", e);
        }
    
        return movies;
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
                // System.out.println("Response Body: " + responseBody);
                
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
                        String genreTitle = getGenreName(genreId);
                        if (genreTitle != null) {
                            genreTitles.add(genreTitle);
                        }
                    }
                    String releaseDateString = movieJson.optString("release_date", null);
                    Date releaseDate = releaseDateString != null && !releaseDateString.isEmpty() ? parseDate(releaseDateString) : null;
                    double rating = movieJson.getDouble("vote_average");
                    String plot = movieJson.getString("overview");
                    String posterPath = movieJson.optString("poster_path", "");
                    List<String> userReviews = getUserReviews(movieID);
                    String trailerLink = getTrailer(movieID);
                    movies.add(new Movie(movieTitle, movieID, genreTitles, releaseDate, rating, plot, posterPath, userReviews, trailerLink));
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

    @Override
        public List<Movie> searchRecommendations(List<Integer> movies) {
        List<Movie> moviesOutputList = new ArrayList<>();

        Request request = new Request.Builder()
                .url(BASE_URL + SIMILAR_MOVIE_ENDPOINT.replace("{movie_id}", String.valueOf(movies.get(0))) + "?api_key=" + TMDB_API_KEY)
                .get()
                .addHeader("accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();

                // Debugging: Print the raw response body
//                System.out.println("Response Body: " + responseBody);

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
                        String genreTitle = getGenreName(genreId);
                        if (genreTitle != null) {
                            genreTitles.add(genreTitle);
                        }
                    }
                    String releaseDateString = movieJson.optString("release_date", null);
                    Date releaseDate = releaseDateString != null && !releaseDateString.isEmpty() ? parseDate(releaseDateString) : null;
                    double rating = movieJson.getDouble("vote_average");
                    String plot = movieJson.getString("overview");
                    String posterPath = "https://image.tmdb.org/t/p/w500" + movieJson.optString("poster_path", "");
                    List<String> userReviews = getUserReviews(movieID);
                    String trailerLink = getTrailer(movieID);
                    moviesOutputList.add(new Movie(movieTitle, movieID, genreTitles, releaseDate, rating, plot, posterPath, userReviews, trailerLink));
                }
            } else {
                // Debugging: Print the response code and message
                System.out.println("Response Code: " + response.code());
                System.out.println("Response Message: " + response.message());
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Failed to search for similar movies", e);
        }
        return moviesOutputList;
    }

    public List<Integer> getKeywordIDs(int movieID) {
        List<Integer> keywordIDs = new ArrayList<>();

        String url = BASE_URL + KEYWORDS_MOVIE_ENDPOINT.replace("{movie_id}", String.valueOf(movieID)) + "?api_key=" + TMDB_API_KEY;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
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
            } else {
                // Debugging: Print the response code and message
                System.out.println("Response Code: " + response.code());
                System.out.println("Response Message: " + response.message());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch user reviews", e);
        }

        return keywordIDs;
    }

    @Override
    public List<Movie> searchMoviesByKeywordsHelper(List<Integer> keywords) {

//        return searchMovies(null, null, null, keywords);
        List<Movie> movies = new ArrayList<>();
        StringBuilder urlBuilder = new StringBuilder(BASE_URL + DISCOVER_MOVIE_ENDPOINT + "?api_key=" + TMDB_API_KEY);

//        if (genresList != null && !genresList.isEmpty()) {
//            Integer genreId = getGenreId(genre);
//            if (genreId != null) {
//                urlBuilder.append("&with_genres=").append(genreId);
//            }
//        }

        if (keywords != null && !keywords.isEmpty()) {
            String keywordIDsString = keywords.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining("|"));
            urlBuilder.append("&with_keywords=").append(keywordIDsString);
        }


        Request request = new Request.Builder()
                .url(urlBuilder.toString())
                .get()
                .addHeader("accept", "application/json")
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
                    double ratingValue = movieJson.getDouble("vote_average");
                    String plot = movieJson.getString("overview");
                    String posterPath = "https://image.tmdb.org/t/p/w500" + movieJson.optString("poster_path", "");
                    List<String> userReviews = getUserReviews(movieID);
                    String trailerLink = getTrailer(movieID);

                    movies.add(new Movie(movieTitle, movieID, genreTitles, releaseDate, ratingValue, plot, posterPath, userReviews, trailerLink));
                }
            } else {
                System.out.println("Response Code: " + response.code());
                System.out.println("Response Message: " + response.message());
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException("Failed to search movies", e);
        }

        return movies;
    }


    private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateString);
    }

    public static void main(String[] args) {
        TMDBDataAccessObject tmdbDataAccessObject = new TMDBDataAccessObject();
        tmdbDataAccessObject.downloadKeywords("11_22_2024");
    }
}