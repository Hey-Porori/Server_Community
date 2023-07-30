package porori.backend.community.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static porori.backend.community.config.BaseResponseStatus.SUCCESS;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"statusCode", "message", "data"})
public class BaseResponse<T> {//BaseResponse 객체를 사용할때 성공 경우
    private String message;
    private int statusCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    // 요청에 성공한 경우
    public BaseResponse(T data) {
        this.message = SUCCESS.getMessage();
        this.statusCode = SUCCESS.getCode();
        this.data = data;
    }
}
