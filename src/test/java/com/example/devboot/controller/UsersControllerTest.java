package com.example.devboot.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import com.example.devboot.controller.response.UserDetailResponse;
import com.example.devboot.controller.response.UserResponse;
import com.example.devboot.entity.UserEntity;
import com.example.devboot.service.UsersService;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {

    @Autowired
    MockMvc mockMvc;

    private AutoCloseable closeable;

    @Mock
    private UsersService usersService;

    @InjectMocks
    private UsersController usersController;

    // ユーザーエージェント文字列
    private static final String USER_AGENT =
        "Mozilla/5.0 (iPhone; CPU iPhone OS 13_0 like Mac OS X) " +
        "AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 " +
        "Mobile/15E148 Safari/604.1";

    @BeforeEach
    void beforeEach() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void afeterEach() throws Exception {
        closeable.close();
    }

    @Test
    void test_findAll() throws Exception {

        List<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(new UserEntity(
            1,
            "nana-mizuki",
            "Nana Mizuki",
            LocalDate.of(1980,1,21),
            0));
        userEntityList.add(new UserEntity(
            2,
            "maaya-uchida",
            "Maaya Uchida",
            LocalDate.of(1989,12,27),
            0));

        List<UserResponse> userResponseList = new ArrayList<>();
        userResponseList.add(new UserResponse());
        userResponseList.add(new UserResponse());
        userResponseList.get(0).setId(1);
        userResponseList.get(0).setUsername("nana-mizuki");
        userResponseList.get(1).setId(2);
        userResponseList.get(1).setUsername("maaya-uchida");

        when(usersService.findAll())
            .thenReturn(userEntityList);

        mockMvc
            .perform(get("/users").header(HttpHeaders.USER_AGENT, USER_AGENT))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("users"))
            .andExpect(model().attribute("users", userResponseList))
            .andExpect(content().contentType("text/html;charset=UTF-8"))
            .andExpect(content().string(containsString(userResponseList.get(0).getUsername())))
            .andExpect(content().string(containsString(userResponseList.get(1).getUsername())));

    }

    @Test
    void test_findByUsername() throws Exception {

        UserEntity userEntity = new UserEntity(
            1,
            "nana-mizuki",
            "Nana Mizuki",
            LocalDate.of(1980,1,21),
            (int) ChronoUnit.YEARS.between(LocalDate.of(1980, 1, 21), LocalDate.now()));

        UserDetailResponse userResponse = new UserDetailResponse();
        userResponse.setId(1);
        userResponse.setUsername("nana-mizuki");
        userResponse.setName("Nana Mizuki");
        userResponse.setBirthday(LocalDate.of(1980, 1, 21));
        userResponse.setAge((int) ChronoUnit.YEARS.between(LocalDate.of(1980, 1, 21), LocalDate.now()));

        when(usersService.findByUsername("nana-mizuki"))
            .thenReturn(userEntity);

        mockMvc
            .perform(get("/users/nana-mizuki").header(HttpHeaders.USER_AGENT, USER_AGENT))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("user_detail"))
            .andExpect(model().attribute("user", userResponse))
            .andExpect(content().contentType("text/html;charset=UTF-8"))
            .andExpect(content().string(containsString(userResponse.getName())))
            .andExpect(content().string(containsString(String.valueOf(userResponse.getAge()))));
    }

}
