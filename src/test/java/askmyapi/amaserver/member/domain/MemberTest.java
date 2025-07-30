package askmyapi.amaserver.member.domain;

import askmyapi.amaserver.member.domain.event.CreateMemberEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static askmyapi.amaserver.member.MemberTestFixture.generateName;
import static askmyapi.amaserver.member.MemberTestFixture.generateProfileImageUrl;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TC
 * create
 * 1. name, profileImageUrl를 전달받아 생성되며 MemberId, name, profileImageUrl를 갖는다
 * 2. profileImageUrl이 null이 아니면서 url 형식이 아니면 IllegalArgumentException이 발생한다
 * 3. 생성되는 member는 서로 다른 memberId를 가진다
 * 4. CreateMemberEvent를 등록한다
 */
class MemberTest {

    @Test
    void create_name_profileImageUrl를_전달받아_생성되며_MemberId_name_profileImageUrl를_가진다() {
        // given
        String name = generateName();
        String profileImageUrl = generateProfileImageUrl();

        // when
        Member member = Member.create(name, profileImageUrl);

        // then
        assertNotNull(member);
        assertNotNull(member.getMemberId());
        assertEquals(name, member.getName());
        assertEquals(profileImageUrl, member.getProfileImageUrl());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "http://",                          // 도메인 없음
            "http://-example.com",              // 도메인 시작이 하이픈
            "http://example-.com",              // 도메인 끝이 하이픈
            "http://.example.com",              // 도메인 시작이 점
            "http://example..com",              // 도메인에 중복 점
            "http://example.com:abc",           // 포트에 문자 포함
            "http://example.com:99999",         // 포트 숫자 범위 초과
            "http://example.com/space here",    // 경로에 공백 포함
            "http:/example.com",                // 슬래시 하나 부족 (http:/)
            "htp://example.com"                 // 프로토콜 오타
    })
    void create_profileImageUrl이_null이_아니면서_url_형식이_아니면_IllegalArgumentException이_발생한다(
            String invalidUrl
    ) {
        // given
        String name = generateName();

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            Member.create(name, invalidUrl);
        }, "Invalid Profile Image URL");
    }

    @Test
    void create_생성되는_member는_서로_다른_memberId를_가진다() {
        // given
        String name = generateName();
        String profileImageUrl = generateProfileImageUrl();
        // when
        Member member1 = Member.create(name, profileImageUrl);
        Member member2 = Member.create(name, profileImageUrl);
        // then
        assertNotNull(member1);
        assertNotNull(member2);
        assertNotEquals(member1.getMemberId(), member2.getMemberId(), "Member IDs should be different");
    }

    @Test
    void create_CreateMemberEvent를_등록한다() {
        // given
        String name = generateName();
        String profileImageUrl = generateProfileImageUrl();

        // when
        Member member = Member.create(name, profileImageUrl);

        // then
        assertNotNull(member);
        var events = member.consumeDomainEvents();
        assertEquals(1, events.size());
        assertInstanceOf(CreateMemberEvent.class, events.get(0));
    }
}