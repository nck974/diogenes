package dev.nichoko.diogenes.mock;

import dev.nichoko.diogenes.model.UserAuthentication;

public class UserAuthenticationMock {
    /*
     * Return a mock of authentication
     */
    public static UserAuthentication getMockUserAuthentication() {
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setUsername("test");
        userAuthentication.setPassword("test");
        return userAuthentication;

    }
}
