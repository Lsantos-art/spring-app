package com.springapp.springapp.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.springapp.springapp.configuration.constants.UserConstants;
import com.springapp.springapp.dtos.UserDTO;
import com.springapp.springapp.models.UserModel;
import com.springapp.springapp.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Api(value = "CRUD de usuarios")
@RequestMapping("api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation(value = UserConstants.API_USER_LIST, authorizations = { @Authorization(value = UserConstants.API_AUTH_PREFIX) })
    public ResponseEntity<Page<UserModel>> getUsers(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(pageable));
    }

    @PostMapping
    @ApiOperation(value = UserConstants.API_USER_CREATION, authorizations = { @Authorization(value = UserConstants.API_AUTH_PREFIX) })
    public ResponseEntity<UserModel> storeUser(@RequestBody UserDTO user) {
        var userModel = new UserModel();

        BeanUtils.copyProperties(user, userModel);
        userModel.setCreatedat(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = UserConstants.API_USER_GET, authorizations = {@Authorization(UserConstants.API_AUTH_PREFIX)})
    public ResponseEntity<UserModel> getUser(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = UserConstants.API_USER_UPDATE, authorizations = {@Authorization(value = UserConstants.API_AUTH_PREFIX)})
    public ResponseEntity<Object> updateUser(@PathVariable("id") Integer id, @RequestBody UserDTO user) {
        var userModel = userService.findById(id);

        if (userModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UserConstants.USER_NOT_FOUND);
        }

        BeanUtils.copyProperties(user, userModel);
        userModel.setUpdatedat(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = UserConstants.API_USER_DELETION, authorizations = { @Authorization(value = UserConstants.API_AUTH_PREFIX) })
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Integer id) {
        var userModel = userService.findById(id);

        if (userModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UserConstants.USER_NOT_FOUND);
        }

        userService.delete(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(UserConstants.USER_DELETED);
    }

    @PostMapping("/login")
    @ApiOperation(value = UserConstants.API_USER_LOGIN)
    public String login(@RequestBody UserDTO user) {
        Optional<UserModel> userModel = userService.findByEmail(user.getEmail());

        if (!userModel.isPresent()) {
            return UserConstants.USER_NOT_FOUND;
        }

        boolean checkPassword = userService.checkPassword(user.getPassword(), userModel.get().getPassword());

        if (!checkPassword) {
            return UserConstants.USER_PASSWORD_FAILED;
        }

        String token = JWT.create().withSubject(userModel.get().getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + 600_000))
                .sign(Algorithm.HMAC512(UserConstants.API_AUTH_GUID_PASSWORD));

        return UserConstants.API_AUTH_PREFIX + token;
    }

}
