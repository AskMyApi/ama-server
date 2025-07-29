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
    private final String socialId;

    private OauthAuthenticate(OauthProvider provider, String socialId) {
        this.provider = provider;
        this.socialId = socialId;
    }

    public static OauthAuthenticate create(String provider, String socialId) {
        return new OauthAuthenticate(OauthProvider.fromValueIgnoreCase(provider), socialId);
    }

    public static OauthAuthenticate from(OauthProvider provider, String socialId) {
        return new OauthAuthenticate(provider, socialId);
    }
}
