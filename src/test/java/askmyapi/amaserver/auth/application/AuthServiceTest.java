package askmyapi.amaserver.auth.application;

import askmyapi.amaserver.auth.application.port.in.AuthResult;
import askmyapi.amaserver.auth.application.port.in.LoadOrCreateOauthCommand;
import askmyapi.amaserver.auth.application.port.out.*;
import askmyapi.amaserver.auth.domain.OauthAuthInfo;
import askmyapi.amaserver.auth.domain.vo.AuthId;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static askmyapi.amaserver.auth.AuthTestFixture.*;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TC
 * loadOrCreate
 * 1. 유효한 요청에 대해 AuthResult.Load를 반환한다.
 * 2. 존재하는 provider와 email이면 조회된 정보를 반환한다.
 * 3. 존재하지 않는 provider와 email이면 회원서비스로 생성 요청을 하고 회원 정보를 저장한 뒤 반환한다.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    private MemberCommandPort memberCommandPort;
    @Mock
    private AuthCommandPort authCommandPort;
    @Mock
    private AuthQueryPort authQueryPort;

    @Test
    void loadOrCreate_유효한_요청에_대해_AuthResult_Load를_반환한다() {
        // given
        var provider = generateProvider();
        var email = generateEmail();
        var username = generateUsername();
        var profileImageUrl = generateProfileImgUrl();
        var command = new LoadOrCreateOauthCommand(
                provider,
                email,
                username,
                profileImageUrl
        );

        when(authQueryPort.findOauthAuthInfo(any())).thenReturn(Optional.ofNullable(Instancio.of(OauthAuthInfo.class).create()));

        // when
        AuthResult.Load result = authService.loadOrCreate(command);

        // then
        assertNotNull(result);
    }

    @Test
    void loadOrCreate_존재하는_provider와_email이면_조회된_정보를_반환한다() {
        // given
        var provider = generateProvider();
        var email = generateEmail();
        var command = new LoadOrCreateOauthCommand(provider, email, generateUsername(), generateProfileImgUrl());
        var authInfo = Instancio.of(OauthAuthInfo.class)
                .set(field(OauthAuthInfo::getAuthId), AuthId.create())
                .set(field(OauthAuthInfo::getMemberId), generateMemberId())
                .create();
        var expectedAuthId = authInfo.getAuthId().getValue();
        var expectedMemberId = authInfo.getMemberId();

        when(authQueryPort.findOauthAuthInfo(any())).thenReturn(Optional.of(authInfo));

        // when
        AuthResult.Load result = authService.loadOrCreate(command);

        // then
        assertNotNull(result);
        assertEquals(expectedAuthId, result.id());
        assertEquals(expectedMemberId, result.memberId());
        verify(authQueryPort, times(1)).findOauthAuthInfo(any());
        verify(authCommandPort, never()).createOauthAuthInfo(any());
        verify(memberCommandPort, never()).create(any());
    }

    @Test
    void loadOrCreate_존재하지_않는_provider와_email이면_회원서비스로_생성_요청을_하고_회원_정보를_저장한_뒤_반환한다() {
        // given
        var provider = generateProvider();
        var email = generateEmail();
        var command = new LoadOrCreateOauthCommand(provider, email, generateUsername(), generateProfileImgUrl());
        var createMemberResult = Instancio.of(CreateMemberResult.class).create();
        var authInfo = Instancio.of(OauthAuthInfo.class).create();

        when(authQueryPort.findOauthAuthInfo(any())).thenReturn(Optional.empty());
        when(memberCommandPort.create(any())).thenReturn(createMemberResult);
        when(authCommandPort.createOauthAuthInfo(any())).thenReturn(authInfo);

        // when
        AuthResult.Load result = authService.loadOrCreate(command);

        // then
        assertNotNull(result);
        verify(authQueryPort, times(1)).findOauthAuthInfo(any());
        verify(memberCommandPort, times(1)).create(any());
        verify(authCommandPort, times(1)).createOauthAuthInfo(any());
    }

    @Test
    void loadOrCreate_같은_provider와_email을_가진_유저의_AuthResult_Load는_동일하다() {
        // given
        var provider = generateProvider();
        var email = generateEmail();
        var command = new LoadOrCreateOauthCommand(provider, email, generateUsername(), generateProfileImgUrl());
        var authInfo = Instancio.of(OauthAuthInfo.class).create();

        when(authQueryPort.findOauthAuthInfo(any())).thenReturn(Optional.of(authInfo));

        // when
        AuthResult.Load result1 = authService.loadOrCreate(command);
        AuthResult.Load result2 = authService.loadOrCreate(command);

        // then
        assertEquals(result1.id(), result2.id());
        assertEquals(result1.memberId(), result2.memberId());
    }
}