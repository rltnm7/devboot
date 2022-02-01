package com.example.devboot.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.example.devboot.entity.UserEntity;

/**
 * ユーザマッパー
 * 
 * @author rlt
 * @since 1.0.0
 */
@Mapper
public interface UsersRepository {

    /**
     * ユーザ情報全件検索.
     * 
     * @return ユーザ情報一覧
     */
    public List<UserEntity> findAll();

    /**
     * ユーザ情報検索.
     * 
     * @param 検索ユーザ名
     * @return ユーザ情報一覧
     */
    public Optional<UserEntity> findByUsername(String username);
}
