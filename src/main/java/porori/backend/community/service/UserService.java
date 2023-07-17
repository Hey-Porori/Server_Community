package porori.backend.community.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import porori.backend.community.dto.UserReqDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final WebClient webClient;

    public Long getUserId(String token){
        String[] splitToken = token.split(" ");
        if (splitToken.length <= 1) {
            throw new IllegalArgumentException("Invalid token format");
        }

        UserReqDto.AccessTokenReq accessTokenReq = UserReqDto.AccessTokenReq.builder()
                .accessToken(splitToken[1])
                .build();

        String response = webClient.post()
                .uri("/token/me")
                .header("Authorization", token)
                .bodyValue(accessTokenReq)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonElement jsonElement = JsonParser.parseString(response);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        JsonObject data = jsonObject.get("data").getAsJsonObject();

        return data.get("userId").getAsLong();

    }
}
