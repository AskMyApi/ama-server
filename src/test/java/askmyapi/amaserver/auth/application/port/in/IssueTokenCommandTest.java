package askmyapi.amaserver.auth.application.port.in;


import org.junit.jupiter.api.Test;

import static askmyapi.amaserver.auth.AuthTestFixture.generateMemberId;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TC
 * 1. memberId로 생성한다
 * 2. memberId가 null이면 IllegalArgumentException 예외를 던진다
 * 3. memberId가 빈 문자열이면 IllegalArgumentException 예외를 던진다
 */
class IssueTokenCommandTest {

    @Test
    void memberId로_생성한다() {
        // Arrange
        String memberId = generateMemberId();

        // Act & Assert
        IssueTokenCommand command = new IssueTokenCommand(memberId);
        assertNotNull(command);
    }

    @Test
    void memberId가_null이면_IllegalArgumentException_예외를_던진다() {
        // Arrange
        String memberId = null;
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new IssueTokenCommand(memberId);
        }, "Invalid Member ID");
    }

    @Test
    void memberId가_빈_문자열이면_IllegalArgumentException_예외를_던진다() {
        // Arrange
        String memberId = "";
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new IssueTokenCommand(memberId);
        }, "Invalid Member ID");
    }
}