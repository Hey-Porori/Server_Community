package porori.backend.community.domain.user;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
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
