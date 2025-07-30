package askmyapi.amaserver.member.application.port.in;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static askmyapi.amaserver.member.MemberTestFixture.generateName;
import static askmyapi.amaserver.member.MemberTestFixture.generateProfileImageUrl;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TC
 * 1. name이 null이면 IllegalArgumentException이 발생한다
 * 2. name이 빈 문자열이면 IllegalArgumentException이 발생한다
 * 3. profileImageUrl이 null이 아니면서 url 형식이 아니면 IllegalArgumentException이 발생한다
 * 4. 유효한 name과 profileImageUrl을 전달받아 CreateMemberCommand가 생성된다
 */
class CreateMemberCommandTest {

    @Test
    void create_name이_null이면_IllegalArgumentException이_발생한다() {
        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            new CreateMemberCommand(null, generateProfileImageUrl());
        }, "Invalid Name");
    }

    @Test
    void create_name이_빈_문자열이면_IllegalArgumentException이_발생한다() {
        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            new CreateMemberCommand("", generateProfileImageUrl());
        }, "Invalid Name");
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
        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            new CreateMemberCommand(generateName(), invalidUrl);
        }, "Invalid Profile Image URL");
    }

    @Test
    void create_유효한_name과_profileImageUrl을_전달받아_CreateMemberCommand가_생성된다() {
        // given
        String name = generateName();
        String profileImageUrl = generateProfileImageUrl();
        // when
        CreateMemberCommand command = new CreateMemberCommand(name, profileImageUrl);
        // then
        assertNotNull(command);
        assertEquals(name, command.name());
        assertEquals(profileImageUrl, command.profileImageUrl());
    }
}