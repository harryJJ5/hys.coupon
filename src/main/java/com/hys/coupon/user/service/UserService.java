package com.hys.coupon.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hys.coupon.user.dto.CreateUserRequest;
import com.hys.coupon.user.entity.UserEntity;
import com.hys.coupon.user.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<UserEntity> listUsers() {
        return userMapper.selectList(new LambdaQueryWrapper<UserEntity>()
            .orderByDesc(UserEntity::getId));
    }

    public UserEntity getUser(Long id) {
        UserEntity entity = userMapper.selectById(id);
        if (entity == null) {
            throw new ResponseStatusException(NOT_FOUND, "用户不存在");
        }
        return entity;
    }

    public UserEntity createUser(CreateUserRequest request) {
        UserEntity entity = new UserEntity();
        entity.setUserName(request.userName());
        entity.setEmail(request.email());
        entity.setStatus("ACTIVE");
        userMapper.insert(entity);
        return entity;
    }
}
