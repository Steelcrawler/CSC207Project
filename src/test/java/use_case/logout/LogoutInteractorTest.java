package use_case.logout;

import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.Test;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInputData;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.logout.LogoutOutputData;
import data_access.InMemoryUserDataAccessObject;

import static org.junit.jupiter.api.Assertions.*;

class LogoutInteractorTest {

    @Test
    void successTest() {
        LogoutInputData inputData = new LogoutInputData("Paul");
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        // For the success test, we need to add Paul to the data access repository before we log in.
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("Paul", "password");
        userRepository.save(user);
        userRepository.setCurrentUsername("Paul");

        // This creates a successPresenter that tests whether the test case is as we expect.
        LogoutOutputBoundary successPresenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData user) {
                // check that the output data contains the username of who logged out
                assertEquals("Paul", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToSignUpView() {
                // Implement this method if needed for the test
            }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
        // check that the user was logged out
        assertNull(userRepository.getCurrentUsername());
    }
}