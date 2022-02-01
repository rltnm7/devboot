
package com.example.devboot.controller;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import com.example.devboot.entity.UserEntity;
import com.example.devboot.controller.response.UserDetailResponse;
import com.example.devboot.controller.response.UserResponse;
import com.example.devboot.service.UsersService;

/**
 * Users Controller.
 * <p>
 * ユーザ情報を取得するコントローラ
 * </p>
 * 
 * @author rlt
 * @since 1.0.0
*/
@Controller
//@RequestMapping("/")
@Slf4j
public class UsersController {

    /**
     * Users Service
     */
    @Autowired
    private UsersService usersService;

    /**
     * ユーザ全件検索.
     * <p>
     * ユーザの全件検索を行い、返却する
     * </p>
     * 
     * @return ユーザ一覧
     */
    @GetMapping("/users")
    public String findAll(Model model) {
        log.debug("ユーザ全件検索");
        Mapper dozerMapper = DozerBeanMapperBuilder.buildDefault();

        List<UserEntity> usersList = usersService.findAll();
        List<UserResponse> usersResponse = new ArrayList<>();
        for (UserEntity user: usersList) {
            usersResponse.add(dozerMapper.map(user, UserResponse.class));
        }
        model.addAttribute("users", usersResponse);
        return "users";
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
    @GetMapping("/users/{username}")
    public String findByUsername(@PathVariable("username") String username, Model model)
        throws NotFoundException {
        log.debug("ユーザ一件検索: {}", username);

        Mapper dozerMapper = DozerBeanMapperBuilder.buildDefault();
        UserEntity user = usersService.findByUsername(username);
        model.addAttribute("user", dozerMapper.map(user, UserDetailResponse.class));
        return "user_detail";
    }

}
