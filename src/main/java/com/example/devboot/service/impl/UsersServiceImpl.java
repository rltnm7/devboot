package com.example.devboot.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.devboot.repository.UsersRepository;
import com.example.devboot.entity.UserEntity;
import com.example.devboot.service.UsersService;

/**
 * ユーザ検索サービス
 * 
 * @author rlt
 * @since 1.0.0
 */
@Service
@Transactional
@Slf4j
public class UsersServiceImpl implements UsersService {

    /**
     * ユーザリポジトリ
     */
    @Autowired
    private UsersRepository usersRepository;

    /**
     * 年齢計算.
     * <p>
     * 誕生日から年齢を計算する
     * </p>
     * 
     * @param birthday 誕生日
     * @return 年齢
     */
    private int calculateAge(LocalDate birthday) {
        return (int) ChronoUnit.YEARS.between(birthday, LocalDate.now());
    }
    
    /**
     * ユーザ全件検索.
     * <p>
     * ユーザの全件検索を行い、返却する
     * </p>
     * 
     * @return ユーザ一覧
     */
    public List<UserEntity> findAll() {
        log.debug("ユーザ全件検索");
        List<UserEntity> usersList = usersRepository.findAll();
        for(UserEntity user: usersList) {
            user.setAge(this.calculateAge(user.getBirthday()));
        }
        return usersList;
    }

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
    public UserEntity findByUsername(String username) throws NotFoundException {
        log.debug("ユーザ一件検索: {}", username);

        Optional<UserEntity> user = usersRepository.findByUsername(username);

        if (!user.isPresent()) {
            String message = String.format("Request Username %s is NOT FOUND.", username);
            log.error(message);
            throw new NotFoundException(message);
        }

        UserEntity userEntity = user.get();

        // 生年月日情報から年齢を計算する
        int age = this.calculateAge(userEntity.getBirthday());
        userEntity.setAge(age);

        return userEntity;
    }

}
