package askmyapi.amaserver.auth.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class AuthId {
    private final UUID value;

    private AuthId(UUID value) {
        this.value = value;
    }

    public static AuthId create() {
        return new AuthId(UUID.randomUUID());
    }

    public static AuthId from(UUID value) {
        return new AuthId(value);
    }
}
