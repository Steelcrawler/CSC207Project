package use_case.watchliststorage;

import data_access.MongoDBUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MongoDBUserDataAccessObjectTest {
    private MongoDBUserDataAccessObject dao;
    private UserFactory userFactory;
    private User user;

    @Before
    public void setUp() {
        userFactory = new CommonUserFactory();
        user = userFactory.create("john_doe", "password123");
        dao = new MongoDBUserDataAccessObject("mongodb://localhost:27017", "movieDB", userFactory);
        dao.save(user);
    }

    @After
    public void tearDown() {
        // Delete all documents in the users collection
        dao.getUsersCollection().deleteMany(new Document());
        dao.close();
    }

    @Test
    public void testAddUser() {
        assertTrue(dao.existsByName(user.getName()));
    }

    @Test
    public void testAddToWatchlist() {
        dao.addToWatchlist(user.getName(), 1); // Movie ID 1
        dao.addToWatchlist(user.getName(), 2); // Movie ID 2

        List<Integer> watchlist = dao.getWatchlist(user.getName());
        assertEquals(2, watchlist.size());
        assertTrue(watchlist.contains(1));
        assertTrue(watchlist.contains(2));
    }

    @Test
    public void testRemoveFromWatchlist() {
        dao.addToWatchlist(user.getName(), 1); // Movie ID 1
        dao.addToWatchlist(user.getName(), 2); // Movie ID 2

        dao.removeFromWatchlist(user.getName(), 1); // Remove Movie ID 1

        List<Integer> watchlist = dao.getWatchlist(user.getName());
        assertEquals(1, watchlist.size());
        assertFalse(watchlist.contains(1));
        assertTrue(watchlist.contains(2));
    }

    @Test
    public void testChangePassword() {
        user.setPassword("newpassword123");
        dao.changePassword(user);

        User updatedUser = dao.get(user.getName());
        assertEquals("newpassword123", updatedUser.getPassword());
    }
}