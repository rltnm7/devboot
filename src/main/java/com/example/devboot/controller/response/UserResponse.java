package com.example.devboot.controller.response;

import lombok.Data;

/**
 * ユーザ情報レスポンス
 * 
 * @author rlt
 * @since 1.0.0
 */
@Data
public class UserResponse {
    /**
     * id
     */
    private int id;

    /**
     * ユーザ名
     */
    private String username;
}
