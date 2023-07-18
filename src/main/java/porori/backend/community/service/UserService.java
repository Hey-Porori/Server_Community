package porori.backend.community.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import porori.backend.community.config.BaseException;
import porori.backend.community.dto.UserReqDto;

import static porori.backend.community.config.BaseResponseStatus.JSON_ERROR;
import static porori.backend.community.config.BaseResponseStatus.TOKEN_ERROR;

@Service
@RequiredArgsConstructor
public class UserService {
    private final WebClient webClient;

    public Long getUserId(String token) throws BaseException {
        String[] splitToken = token.split(" ");

        UserReqDto.AccessTokenReq accessTokenReq = UserReqDto.AccessTokenReq.builder()
                .accessToken(splitToken[1])
                .build();

        String response = sendTokenMeRequest(token, accessTokenReq);
        return extractUserIdFromResponse(response);
    }

    //(테스트) 토큰 유효성 검증
    public boolean validationToken(String token) throws BaseException {

        String response = tokenValidationRequest(token);
        JsonElement jsonElement = JsonParser.parseString(response);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int code = jsonObject.get("statusCode").getAsInt();
        System.out.println(code);

        return code == 200;

    }

    private String sendTokenMeRequest(String token, UserReqDto.AccessTokenReq accessTokenReq) throws BaseException {
        try {
            return webClient.post()
                    .uri("/token/me")
                    .header("Authorization", token)
                    .bodyValue(accessTokenReq)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(TOKEN_ERROR);
        }
    }

    private String tokenValidationRequest(String token) throws BaseException {
        try{
            return  webClient.post()
                    .uri("/test/jwt")
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e){
            e.printStackTrace();
            throw new BaseException(TOKEN_ERROR);
        }
    }

    private Long extractUserIdFromResponse(String response) throws BaseException {
        try {
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            JsonObject data = jsonObject.getAsJsonObject("data");
            return data.get("userId").getAsLong();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(JSON_ERROR);
        }
    }

}
