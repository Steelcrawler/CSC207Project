package data_access;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entity.User;
import entity.UserFactory;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;
import org.bson.conversions.Bson;
import use_case.add_to_watchlist.AddToWatchlistDataAccessInterface;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.delete_from_watchlist.DeleteFromWatchlistDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.open_watchlist.OpenWatchlistDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import use_case.watchliststorage.UserWatchlistDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoDBUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        UserWatchlistDataAccessInterface, AddToWatchlistDataAccessInterface, OpenWatchlistDataAccessInterface, DeleteFromWatchlistDataAccessInterface {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String WATCHLIST = "watchlist";
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> usersCollection;
    private final UserFactory userFactory;
    private String currentUsername;

    public MongoDBUserDataAccessObject() {
        Dotenv dotenv = Dotenv.load();
        String connectionString = dotenv.get("MONGODB_CONNECTION_STRING");
        String dbName = dotenv.get("MONGODB_DB_NAME");
        String userFactoryClass = dotenv.get("USER_FACTORY_CLASS");

        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase(dbName);
        this.usersCollection = database.getCollection("users");

        try {
            Class<?> clazz = Class.forName(userFactoryClass);
            this.userFactory = (UserFactory) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate UserFactory", e);
        }
    }

    @Override
    public boolean existsByName(String username) {
        Document user = usersCollection.find(eq(USERNAME, username)).first();
        return user != null;
    }

    @Override
    public void save(User user) {
        Document userDoc = new Document(USERNAME, user.getName())
                .append(PASSWORD, user.getPassword())
                .append(WATCHLIST, new ArrayList<Integer>());
        usersCollection.insertOne(userDoc);
    }

    @Override
    public void changePassword(User user) {
        Bson filter = eq(USERNAME, user.getName());
        Bson update = new Document("$set", new Document(PASSWORD, user.getPassword()));
        usersCollection.updateOne(filter, update);
    }

    @Override
    public User get(String username) {
        Document userDoc = usersCollection.find(eq(USERNAME, username)).first();
        if (userDoc != null) {
            String name = userDoc.getString(USERNAME);
            String password = userDoc.getString(PASSWORD);
            return userFactory.create(name, password);
        }
        return null;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    @Override
    public List<Integer> getWatchlist(String username) {
        Document user = usersCollection.find(eq(USERNAME, username)).first();
        if (user != null) {
            return user.getList(WATCHLIST, Integer.class);
        }
        return new ArrayList<>();
    }

    @Override
    public void addToWatchlist(String username, Integer movieId) {
        Bson filter = eq(USERNAME, username);
        Bson update = new Document("$push", new Document(WATCHLIST, movieId));
        usersCollection.updateOne(filter, update);
    }

    @Override
    public void removeFromWatchlist(String username, Integer movieId) {
        Bson filter = eq(USERNAME, username);
        Bson update = new Document("$pull", new Document(WATCHLIST, movieId));
        usersCollection.updateOne(filter, update);
    }

    @Override
    public boolean existsInWatchlist(String username, Integer movieID) {
        List<Integer> userWatchlist = this.getWatchlist(username);
        return userWatchlist.contains(movieID);
    }

    public MongoCollection<Document> getUsersCollection() {
        return usersCollection;
    }

    public void close() {
        mongoClient.close();
    }

}