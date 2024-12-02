package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.GeminiDataAccessObject;
import data_access.MongoDBUserDataAccessObject;
import data_access.TMDBDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.add_recommended_to_watchlist.AddRecommendedToWatchlistController;
import interface_adapter.add_recommended_to_watchlist.AddRecommendedToWatchlistPresenter;
import interface_adapter.select.SelectViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_to_watchlist.AddToWatchlistController;
import interface_adapter.add_to_watchlist.AddToWatchlistPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.delete_from_watchlist.DeleteFromWatchlistController;
import interface_adapter.delete_from_watchlist.DeleteFromWatchlistPresenter;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.movie_info.MovieInfoController;
import interface_adapter.movie_info.MovieInfoPresenter;
import interface_adapter.movie_info.MovieInfoViewModel;
import interface_adapter.movie_justif.MovieJustifController;
import interface_adapter.movie_justif.MovieJustifPresenter;
import interface_adapter.movie_justif.MovieJustifViewModel;
import interface_adapter.movie_search.MovieSearchController;
import interface_adapter.movie_search.MovieSearchPresenter;
import interface_adapter.movie_search.MovieSearchViewModel;
import interface_adapter.open_watchlist.OpenWatchlistController;
import interface_adapter.open_watchlist.OpenWatchlistPresenter;
import interface_adapter.recommendation.RecommendationController;
import interface_adapter.recommendation.RecommendationPresenter;
import interface_adapter.recommendation.RecommendationViewModel;
import interface_adapter.select.SelectViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.add_recommended_to_watchlist.AddRecommendedToWatchlistInputBoundary;
import use_case.add_recommended_to_watchlist.AddRecommendedToWatchlistInteractor;
import use_case.add_recommended_to_watchlist.AddRecommendedToWatchlistOutputBoundary;
import interface_adapter.watchlist.WatchlistViewModel;
import use_case.add_to_watchlist.AddToWatchlistInputBoundary;
import use_case.add_to_watchlist.AddToWatchlistInteractor;
import use_case.add_to_watchlist.AddToWatchlistOutputBoundary;
import interface_adapter.watchlist.WatchlistViewModel;
import use_case.delete_from_watchlist.DeleteFromWatchlistInputBoundary;
import use_case.delete_from_watchlist.DeleteFromWatchlistInteractor;
import use_case.delete_from_watchlist.DeleteFromWatchlistOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.movie_info.MovieInfoDataAccessInterface;
import use_case.movie_info.MovieInfoInputBoundary;
import use_case.movie_info.MovieInfoInteractor;
import use_case.movie_info.MovieInfoOutputBoundary;
import use_case.movie_justif.MovieJustifDataAccessInterface;
import use_case.movie_justif.MovieJustifInputBoundary;
import use_case.movie_justif.MovieJustifInteractor;
import use_case.movie_justif.MovieJustifOutputBoundary;
import use_case.movie_search.MovieSearchDataAccessInterface;
import use_case.movie_search.MovieSearchInputBoundary;
import use_case.movie_search.MovieSearchInteractor;
import use_case.movie_search.MovieSearchOutputBoundary;
import use_case.movie_info.MovieInfoInteractor;
import use_case.open_watchlist.OpenWatchlistInputBoundary;
import use_case.open_watchlist.OpenWatchlistInteractor;
import use_case.open_watchlist.OpenWatchlistOutputBoundary;
import use_case.recommendation.RecommendationDataAccessInterface;
import use_case.recommendation.RecommendationInputBoundary;
import use_case.recommendation.RecommendationInteractor;
import use_case.recommendation.RecommendationOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    // thought question: is the hard dependency below a problem?
    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // thought question: is the hard dependency below a problem?
    private final MongoDBUserDataAccessObject userDataAccessObject = new MongoDBUserDataAccessObject();

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private MovieSearchView movieSearchView;
    private MovieSearchViewModel movieSearchViewModel;
    private MovieInfoView movieInfoView;
    private MovieInfoViewModel movieInfoViewModel;
    private WatchlistView watchlistView;
    private WatchlistViewModel watchlistViewModel;
    private SelectView selectView;
    private SelectViewModel selectViewModel = new SelectViewModel();
    private RecommendationView recommendationView;
    private RecommendationViewModel recommendationViewModel;
    private MovieJustifView movieJustifView;
    private MovieJustifViewModel movieJustifViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Signup View to the application.
     * @return this builder
     */
    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    /**
     * Adds the Movie Search View to the application.
     * @return this builder
     */
    public AppBuilder addMovieSearchView() {
        movieSearchViewModel = new MovieSearchViewModel();
        movieSearchView = new MovieSearchView(movieSearchViewModel);
        cardPanel.add(movieSearchView, movieSearchView.getViewName());
        return this;
    }

    /**
     * Adds the Movie Info View to the application.
     * @return this builder
     */
    public AppBuilder addMovieInfoView() {
        movieInfoViewModel = new MovieInfoViewModel();
        movieInfoView = new MovieInfoView(movieInfoViewModel);
        cardPanel.add(movieInfoView, movieInfoView.getViewName());
        return this;
    }

    /**
     * Adds the movie justification view.
     * @return the app builder object.
     */
    public AppBuilder addMovieJustifView() {
        movieJustifViewModel = new MovieJustifViewModel();
        movieJustifView = new MovieJustifView(movieJustifViewModel);
        cardPanel.add(movieJustifView, movieJustifView.getViewName());
        return this;
    }

    /**
     * Adds the Watchlist View to the application.
     * @return  this builder
     */
    public AppBuilder addWatchlistView() {
        watchlistViewModel = new WatchlistViewModel();
        watchlistView = new WatchlistView(watchlistViewModel, viewManagerModel);
        watchlistView.setSelectViewModel(selectViewModel);
        cardPanel.add(watchlistView, watchlistView.getViewName());
        return this;
    }

    /**
     * Adds the select view to app builder.
     * @return the appbuilder.
     */
    public AppBuilder addSelectView() {
        selectView = new SelectView(selectViewModel, viewManagerModel);
        cardPanel.add(selectView, selectView.getViewName());
        return this;
    }

    /**
     * Adds the recommendation view.
     * @return the appbuilder
     */
    public AppBuilder addRecommendationView() {
        recommendationViewModel = new RecommendationViewModel();
        recommendationView = new RecommendationView(recommendationViewModel);
        cardPanel.add(recommendationView, recommendationView.getViewName());
        return this;
    }

    /**
     * Adds the Login View to the application.
     * @return this builder
     */
    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    /**
     * Adds the LoggedIn View to the application.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    /**
     * Adds the Signup Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    /**
     * Adds the Login Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                movieSearchViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Movie Search Use Case to the application.
     * @return this builder
     */
    public AppBuilder addMovieSearchUseCase() {
        final MovieSearchOutputBoundary movieSearchOutputBoundary = new MovieSearchPresenter(viewManagerModel,
                movieSearchViewModel);
        final MovieSearchDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();
        final MovieSearchInputBoundary movieSearchInputBoundary = new MovieSearchInteractor(tmdbDataAccessObject,
                movieSearchOutputBoundary);
        final MovieSearchController movieSearchController = new MovieSearchController(movieSearchInputBoundary);
        movieSearchView.setMovieSearchController(movieSearchController);
        return this;
    }

    /**
     * Adds the Movie Info Use Case to the application.
     * @return this builder
     */
    public AppBuilder addMovieInfoUseCase() {
        final MovieInfoOutputBoundary movieInfoOutputBoundary = new MovieInfoPresenter(viewManagerModel,
                movieInfoViewModel);
        final MovieInfoDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();
        final MovieInfoInputBoundary movieInfoInputBoundary = new MovieInfoInteractor(tmdbDataAccessObject,
                movieInfoOutputBoundary);
        MovieInfoController movieInfoController = new MovieInfoController(movieInfoInputBoundary);
        movieInfoView.setMovieInfoController(movieInfoController);
        watchlistView.setMovieInfoController(movieInfoController);
        return this;
    }

    /**
     * Adds the Movie Justif Use Case to the application.
     * @return this builder
     */
    public AppBuilder addMovieJustifUseCase() {
        final MovieJustifOutputBoundary movieJustifOutputBoundary = new MovieJustifPresenter(viewManagerModel,
                movieJustifViewModel);
        final MovieJustifDataAccessInterface geminiDataAccessObject = new GeminiDataAccessObject();
        final MovieInfoDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();
        final MovieJustifInputBoundary movieJustifInputBoundary = new MovieJustifInteractor(tmdbDataAccessObject,
                geminiDataAccessObject, movieJustifOutputBoundary);
        MovieJustifController movieJustifController = new MovieJustifController(movieJustifInputBoundary);
        movieJustifView.setMovieJustifController(movieJustifController);
        recommendationView.setMovieJustifController(movieJustifController);
        return this;
    }

    /**
     * Adds the Add To Watchlist Use Case to the application.
     * @return this builder
     */
    public AppBuilder addAddToWatchlistUseCase() {
        final AddToWatchlistOutputBoundary addToWatchlistOutputBoundary = new
                AddToWatchlistPresenter(viewManagerModel, movieSearchViewModel);
        final AddToWatchlistInputBoundary addToWatchlistInputBoundary = new
                AddToWatchlistInteractor(userDataAccessObject, addToWatchlistOutputBoundary);
        final AddToWatchlistController addToWatchlistController = new
                AddToWatchlistController(addToWatchlistInputBoundary);
        movieSearchView.setAddToWatchlistController(addToWatchlistController);
        return this;
    }

    /**
     * Adds the Add Recommended To Watchlist Use Case to the application.
     * @return this builder
     */
    public AppBuilder addAddRecommendedToWatchlistUseCase() {
        final AddRecommendedToWatchlistOutputBoundary addRecommendedToWatchlistOutputBoundary =
                new AddRecommendedToWatchlistPresenter(viewManagerModel, recommendationViewModel, watchlistViewModel,
                        selectViewModel);
        final AddRecommendedToWatchlistInputBoundary addRecommendedToWatchlistInputBoundary =
                new AddRecommendedToWatchlistInteractor(userDataAccessObject, addRecommendedToWatchlistOutputBoundary);
        final AddRecommendedToWatchlistController addRecommendedToWatchlistController =
                new AddRecommendedToWatchlistController(addRecommendedToWatchlistInputBoundary);
        recommendationView.setAddRecommendedToWatchlistController(addRecommendedToWatchlistController);
        return this;
    }

    /**
     * Adds the Open Watchlist Use Case to the application.
     * @return this builder
     */
    public AppBuilder addOpenWatchlistUseCase() {
        final OpenWatchlistOutputBoundary openWatchlistOutputBoundary = new OpenWatchlistPresenter(viewManagerModel,
                watchlistViewModel, movieSearchViewModel, movieInfoViewModel);
        final TMDBDataAccessObject tmdbDataAccessObject = new TMDBDataAccessObject();
        final OpenWatchlistInputBoundary openWatchlistInputBoundary = new OpenWatchlistInteractor(userDataAccessObject,
                tmdbDataAccessObject, openWatchlistOutputBoundary);
        final OpenWatchlistController openWatchlistController = new OpenWatchlistController(openWatchlistInputBoundary);
        movieSearchView.setOpenWatchlistController(openWatchlistController);
        watchlistView.setOpenWatchlistController(openWatchlistController);
        return this;
    }

    /**
     * Adds the movie reccomendation use case.
     * @return the movie reccomendation use case
     */
    public AppBuilder addRecommendationUseCase() {
        final RecommendationOutputBoundary recommendationOutputBoundary = new
                RecommendationPresenter(viewManagerModel, recommendationViewModel, selectViewModel);
        final RecommendationDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();
        final RecommendationInputBoundary recommendationInputBoundary = new
                RecommendationInteractor(tmdbDataAccessObject, recommendationOutputBoundary);
        final RecommendationController recommendationController = new
                RecommendationController(recommendationInputBoundary);
        selectView.setRecommendationController(recommendationController);
        recommendationView.setRecommendationController(recommendationController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new
                LogoutPresenter(viewManagerModel, loggedInViewModel, loginViewModel);
        final LogoutInputBoundary logoutInteractor = new
                LogoutInteractor(userDataAccessObject, logoutOutputBoundary);
        final LogoutController logoutController = new LogoutController(logoutInteractor);
        movieSearchView.setLogoutController(logoutController);
        return this;
    }

    /**
     * Adds the Delete From Watchlist Use Case to the application.
     * @return the app builder
     */
    public AppBuilder addDeleteFromWatchlistUseCase() {
        final DeleteFromWatchlistOutputBoundary deleteFromWatchlistOutputBoundary = new
                DeleteFromWatchlistPresenter(viewManagerModel, watchlistViewModel, selectViewModel);
        final TMDBDataAccessObject tmdbDataAccessObject = new TMDBDataAccessObject();
        final DeleteFromWatchlistInputBoundary deleteFromWatchlistInputBoundary = new
                DeleteFromWatchlistInteractor(userDataAccessObject, tmdbDataAccessObject,
                deleteFromWatchlistOutputBoundary);
        final DeleteFromWatchlistController deleteFromWatchlistController = new
                DeleteFromWatchlistController(deleteFromWatchlistInputBoundary);
        selectView.setDeleteFromWatchlistController(deleteFromWatchlistController);

        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Movie Search / Recommendations");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }

    /**
     * The main block.
     * @param args java tings.
     */
    public static void main(String[] args) {
        AppBuilder test = new AppBuilder();
        System.out.println(test.movieInfoView.getClass().getName());
    }
}
