package com.example.devboot.controller.response;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 詳細ユーザ情報レスポンス
 * 
 * @author rlt
 * @since 1.0.0
 */
@Data
public class UserDetailResponse {
    /**
     * id
     */
    private int id;

    /**
     * ユーザ名
     */
    private String username;

    /**
     * 氏名
     */
    private String name;

    /**
     * 生年月日
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthday;

    /**
     * 年齢
     */
    private int age;
}
