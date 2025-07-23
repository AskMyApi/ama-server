package askmyapi.amaserver.auth.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@EqualsAndHashCode
@ToString
public class OauthAuthenticate {

    public enum OauthProvider { GOOGLE("google"), GITHUB("github");
        private final String value;
        OauthProvider(String value) {
            this.value = value;
        }

        public static OauthProvider fromValueIgnoreCase(String value) {
            return Arrays.stream(values())
                    .filter(provider -> provider.name().equalsIgnoreCase(value))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid OauthProvider"));
        }
    }

    private final OauthProvider provider;
    private final String email;

    private OauthAuthenticate(OauthProvider provider, String email) {
        this.provider = provider;
        this.email = email;
    }

    public static OauthAuthenticate create(String provider, String email) {
        return new OauthAuthenticate(OauthProvider.fromValueIgnoreCase(provider), email);
    }

    public static OauthAuthenticate from(OauthProvider provider, String email) {
        return new OauthAuthenticate(provider, email);
    }
}
