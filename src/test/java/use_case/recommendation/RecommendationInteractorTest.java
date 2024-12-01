package use_case.recommendation;

import data_access.TMDBDataAccessObject;
import entity.Movie;
import interface_adapter.select.SelectViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.recommendation.RecommendationController;
import interface_adapter.recommendation.RecommendationPresenter;
import interface_adapter.recommendation.RecommendationViewModel;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import use_case.movie_search.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import view.RecommendationView;
import view.SelectView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RecommendationInteractorTest {

    private RecommendationDataAccessInterface mockDataAccess;
    private RecommendationOutputBoundary mockPresenter;
    private RecommendationInteractor interactor;

    @BeforeEach
    void setUp() {
//        final ViewManagerModel viewManagerModel = new ViewManagerModel();
//        SelectViewModel selectViewModel = new SelectViewModel();
//        RecommendationViewModel recommendationViewModel = new RecommendationViewModel();
//        RecommendationView recommendationView = new RecommendationView(recommendationViewModel);
//        final RecommendationOutputBoundary recommendationOutputBoundary = new RecommendationPresenter(viewManagerModel, recommendationViewModel, selectViewModel);
//        final RecommendationDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();
//        final RecommendationInputBoundary recommendationInputBoundary = new RecommendationInteractor(tmdbDataAccessObject, recommendationOutputBoundary);
//        final RecommendationController recommendationController = new RecommendationController(recommendationInputBoundary);
//        SelectView selectView = new SelectView(selectViewModel, viewManagerModel);
//        selectView.setRecommendationController(recommendationController);
//        recommendationView.setRecommendationController(recommendationController);
//        interactor = new RecommendationInteractor()
//        RecommendationInputData inputData = new RecommendationInputData(671);
        RecommendationDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        RecommendationViewModel recommendationViewModel = new RecommendationViewModel();
    }

    @Test
    void testExecuteWithNoMovies() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        RecommendationViewModel recommendationViewModel = new RecommendationViewModel();
        RecommendationOutputBoundary successPresenter = new RecommendationPresenter(viewManagerModel, recommendationViewModel, new SelectViewModel());
        mockPresenter = mock(RecommendationOutputBoundary.class);
        RecommendationDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();
        RecommendationInputBoundary interactor = new RecommendationInteractor(tmdbDataAccessObject, mockPresenter);
        List<Integer> ints = new ArrayList<>();
        RecommendationInputData inputData = new RecommendationInputData(ints);
        interactor.execute(inputData);

        verify(mockPresenter).prepareFailView("Movies were not provided");
        verify(mockPresenter).prepareFailView("A Recommendation could not be made");
    }

    @Test
    void testToSelectView() {
        TMDBDataAccessObject tmdbDataAccessObject = new TMDBDataAccessObject();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        RecommendationViewModel recommendationViewModel = new RecommendationViewModel();

        // This creates a successPresenter that tests whether the test case is as we expect.
        RecommendationOutputBoundary successPresenter = new RecommendationOutputBoundary() {
            @Override
            public void prepareSuccessView(RecommendationOutputData outputData) {
                Assertions.fail("IncorrectView2");
            }

            public void prepareFailView(String message) {
                Assertions.fail("Incorrect View 1");
            }

            @Override
            public void toSelectView() {
                viewManagerModel.setState(recommendationViewModel.getViewName());
                viewManagerModel.firePropertyChanged();
            }
        };

        RecommendationInputBoundary interactor = new RecommendationInteractor(tmdbDataAccessObject,
                successPresenter);
        interactor.toSelectView();
        String finalView = viewManagerModel.getState();
        Assertions.assertEquals("Recommendation", finalView);
    }

    @Test
    void testExecutefake() {
        List<Integer> ints = new ArrayList<>();
        ints.add(671);
        ints.add(238);
        RecommendationInputData inputData = new RecommendationInputData(ints);
        RecommendationDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        RecommendationViewModel recommendationViewModel = new RecommendationViewModel();
//        List<Integer> ints = new ArrayList<>();
//        ints.add(278);
//        ints.add(238);
//        RecommendationInputData inputData = new RecommendationInputData(ints);
//        interactor.execute(inputData);
//        assertEquals(2,2);

        RecommendationOutputBoundary successPresenter = new RecommendationPresenter(viewManagerModel, recommendationViewModel, new SelectViewModel()) {
            @Override
            public void prepareSuccessView(RecommendationOutputData recommendationOutputData) {
                Assertions.assertEquals(402431, recommendationOutputData.getMovieIDsList().get(0));
//                Assertions.assertEquals("Harry Potter and the Philosopher's Stone", recommendationOutputData.getMovieIDsList().get(0));
//                Assertions.assertEquals("", recommendationOutputData.getMovieIDsList().get(1));
            }

            @Override
            public void prepareFailView(String error) {
                Assertions.fail("Use case failure is unexpected.");
            }
        };

        RecommendationInputBoundary interactor = new RecommendationInteractor(tmdbDataAccessObject, successPresenter);
        interactor.execute(inputData);
    }
}