package entity;

import java.util.List;
import java.util.ArrayList;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String name;
    private final String password;
    private final List<Integer> Watchlist;

    public CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
        this.Watchlist = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public List<Integer> getWatchlist() {
        return Watchlist;
    }

    @Override
    public void addToWatchlist(int movieID) {
        Watchlist.add(movieID);
    }
}
