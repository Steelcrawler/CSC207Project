package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.User;
import use_case.add_to_watchlist.AddToWatchlistDataAccessInterface;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.open_watchlist.OpenWatchlistDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program.
 */
public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface, AddToWatchlistDataAccessInterface, OpenWatchlistDataAccessInterface {

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
    public boolean existsInWatchlist(String currentUsername, Integer movieID) {
        if (watchlists.containsKey(currentUsername)) {
            return watchlists.get(currentUsername).contains(movieID);
        }
        return false;
    }

    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }

    @Override
    public List<Integer> getWatchlist(String username) {
        return watchlists.get(username);
    }

    @Override
    public void addToWatchlist(String currentUsername, Integer movieID) {
        if (watchlists.containsKey(currentUsername)) {
            if (!watchlists.get(currentUsername).contains(movieID)) {
                watchlists.get(currentUsername).add(movieID);
            }
        }
        else {
            List<Integer> newWatchlist = new ArrayList<>();
            newWatchlist.add(movieID);
            watchlists.put(currentUsername, newWatchlist);
        }    }
}
