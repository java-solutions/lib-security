package pl.javasolutions.security.oauth2.userInfo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static pl.javasolutions.security.oauth2.userInfo.SampleProviderGoogleUserInfo.DEFAULT_GOOGLE_OIDC_USER;
import static pl.javasolutions.security.samples.SampleUser.DEFAULT_OIDC_USER;

@DisplayName("provider user info factory test")
class ProviderUserInfoFactoryTest {

    @Test
    @DisplayName("should create user info when provider is google")
    void shouldCreateProviderUserInfoWithGoogleProvider() {
        // given
        OAuth2UserRequest request = mock(OAuth2UserRequest.class);
        ClientRegistration registration = CommonOAuth2Provider.GOOGLE
                .getBuilder("google")
                .clientId("some-client-id")
                .build();
        given(request.getClientRegistration()).willReturn(registration);

        // when
        ProviderUserInfo<OidcUser> userInfo = ProviderUserInfoFactory.create(request, DEFAULT_OIDC_USER);

        // then
        assertThat(userInfo)
                .isNotNull()
                .isEqualTo(DEFAULT_GOOGLE_OIDC_USER);
    }

    @Test
    @DisplayName("should throw error when unknown provider is used")
    void shouldThrowExceptionIllegalState() {
        OAuth2UserRequest request = mock(OAuth2UserRequest.class);
        ClientRegistration registration = CommonOAuth2Provider.GOOGLE
                .getBuilder("unknown")
                .clientId("some-client-id")
                .build();
        given(request.getClientRegistration()).willReturn(registration);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> ProviderUserInfoFactory.create(request, DEFAULT_OIDC_USER));

        assertNotNull(ex);
    }
}