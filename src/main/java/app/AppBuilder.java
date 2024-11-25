package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.InMemoryUserDataAccessObject;
import data_access.MongoDBUserDataAccessObject;
import data_access.TMDBDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_to_watchlist.AddToWatchlistController;
import interface_adapter.add_to_watchlist.AddToWatchlistPresenter;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.movieinfo.MovieInfoViewModel;
import interface_adapter.movieinfo.MovieInfoController;
import interface_adapter.movieinfo.MovieInfoPresenter;
import interface_adapter.movieinfo.MovieInfoViewModel;
import interface_adapter.moviesearch.MovieSearchViewModel;
import interface_adapter.moviesearch.MovieSearchController;
import interface_adapter.moviesearch.MovieSearchPresenter;
import interface_adapter.moviesearch.MovieSearchViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.add_to_watchlist.AddToWatchlistDataAccessInterface;
import use_case.add_to_watchlist.AddToWatchlistInputBoundary;
import use_case.add_to_watchlist.AddToWatchlistInteractor;
import use_case.add_to_watchlist.AddToWatchlistOutputBoundary;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.movieinfo.MovieInfoDataAccessInterface;
import use_case.movieinfo.MovieInfoInputBoundary;
import use_case.movieinfo.MovieInfoOutputBoundary;
import use_case.movieinfo.MovieInfoInteractor;
import use_case.movie_search.MovieSearchDataAccessInterface;
import use_case.movie_search.MovieSearchInputBoundary;
import use_case.movie_search.MovieSearchInteractor;
import use_case.movie_search.MovieSearchOutputBoundary;
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
        final MovieSearchInputBoundary movieSearchInputBoundary = new MovieSearchInteractor(tmdbDataAccessObject, movieSearchOutputBoundary);
        final MovieSearchController movieSearchController = new MovieSearchController(movieSearchInputBoundary);
        movieSearchView.setMovieSearchController(movieSearchController);
        return this;
    }

    /**
 movie_info_view
     * Adds the Movie Info Use Case to the application.
     * @return this builder
     */
    public AppBuilder addMovieInfoUseCase() {
        final MovieInfoOutputBoundary movieInfoOutputBoundary = new MovieInfoPresenter(viewManagerModel,
                movieInfoViewModel);
        final MovieInfoDataAccessInterface tmdbDataAccessObject = new TMDBDataAccessObject();
        final MovieInfoInputBoundary movieInfoInputBoundary = new MovieInfoInteractor(tmdbDataAccessObject, movieInfoOutputBoundary);
        final MovieInfoController movieInfoController = new MovieInfoController(movieInfoInputBoundary);
        movieInfoView.setMovieInfoController(movieInfoController);
     
     * Adds the Add To Watchlist Use Case to the application.
     * @return this builder
     */
    public AppBuilder addAddToWatchlistUseCase() {
        final AddToWatchlistOutputBoundary addToWatchlistOutputBoundary = new AddToWatchlistPresenter(viewManagerModel,
                movieSearchViewModel);
        final AddToWatchlistInputBoundary addToWatchlistInputBoundary = new AddToWatchlistInteractor(userDataAccessObject, addToWatchlistOutputBoundary);
        final AddToWatchlistController addToWatchlistController = new AddToWatchlistController(addToWatchlistInputBoundary);
        movieSearchView.setAddToWatchlistController(addToWatchlistController);
 main
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
