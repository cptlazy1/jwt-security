package com.retrogj.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameSystemDto {

    public Long gameSystemID;
    public String gameSystemName;
    public String gameSystemReview;
    public String gameSystemRating;

}
