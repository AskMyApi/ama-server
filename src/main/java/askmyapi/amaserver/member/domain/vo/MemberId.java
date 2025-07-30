package askmyapi.amaserver.member.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@EqualsAndHashCode
@ToString
public class MemberId {
    private final UUID value;

    private MemberId(UUID value) {
        this.value = value;
    }

    public static MemberId create() {
        return new MemberId(UUID.randomUUID());
    }

    public static MemberId from(UUID value) {
        return new MemberId(value);
    }
}
