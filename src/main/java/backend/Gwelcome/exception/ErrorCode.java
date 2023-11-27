package backend.Gwelcome.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일 계정입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 회원의 정보 입니다."),
    POLICY_NOT_FOUND(HttpStatus.NOT_FOUND, "찾고자 하는 청년 정책이 없습니다."),
    JWT_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST,"JWT 토큰이 만료되었습니다."),
    JWT_UNSUPPORTED(HttpStatus.BAD_REQUEST,"지원하지 않는 JWT 토큰입니다."),
    JWT_MALFORMED(HttpStatus.BAD_REQUEST,"올바른 JWT 토큰의 형태가 아닙니다."),
    JWT_SIGNATURE(HttpStatus.BAD_REQUEST,"올바른 SIGNATURE가 아닙니다."),
    JWT_ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST,"잘못된 정보를 넣었습니다.");
    private final HttpStatus httpstatus;
    private final String message;
}