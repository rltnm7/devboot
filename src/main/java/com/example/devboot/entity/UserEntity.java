package com.example.devboot.entity;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザ情報
 * 
 * @author rlt
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {
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
    private LocalDate birthday;

    /**
     * 年齢
     */
    private int age;
}
