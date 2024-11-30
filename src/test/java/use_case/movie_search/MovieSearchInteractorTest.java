package use_case.movie_search;

import entity.Movie;
import use_case.movie_search.MovieSearchDataAccessInterface;
import use_case.movie_search.MovieSearchInputData;
import use_case.movie_search.MovieSearchInteractor;
import use_case.movie_search.MovieSearchOutputBoundary;
import use_case.movie_search.MovieSearchOutputData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MovieSearchInteractorTest {

    private MovieSearchDataAccessInterface mockDataAccess;
    private MovieSearchOutputBoundary mockPresenter;
    private MovieSearchInteractor interactor;

    @BeforeEach
    void setUp() {
        mockDataAccess = mock(MovieSearchDataAccessInterface.class);
        mockPresenter = mock(MovieSearchOutputBoundary.class);
        interactor = new MovieSearchInteractor(mockDataAccess, mockPresenter);
    }

    @Test
    void testExecuteWithNoCriteria() {
        MovieSearchInputData inputData = new MovieSearchInputData("", "None", "None", Collections.emptyList());
        interactor.execute(inputData);
        verify(mockPresenter).prepareFailView("No search criteria provided.");
    }

    @Test
    void testExecuteWithTitleExists() {
        MovieSearchInputData inputData = new MovieSearchInputData("Inception", "None", "None", Collections.emptyList());
        when(mockDataAccess.existsByTitle("Inception")).thenReturn(true);
        Movie movie = mock(Movie.class);
        when(mockDataAccess.searchMoviesByTitle("Inception")).thenReturn(Collections.singletonList(movie));

        interactor.execute(inputData);

        ArgumentCaptor<MovieSearchOutputData> captor = ArgumentCaptor.forClass(MovieSearchOutputData.class);
        verify(mockPresenter).prepareSuccessView(captor.capture());
        assertEquals(1, captor.getValue().getMovies().size());
    }

    @Test
    void testExecuteWithTitleNotExists() {
        MovieSearchInputData inputData = new MovieSearchInputData("NonExistentTitle", "None", "None", Collections.emptyList());
        when(mockDataAccess.existsByTitle("NonExistentTitle")).thenReturn(false);

        interactor.execute(inputData);

        verify(mockPresenter).prepareFailView("No movies with that title.");
    }

    @Test
    void testExecuteWithOtherCriteria() {
        MovieSearchInputData inputData = new MovieSearchInputData("", "Action", "5", Arrays.asList("keyword1", "keyword2"));
        when(mockDataAccess.searchMovies("", "Action", 5, Arrays.asList(1, 2))).thenReturn(Collections.emptyList());

        interactor.execute(inputData);

        verify(mockPresenter).prepareFailView("No movies found with the given criteria.");
    }

    @Test
    void testExecuteWithNullDataAccess() {
        MovieSearchInteractor interactorWithNullDataAccess = new MovieSearchInteractor(null, mockPresenter);
        MovieSearchInputData inputData = new MovieSearchInputData("Inception", "None", "None", Collections.emptyList());

        interactorWithNullDataAccess.execute(inputData);

        verify(mockPresenter).prepareFailView("No movies with that title.");
    }

    @Test
    void testParseTitle() {
        assertNull(interactor.parseTitle(""));
        assertEquals("Inception", interactor.parseTitle("Inception"));
    }

    @Test
    void testParseGenre() {
        assertNull(interactor.parseGenre("None"));
        assertEquals("Action", interactor.parseGenre("Action"));
    }

    @Test
    void testParseRating() {
        assertNull(interactor.parseRating("None"));
        assertNull(interactor.parseRating(""));
        assertEquals(5, interactor.parseRating("5"));
        assertNull(interactor.parseRating("invalid"));
    }

    @Test
    void testParseKeywords() {
        List<String> keywords = Arrays.asList("keyword1", "keyword2");
        Map<String, Integer> keywordMap = new HashMap<>();
        keywordMap.put("keyword1", 1);
        keywordMap.put("keyword2", 2);

        MovieSearchInteractor spyInteractor = spy(interactor);
        doReturn(keywordMap).when(spyInteractor).loadKeywordMap();

        List<Integer> keywordIds = spyInteractor.parseKeywords(keywords);
        assertEquals(Arrays.asList(1, 2), keywordIds);
    }

    @Test
    void testLoadKeywordMap() throws IOException {
        Map<String, Integer> keywordMap = interactor.loadKeywordMap();
        assertNotNull(keywordMap);
    }

    @Test
    void testLoadKeywordMapWithFail() {
        Map<String, Integer> keywordMap = interactor.loadKeywordMap(true);
        assertTrue(keywordMap.isEmpty());
    }

    @Test
    void testCreateBufferedReader() throws IOException {
        MovieSearchInteractor interactor = new MovieSearchInteractor(mockDataAccess, mockPresenter);

        String testContent = "Test content";
        Path tempFile = Files.createTempFile("test", ".txt");
        Files.writeString(tempFile, testContent);

        try {
            BufferedReader reader = interactor.createBufferedReader(tempFile.toString());
            assertNotNull(reader);
            assertEquals(testContent, reader.readLine());
            reader.close();
        } finally {
            Files.delete(tempFile);
        }

        // Test with non-existent file
        assertThrows(FileNotFoundException.class, () ->
                interactor.createBufferedReader("nonexistent.txt")
        );
    }

    @Test
    void testLoadKeywordMapWithIOException() throws IOException {
        MovieSearchInteractor spyInteractor = spy(interactor);
        doThrow(new IOException()).when(spyInteractor).createBufferedReader(anyString());

        Map<String, Integer> result = spyInteractor.loadKeywordMap(true);
        assertTrue(result.isEmpty());
    }

    @Test
    void testExecuteWithEmptyKeywordList() {
        MovieSearchInputData inputData = new MovieSearchInputData("", "Action", "5", Collections.emptyList());
        when(mockDataAccess.searchMovies("", "Action", 5, Collections.emptyList())).thenReturn(Collections.emptyList());

        interactor.execute(inputData);

        verify(mockPresenter).prepareFailView("No movies found with the given criteria.");
    }

    @Test
    void testExecuteWithNullRating() {
        MovieSearchInputData inputData = new MovieSearchInputData("", "Action", null, Collections.emptyList());
        when(mockDataAccess.searchMovies("", "Action", null, Collections.emptyList())).thenReturn(Collections.emptyList());

        interactor.execute(inputData);

        verify(mockPresenter).prepareFailView("No movies found with the given criteria.");
    }

    @Test
    void testExecuteWithNullGenre() {
        MovieSearchInputData inputData = new MovieSearchInputData("", null, "5", Collections.emptyList());
        when(mockDataAccess.searchMovies("", null, 5, Collections.emptyList())).thenReturn(Collections.emptyList());

        interactor.execute(inputData);

        verify(mockPresenter).prepareFailView("No movies found with the given criteria.");
    }

    @Test
    void testExecuteWithInvalidRating() {
        MovieSearchInputData inputData = new MovieSearchInputData("", "Action", "invalid", Collections.emptyList());
        interactor.execute(inputData);
        verify(mockPresenter).prepareFailView("Rating must be an integer.");
    }

    @Test
    void testExecuteWithValidCriteriaButNoMoviesFound() {
        MovieSearchInputData inputData = new MovieSearchInputData("ValidTitle", "Action", "5", Arrays.asList("keyword1", "keyword2"));
        when(mockDataAccess.existsByTitle("ValidTitle")).thenReturn(true);
        when(mockDataAccess.searchMoviesByTitle("ValidTitle")).thenReturn(Collections.emptyList());

        interactor.execute(inputData);

        verify(mockPresenter).prepareFailView("No movies found with the given criteria.");
    }

    @Test
    void testExecuteWithValidCriteriaAndMoviesFound() {
        MovieSearchInputData inputData = new MovieSearchInputData("ValidTitle", "Action", "5", Arrays.asList("keyword1", "keyword2"));
        when(mockDataAccess.existsByTitle("ValidTitle")).thenReturn(true);
        Movie movie = mock(Movie.class);
        when(mockDataAccess.searchMoviesByTitle("ValidTitle")).thenReturn(Collections.singletonList(movie));

        interactor.execute(inputData);

        ArgumentCaptor<MovieSearchOutputData> captor = ArgumentCaptor.forClass(MovieSearchOutputData.class);
        verify(mockPresenter).prepareSuccessView(captor.capture());
        assertEquals(1, captor.getValue().getMovies().size());
    }
}