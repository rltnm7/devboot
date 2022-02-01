package com.example.devboot.service;

import java.util.List;

import org.apache.ibatis.javassist.NotFoundException;

import com.example.devboot.entity.UserEntity;

/**
 * ユーザ検索サービスインターフェース
 * 
 * @author rlt
 * @since 1.0.0
 */
public interface UsersService {
    /**
     * ユーザ全件検索.
     * <p>
     * ユーザの全件検索を行い、返却する
     * </p>
     * 
     * @return ユーザ一覧
     */
    public List<UserEntity> findAll();

    /**
     * ユーザ検索.
     * <p>
     * 指定ユーザの検索を行い、返却する
     * </p>
     * 
     * @param username 検索ユーザ名
     * @return 検索結果
     * @throws NotFoundException ユーザ名のユーザが存在しない場合に投げられる例外
     */
    public UserEntity findByUsername(String username) throws NotFoundException;
}
