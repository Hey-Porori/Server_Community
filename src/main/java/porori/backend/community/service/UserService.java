package porori.backend.community.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import porori.backend.community.config.exception.user.TokenException;
import porori.backend.community.config.exception.user.JsonException;
import porori.backend.community.dto.UserReqDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final WebClient webClient;
    private static final String LOG_FORMAT = "Method : {}";

    public Long getUserId(String token) {
        String[] splitToken = token.split(" ");

        UserReqDto.AccessTokenReq accessTokenReq = UserReqDto.AccessTokenReq.builder()
                .accessToken(splitToken[1])
                .build();

        String response = sendTokenMeRequest(token, accessTokenReq);
        return extractUserIdFromResponse(response);
    }

    //(테스트) 토큰 유효성 검증
    public boolean validationToken(String token) {

        try {
            String response = tokenValidationRequest(token);
            JsonElement jsonElement = JsonParser.parseString(response);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            int code = jsonObject.get("statusCode").getAsInt();

            return code == 200;
        } catch (Exception e) {
            log.warn(LOG_FORMAT, "validation Token");
            throw new TokenException();
        }

    }

    private String sendTokenMeRequest(String token, UserReqDto.AccessTokenReq accessTokenReq) {
        try {
            return webClient.post()
                    .uri("/token/me")
                    .header("Authorization", token)
                    .bodyValue(accessTokenReq)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            log.warn(LOG_FORMAT, "sendTokenMeRequest");
            throw new TokenException();
        }
    }

    private String tokenValidationRequest(String token) {
        try {
            return webClient.post()
                    .uri("/test/jwt")
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            log.warn(LOG_FORMAT, "tokenValidationRequest");
            throw new TokenException();
        }
    }

    private Long extractUserIdFromResponse(String response) {
        try {
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            JsonObject data = jsonObject.getAsJsonObject("data");
            return data.get("userId").getAsLong();
        } catch (Exception e) {
            log.warn(LOG_FORMAT, "extractUserIdFromResponse");
            throw new JsonException();
        }
    }

}
