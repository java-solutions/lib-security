package pl.javasolutions.security.user;

import static pl.javasolutions.security.samples.SampleUser.DEFAULT_OIDC_ID_TOKEN;
import static pl.javasolutions.security.samples.SampleUser.DEFAULT_USER_DETAILS;

public class SampleUserPrincipal {
    public static final UserPrincipal DEFAULT_USER_PRINCIPAL = createDefaultUser();

    private static UserPrincipal createDefaultUser() {
        return UserPrincipal.create(DEFAULT_OIDC_ID_TOKEN, DEFAULT_USER_DETAILS);
    }
}