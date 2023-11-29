package com.retrogj.security.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameSystemConditionDto {

    public Long gameSystemConditionID;
    public Boolean hasBox;
    public Boolean hasCables;
    public Boolean isModified;

}
