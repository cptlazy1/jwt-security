package com.retrogj.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    public String username;
//    public String password;
    public String email;
    public Boolean profileIsPrivate;
//    public String role;
}
