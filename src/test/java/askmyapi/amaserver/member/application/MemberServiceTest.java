package askmyapi.amaserver.member.application;

import askmyapi.amaserver.member.application.port.in.CreateMemberCommand;
import askmyapi.amaserver.member.application.port.out.MemberCommandPort;
import askmyapi.amaserver.member.application.port.out.MemberEventPublisher;
import askmyapi.amaserver.member.domain.event.CreateMemberEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static askmyapi.amaserver.member.MemberTestFixture.generateName;
import static askmyapi.amaserver.member.MemberTestFixture.generateProfileImageUrl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * TC
 * createMember
 * 1. 유효한 명령어를 전달받아 멤버를 생성하고 응답을 반환한다.
 * 2. MemberCommandPort.save를 1회 호출한다.
 * 3. MemberEventPublisher.publish를 1회 호출한다.
 */
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberCommandPort memberCommandPort;
    @Mock
    MemberEventPublisher memberEventPublisher;

    @Test
    void createMember_유효한_명령어를_전달받아_멤버를_생성하고_응답을_반환한다() {
        // given
        String name = generateName();
        String profileImageUrl = generateProfileImageUrl();

        // when
        var result = memberService.createMember(new CreateMemberCommand(name, profileImageUrl));

        // then
        assertNotNull(result);
        assertNotNull(result.memberId());
        assertEquals(name, result.name());
        assertEquals(profileImageUrl, result.profileImageUrl());
    }

    @Test
    void createMember_MemberCommandPort_save를_1회_호출한다() {
        // given
        String name = generateName();
        String profileImageUrl = generateProfileImageUrl();

        // when
        var result = memberService.createMember(new CreateMemberCommand(name, profileImageUrl));

        // then
        verify(memberCommandPort).save(argThat(member ->
                member.getName().equals(name) &&
                        member.getProfileImageUrl().equals(profileImageUrl) &&
                        member.getMemberId().getValue().equals(result.memberId())
        ));
    }

    @Test
    void createMember_MemberEventPublisher_publish를_1회_호출한다() {
        // given
        String name = generateName();
        String profileImageUrl = generateProfileImageUrl();

        // when
        var result = memberService.createMember(new CreateMemberCommand(name, profileImageUrl));

        // then
        verify(memberEventPublisher, times(1)).publish(argThat(event ->
                event instanceof CreateMemberEvent &&
                        ((CreateMemberEvent) event).memberId().equals(result.memberId()) &&
                        ((CreateMemberEvent) event).name().equals(name) &&
                        ((CreateMemberEvent) event).profileImageUrl().equals(profileImageUrl)
        ));
    }
}