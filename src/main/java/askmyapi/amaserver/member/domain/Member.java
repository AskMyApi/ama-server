package askmyapi.amaserver.member.domain;

import askmyapi.amaserver.global.AggregateRoot;
import askmyapi.amaserver.member.domain.event.CreateMemberEvent;
import askmyapi.amaserver.member.domain.vo.MemberId;
import lombok.Getter;

import java.util.UUID;

import static askmyapi.amaserver.util.UrlValidator.isValidUrl;

@Getter
public class Member extends AggregateRoot {
    private MemberId memberId;
    private String name;
    private String profileImageUrl;

    private Member(String name, String profileImageUrl) {
        this.memberId = MemberId.create();
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    private Member(MemberId memberId, String name, String profileImageUrl) {
        this.memberId = memberId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public static Member create(String name, String profileImageUrl) {
        if(profileImageUrl != null && !isValidUrl(profileImageUrl)) {
            throw new IllegalArgumentException("Invalid Profile Image URL");
        }
        var created = new Member(name, profileImageUrl);
        created.registerEvent(new CreateMemberEvent(
                created.getMemberId().getValue(),
                created.getName(),
                created.getProfileImageUrl()
        ));

        return created;
    }

    public static Member createWithId(MemberId memberId, String name, String profileImageUrl) {
        return new Member(memberId, name, profileImageUrl);
    }
}
