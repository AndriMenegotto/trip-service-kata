package org.craftedsw.tripservicekata.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    public static final User JUANITO = new User();

    @Test
    void should_return_false_if_the_user_is_not_a_friend() {
        User user = new User();
        assertFalse(user.isAfriendOf(JUANITO));
    }

    @Test
    void should_return_true_if_the_user_is_a_friend() {
        User user = new User();
        user.addFriend(JUANITO);

        assertTrue(user.isAfriendOf(JUANITO));
    }
}
