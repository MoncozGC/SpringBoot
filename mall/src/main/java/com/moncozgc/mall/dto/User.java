package com.moncozgc.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer UI_ID;
    private String UI_USER_NAME;
    private String UI_PASSWORD;
    private String UI_STATUS;
    private Long UI_CREATE_TIME;
    private String UI_ROLES;
}