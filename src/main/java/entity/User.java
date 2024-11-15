package entity;

import java.util.List;
/**
 * The representation of a user in our program.
 */
public interface User {

    /**
     * Returns the username of the user.
     * @return the username of the user.
     */
    String getName();

    /**
     * Returns the password of the user.
     * @return the password of the user.
     */
    String getPassword();

    /**
     * Returns the watchlist of the user.
     * @return the watchlist of the user.
     */
    List<Integer> getWatchlist();

    /**
     * Adds a movie to the user's watchlist.
     * @param movieID the ID of the movie to add to the watchlist.
     */
    void addToWatchlist(int movieID);

}
