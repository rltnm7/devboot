package com.example.devboot.repository;

import com.example.devboot.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class UsersRepositoryTest {
    
    @Autowired
    private UsersRepository usersRepository;
    
    @Test
    public void test_findAll() {

        List<UserEntity> userEntityList = usersRepository.findAll();
        assertAll("findAll",
            () -> assertEquals(1, userEntityList.get(0).getId()),
            () -> assertEquals("nana-mizuki", userEntityList.get(0).getUsername()),
            () -> assertEquals("Nana Mizuki", userEntityList.get(0).getName()),
            () -> assertEquals(userEntityList.get(0).getBirthday(), LocalDate.of(1980, 1, 21)),
            () -> assertEquals(0, userEntityList.get(0).getAge()),
            () -> assertEquals(2, userEntityList.get(1).getId()),
            () -> assertEquals("maaya-uchida", userEntityList.get(1).getUsername()),
            () -> assertEquals("Maaya Uchida", userEntityList.get(1).getName()),
            () -> assertEquals(userEntityList.get(1).getBirthday(), LocalDate.of(1989, 12, 27)),
            () -> assertEquals(0, userEntityList.get(1).getAge())
        );
    }

    @Test
    public void test_findByUsername() {
        Optional<UserEntity> userEntity = usersRepository.findByUsername("nana-mizuki");

        assertAll("findByUsername",
            () -> assertEquals(1, userEntity.get().getId()),
            () -> assertEquals("nana-mizuki", userEntity.get().getUsername()),
            () -> assertEquals("Nana Mizuki", userEntity.get().getName()),
            () -> assertEquals(userEntity.get().getBirthday(), LocalDate.of(1980, 1, 21)),
            () -> assertEquals(0, userEntity.get().getAge())
        );

    }
    
}
