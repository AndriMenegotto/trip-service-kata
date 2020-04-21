package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TripServiceTest {

    public static final User NOT_LOGGED_USER = null;
    public static final User UNUSED_USER = null;
    public static final User ANOTHER_USER = new User();
    public static final User LOGGED_USER = new User();

    public static final Trip TO_CHILE = new Trip();
    private static final Trip TO_COLOMBIA = new Trip();

    private User loggedUser;
    private TripService tripService;

    @BeforeEach
    void setUp() {
        tripService = new TestableTripService();
    }

    @Test
    void throws_exception_when_user_is_not_logged() {
        loggedUser = NOT_LOGGED_USER;

        assertThrows(UserNotLoggedInException.class, () -> {
            tripService.getTripsByUser(UNUSED_USER);
        });
    }

    @Test
    void should_return_empty_trip_list_when_users_are_not_friends() {
        loggedUser = LOGGED_USER;

        User friend = UserBuilder.user()
            .withFriends(ANOTHER_USER)
            .withTrips(TO_CHILE)
            .build();

        List<Trip> trips = tripService.getTripsByUser(friend);
        assertEquals(trips.size(),0);
    }

    @Test
    void should_return_trip_list_when_users_are_friends() {
        loggedUser = LOGGED_USER;

        User friend = UserBuilder.user()
            .withFriends(ANOTHER_USER, loggedUser)
            .withTrips(TO_CHILE, TO_COLOMBIA)
            .build();

        List<Trip> trips = tripService.getTripsByUser(friend);
        assertEquals(trips.size(),2);
    }

    private class TestableTripService extends TripService{

        @Override
        protected User getLoggedUser() {
            return loggedUser;
        }

        @Override
        protected List<Trip> tripsByUser(User user) {
            return user.trips();
        }
    }
}