package askmyapi.amaserver.member.application;

import askmyapi.amaserver.IntegrationTest;
import askmyapi.amaserver.member.adapter.out.persistence.repository.MemberJpaRepository;
import askmyapi.amaserver.member.application.port.in.CreateMemberCommand;
import askmyapi.amaserver.member.domain.event.CreateMemberEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import static askmyapi.amaserver.member.MemberTestFixture.generateName;
import static askmyapi.amaserver.member.MemberTestFixture.generateProfileImageUrl;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

/**
 * TC
 * createMember
 * 1. member가 정확히 영속화된다.
 * 2. memberCreated 이벤트가 등록된다. -- 단위테스트로 검증
 */
@IntegrationTest
public class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void createMember_member가_정확히_영속화된다() {
        // given
        var name = generateName();
        var profileImageUrl = generateProfileImageUrl();
        var command = new CreateMemberCommand(name, profileImageUrl);

        // when
        var result = memberService.createMember(command);

        // then
        var member = memberJpaRepository.findById(result.memberId()).orElseThrow();
        assertNotNull(member);
        assertEquals(result.memberId(), member.getId());
        assertEquals(name, member.getName());
        assertEquals(profileImageUrl, member.getProfileImageUrl());
    }
}
