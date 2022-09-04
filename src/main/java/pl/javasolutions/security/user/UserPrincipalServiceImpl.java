package pl.javasolutions.security.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import pl.javasolutions.security.jwt.TokenService;
import pl.javasolutions.security.oauth2.userInfo.ProviderUserInfo;
import pl.javasolutions.security.oauth2.userInfo.ProviderUserInfoValidator;

import java.util.Optional;

import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor(access = PROTECTED)
class UserPrincipalServiceImpl implements UserPrincipalService {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    public UserPrincipal loadUser(final ProviderUserInfo<?> providerUserInfo) {
        ProviderUserInfoValidator.validateUserInfo(providerUserInfo);
        Optional<UserDetails> userDetails = userRepository.findByEmail(providerUserInfo.getEmail());

        OidcIdToken oidcIdToken = userDetails.map(details -> tokenService.createToken(providerUserInfo, details))
                .orElseGet(() -> tokenService.createToken(providerUserInfo));

        return userDetails.map(details -> UserPrincipal.create(oidcIdToken, details))
                .orElseGet(() -> UserPrincipal.create(oidcIdToken, null));
    }
}
