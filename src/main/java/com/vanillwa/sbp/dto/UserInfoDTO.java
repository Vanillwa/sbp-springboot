package com.vanillwa.sbp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {
    private long userId;
    private String username;
    private String nickname;
    private String role;
}
