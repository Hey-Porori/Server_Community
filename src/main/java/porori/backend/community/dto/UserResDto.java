package porori.backend.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserResDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfo {
        private Long userId;
        private String name;
        private String nickName;
        private String phoneNumber;
        private boolean gender;
        private String address;
        private String backgroundColor;
        private String email;
        private String imageUrl;
    }


}
