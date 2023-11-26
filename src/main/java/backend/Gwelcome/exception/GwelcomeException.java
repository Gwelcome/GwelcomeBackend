package backend.Gwelcome.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GwelcomeException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    public GwelcomeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        if (message == null) {
            message = errorCode.getMessage();
        }
        return message;
    }
}