package data_access;

import entity.User;
import use_case.add_to_watchlist.AddToWatchlistDataAccessInterface;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.delete_from_watchlist.DeleteFromWatchlistDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.open_watchlist.OpenWatchlistDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import use_case.watchliststorage.UserWatchlistDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program.
 */
public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        UserWatchlistDataAccessInterface, AddToWatchlistDataAccessInterface, OpenWatchlistDataAccessInterface,
        DeleteFromWatchlistDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();
    private final Map<String, List<Integer>> watchlists = new HashMap<>();
    private String currentUsername;

    @Override
    public boolean existsByName(String identifier) {
        return users.containsKey(identifier);
    }

    @Override
    public void save(User user) {
        users.put(user.getName(), user);
        watchlists.put(user.getName(), new ArrayList<>());
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public void changePassword(User user) {
        // Replace the old entry with the new password
        users.put(user.getName(), user);
    }

    @Override
    public void setCurrentUsername(String name) {
        this.currentUsername = name;
    }

    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }

    @Override
    public List<Integer> getWatchlist(String username) {
        return watchlists.getOrDefault(username, new ArrayList<>());
    }

    @Override
    public void addToWatchlist(String username, Integer movieId) {
        List<Integer> watchlist = watchlists.get(username);
        if (watchlist != null) {
            watchlist.add(movieId);
        }
    }

    @Override
    public void removeFromWatchlist(String username, Integer movieId) {
        List<Integer> watchlist = watchlists.get(username);
        if (watchlist != null) {
            watchlist.remove(movieId);
        }
    }

    @Override
    public boolean existsInWatchlist(String username, Integer movieID) {
        List<Integer> userWatchlist = this.getWatchlist(username);
        return userWatchlist.contains(movieID);
    }
}