package com.example.devboot.service.impl;

import com.example.devboot.entity.UserEntity;
import com.example.devboot.repository.UsersRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UsersServiceImplTest {

    private AutoCloseable closeable;

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersServiceImpl usersService;

    @BeforeEach
    void beforeEach() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void afterEach() throws Exception {
        closeable.close();
    }

    @Test
    void test_findAll() throws Exception {

        List<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(new UserEntity(
            1,
            "nana-mizuki",
            "Nana Mizuki",
            LocalDate.of(1980, 1, 21),
            0));
        userEntityList.add(new UserEntity(
            2,
            "maaya-uchida",
            "Maaya Uchida",
            LocalDate.of(1989, 12, 27),
            0));

        when(usersRepository.findAll())
            .thenReturn(userEntityList);

        List<UserEntity> userEntityListResult = usersService.findAll();

        assertAll("findAll",
            () -> assertEquals(1, userEntityListResult.get(0).getId()),
            () -> assertEquals("nana-mizuki", userEntityListResult.get(0).getUsername()),
            () -> assertEquals("Nana Mizuki", userEntityListResult.get(0).getName()),
            () -> assertEquals(userEntityListResult.get(0).getBirthday(), LocalDate.of(1980, 1, 21)),
            () -> assertEquals((int) ChronoUnit.YEARS.between(LocalDate.of(1980, 1, 21), LocalDate.now()),
                userEntityListResult.get(0).getAge()),
            () -> assertEquals(2, userEntityListResult.get(1).getId()),
            () -> assertEquals("maaya-uchida", userEntityListResult.get(1).getUsername()),
            () -> assertEquals("Maaya Uchida", userEntityListResult.get(1).getName()),
            () -> assertEquals(userEntityListResult.get(1).getBirthday(), LocalDate.of(1989, 12, 27)),
            () -> assertEquals((int) ChronoUnit.YEARS.between(LocalDate.of(1989, 12, 27), LocalDate.now()),
                userEntityListResult.get(1).getAge())
        );
    }

    @Test
    void test_findByUsername() throws Exception {

        Optional<UserEntity> userEntity = Optional.ofNullable(new UserEntity(
            1,
            "nana-mizuki",
            "Nana Mizuki",
            LocalDate.of(1980, 1, 21),
            0));

        when(usersRepository.findByUsername("nana-mizuki"))
            .thenReturn(userEntity);

        UserEntity userEntityResult = usersService.findByUsername("nana-mizuki");

        assertAll("findByUsername",
            () -> assertEquals(1, userEntityResult.getId()),
            () -> assertEquals("nana-mizuki", userEntityResult.getUsername()),
            () -> assertEquals("Nana Mizuki", userEntityResult.getName()),
            () -> assertEquals(userEntityResult.getBirthday(), LocalDate.of(1980,1,21)),
            () -> assertEquals((int) ChronoUnit.YEARS.between(LocalDate.of(1980, 1, 21), LocalDate.now()),
                userEntityResult.getAge())
        );

    }

    @Test
    void test_findByUsername_NotFound() throws Exception {

        final String acctualMessage = "Request Username invalid-user is NOT FOUND.";
        Optional<UserEntity> userEntity = Optional.ofNullable(null);

        when(usersRepository.findByUsername("invalid-user"))
            .thenReturn(userEntity);

        NotFoundException ex = assertThrows(NotFoundException.class, () -> {
            usersService.findByUsername("invalid-user");
        });

        assertEquals(acctualMessage, ex.getMessage());

    }

}
