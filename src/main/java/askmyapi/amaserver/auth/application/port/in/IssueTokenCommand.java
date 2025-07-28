package askmyapi.amaserver.auth.application.port.in;

public record IssueTokenCommand(
        String memberId
) {
    public IssueTokenCommand {
        if (memberId == null || memberId.isBlank()) {
            throw new IllegalArgumentException("Invalid Member ID");
        }
    }
}
