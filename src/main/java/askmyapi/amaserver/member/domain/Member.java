package askmyapi.amaserver.member.domain;

import askmyapi.amaserver.member.domain.vo.MemberId;
import lombok.Getter;

import java.util.UUID;

import static askmyapi.amaserver.util.UrlValidator.isValidUrl;

@Getter
public class Member {
    private MemberId memberId;
    private String name;
    private String profileImageUrl;

    private Member(String name, String profileImageUrl) {
        this.memberId = MemberId.create();
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public static Member create(String name, String profileImageUrl) {
        if(profileImageUrl != null && !isValidUrl(profileImageUrl)) {
            throw new IllegalArgumentException("Invalid Profile Image URL");
        }
        return new Member(name, profileImageUrl);
    }
}
