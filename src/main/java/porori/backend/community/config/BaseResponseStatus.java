package porori.backend.community.config;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    TOKEN_ERROR(false, 2000, "유효한 토큰이 아닙니다."),

    /**
     * 3000 : Response 오류
     */
    JSON_ERROR(false, 3000, "유효한 JSON 형식이 아닙니다."),


    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 저장에 실패하였습니다.");


    /**
     * 5000 : 필요시 만들어서 쓰세요
     */


    /**
     * 6000 : 필요시 만들어서 쓰세요
     */



    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}