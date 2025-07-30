package askmyapi.amaserver.auth.application;

import askmyapi.amaserver.IntegrationTest;
import askmyapi.amaserver.auth.application.port.in.AuthResult;
import askmyapi.amaserver.auth.application.port.in.IssueTokenCommand;
import askmyapi.amaserver.auth.application.port.in.LoadOrCreateOauthCommand;
import askmyapi.amaserver.auth.application.port.in.RefreshTokenCommand;
import askmyapi.amaserver.auth.application.port.out.*;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.testcontainers.junit.jupiter.Testcontainers;

import static askmyapi.amaserver.auth.AuthTestFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * TC
 * loadOrCreate
 * 1. 같은 provider와 socialId을 가진 유저의 AuthResult.Load는 동일하다.
 * 2. 다른 provider와 socialId을 가진 유저의 AuthResult.Load는 서로 다르다.
 * issueToken
 * 1. 유효한 IssueTokenCommand에 대해 AuthResult.IssueToken을 반환한다
 * 2. 생성한 토큰 정보를 저장한다.
 * refresh
 * 1. 유효한 RefreshTokenCommand에 대해 AuthResult.IssueToken을 반환한다.
 * 2. 저장된 refresh token이 없으면 IllegalArgumentException 예외를 발생시킨다.
 * 3. 저장된 refresh token과 요청한 refresh token이 다르면 IllegalArgumentException 예외를 발생시킨다.
 * 4. 저장된 refresh token과 요청한 refresh token이 같으면 새로운 access token과 refresh token을 발급한다.
 * 5. 새로운 refresh token을 저장한다.
 */
@IntegrationTest
public class AuthServiceIntegrationTest {

    @Autowired
    private AuthService authService;

    @MockitoBean
    private MemberCommandPort memberCommandPort;

    @MockitoSpyBean
    private AuthCommandPort authCommandPort;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Test
    void 같은_provider와_socialId을_가진_유저의_AuthResult_Load는_동일하다() {
        // given
        var provider = generateProvider();
        var socialId = generateSocialId();
        var username = generateUsername();
        var profileImageUrl = generateProfileImgUrl();
        var memberId = generateMemberId();

        var command1 = new LoadOrCreateOauthCommand(provider, socialId, username, profileImageUrl);
        var command2 = new LoadOrCreateOauthCommand(provider, socialId, username, profileImageUrl);
        when(memberCommandPort.create(any())).thenReturn(new CreateMemberResult(memberId));

        // when
        AuthResult.Load result1 = authService.loadOrCreate(command1);
        AuthResult.Load result2 = authService.loadOrCreate(command2);

        // then
        assertEquals(result1.id(), result2.id());
        assertEquals(result1.memberId(), result2.memberId());
    }

    @Test
    void 다른_provider와_socialId을_가진_유저의_AuthResult_Load는_서로_다르다() {
        // given
        var provider1 = generateGoogleProvider();
        var provider2 = generateGithubProvider();
        var socialId1 = generateSocialId();
        var socialId2 = generateSocialId();
        var username1 = generateUsername();
        var username2 = generateUsername();
        var profileImageUrl1 = generateProfileImgUrl();
        var profileImageUrl2 = generateProfileImgUrl();
        var memberId1 = generateMemberId();
        var memberId2 = generateMemberId();

        var command1 = new LoadOrCreateOauthCommand(provider1, socialId1, username1, profileImageUrl1);
        var createMemberCommand1 = new CreateMemberCommand(username1, profileImageUrl1);

        var command2 = new LoadOrCreateOauthCommand(provider2, socialId2, username2, profileImageUrl2);
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

    @Test
    void issueToken_유효한_IssueTokenCommand에_대해_IssueTokenResult를_반환한다() {
        // given
        var memberId = generateMemberId();
        var command = new IssueTokenCommand(memberId);

        // when
        AuthResult.IssueToken result = authService.issue(command);

        // then
        assertNotNull(result);
        assertNotNull(result.accessToken());
        assertNotNull(result.refreshToken());
    }

    @Test
    void issueToken_생성한_토큰_정보를_저장한다() {
        // given
        var memberId = generateMemberId();
        var command = new IssueTokenCommand(memberId);

        // when
        authService.issue(command);

        // then
        verify(authCommandPort, times(1)).saveToken(any());
    }

    @Test
    void refresh_유효한_RefreshTokenCommand에_대해_IssueTokenResult를_반환한다() {
        // given
        var memberId = generateMemberId();
        var issuedToken = authService.issue(new IssueTokenCommand(memberId));
        var refreshToken = issuedToken.refreshToken();
        var command = new RefreshTokenCommand(memberId, refreshToken);

        //when
        var result = authService.refresh(command);

        // then
        assertNotNull(result);
        assertNotNull(result.accessToken());
        assertNotNull(result.refreshToken());
    }

    @Test
    void refresh_저장된_refresh_token이_없으면_IllegalArgumentException_예외를_발생시킨다() {
        // given
        var memberId = generateMemberId();
        var refreshToken = Jwts.builder().compact();
        var command = new RefreshTokenCommand(memberId, refreshToken);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> authService.refresh(command));
    }

    @Test
    void refresh_저장된_refresh_token과_요청한_refresh_token이_다르면_IllegalArgumentException_예외를_발생시킨다() {
        // given
        var memberId = generateMemberId();
        var differentRefreshToken = Jwts.builder().compact();
        var command = new RefreshTokenCommand(memberId, differentRefreshToken);

        // when & then
        assertThrows(IllegalArgumentException.class, () -> authService.refresh(command));
    }

    @Test
    void refresh_저장된_refresh_token과_요청한_refresh_token이_같으면_새로운_access_token과_refresh_token을_발급한다() {
        // given
        var memberId = generateMemberId();
        var issuedToken = authService.issue(new IssueTokenCommand(memberId));
        var refreshToken = issuedToken.refreshToken();
        var command = new RefreshTokenCommand(memberId, refreshToken);
        // when
        var result = authService.refresh(command);
        // then
        assertNotNull(result);
        assertNotNull(result.accessToken());
        assertNotNull(result.refreshToken());
        assertNotEquals(issuedToken.accessToken(), result.accessToken());
        assertNotEquals(issuedToken.refreshToken(), result.refreshToken());

        var authCommandOrder = inOrder(authCommandPort);
        authCommandOrder.verify(authCommandPort, times(1)).saveToken(new SaveRefreshTokenCommand(command.memberId(), issuedToken.refreshToken(), any()));
        authCommandOrder.verify(authCommandPort, times(1)).saveToken(new SaveRefreshTokenCommand(command.memberId(), result.refreshToken(), any()));
    }
}
