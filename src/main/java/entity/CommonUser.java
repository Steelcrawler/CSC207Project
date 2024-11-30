package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private String name;
    private String password;
    private List<Integer> watchlist;

    public CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
        this.watchlist = new ArrayList<>();
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
        return this.watchlist;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void addToWatchlist(int movieID) {
        watchlist.add(movieID);
    }

}
