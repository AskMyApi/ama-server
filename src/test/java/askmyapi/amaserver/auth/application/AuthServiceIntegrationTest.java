package askmyapi.amaserver.auth.application;

import askmyapi.amaserver.auth.application.port.in.AuthResult;
import askmyapi.amaserver.auth.application.port.in.LoadOrCreateOauthCommand;
import askmyapi.amaserver.auth.application.port.out.CreateMemberCommand;
import askmyapi.amaserver.auth.application.port.out.CreateMemberResult;
import askmyapi.amaserver.auth.application.port.out.MemberCommandPort;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import static askmyapi.amaserver.auth.AuthTestFixture.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * TC
 * 1. 같은 provider와 email을 가진 유저의 AuthResult.Load는 동일하다.
 * 2. 다른 provider와 email을 가진 유저의 AuthResult.Load는 서로 다르다.
 */
@SpringBootTest
@Transactional
public class AuthServiceIntegrationTest {

    @Autowired
    private AuthService authService;

    @MockitoBean
    private MemberCommandPort memberCommandPort;

    @Test
    void 같은_provider와_email을_가진_유저의_AuthResult_Load는_동일하다() {
        // given
        var provider = generateProvider();
        var email = generateEmail();
        var username = generateUsername();
        var profileImageUrl = generateProfileImgUrl();
        var memberId = generateMemberId();

        var command1 = new LoadOrCreateOauthCommand(provider, email, username, profileImageUrl);
        var command2 = new LoadOrCreateOauthCommand(provider, email, username, profileImageUrl);
        when(memberCommandPort.create(any())).thenReturn(new CreateMemberResult(memberId));

        // when
        AuthResult.Load result1 = authService.loadOrCreate(command1);
        AuthResult.Load result2 = authService.loadOrCreate(command2);

        // then
        assertEquals(result1.id(), result2.id());
        assertEquals(result1.memberId(), result2.memberId());
    }

    @Test
    void 다른_provider와_email을_가진_유저의_AuthResult_Load는_서로_다르다() {
        // given
        var provider1 = generateGoogleProvider();
        var provider2 = generateGithubProvider();
        var email1 = generateEmail();
        var email2 = generateEmail();
        var username1 = generateUsername();
        var username2 = generateUsername();
        var profileImageUrl1 = generateProfileImgUrl();
        var profileImageUrl2 = generateProfileImgUrl();
        var memberId1 = generateMemberId();
        var memberId2 = generateMemberId();

        var command1 = new LoadOrCreateOauthCommand(provider1, email1, username1, profileImageUrl1);
        var createMemberCommand1 = new CreateMemberCommand(username1, profileImageUrl1);

        var command2 = new LoadOrCreateOauthCommand(provider2, email2, username2, profileImageUrl2);
        var createMemberCommand2 = new CreateMemberCommand(username2, profileImageUrl2);

        when(memberCommandPort.create(createMemberCommand1)).thenReturn(new CreateMemberResult(memberId1));
        when(memberCommandPort.create(createMemberCommand2)).thenReturn(new CreateMemberResult(memberId2));

        // when
        AuthResult.Load result1 = authService.loadOrCreate(command1);
        AuthResult.Load result2 = authService.loadOrCreate(command2);

        // then
        assertNotEquals(result1, result2);
        assertNotEquals(result1.id(), result2.id());
        assertNotEquals(result1.memberId(), result2.memberId());
    }

}
