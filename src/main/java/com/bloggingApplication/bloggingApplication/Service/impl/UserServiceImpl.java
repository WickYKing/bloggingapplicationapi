package com.bloggingApplication.bloggingApplication.Service.impl;

import com.bloggingApplication.bloggingApplication.Entity.User;
import com.bloggingApplication.bloggingApplication.Exceptions.*;
import com.bloggingApplication.bloggingApplication.Payloads.UserDto;
import com.bloggingApplication.bloggingApplication.Repository.*;
import com.bloggingApplication.bloggingApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDto createUser(UserDto userDto) {
         User user = this.dtoToUser(userDto);
         User savedUser = this.userRepo.save(user);
        return this.userToUserdto(savedUser);
    }


    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        user.setName(userDto.getName());
        user.setEmail_id(userDto.getEmail_id());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updateUser = this.userRepo.save(user);

        UserDto userDto1 = this.userToUserdto(updateUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        return this.userToUserdto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToUserdto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        this.userRepo.delete(user);
    }

    private User dtoToUser(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail_id(userDto.getEmail_id());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        return user;
    }

    public UserDto userToUserdto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail_id(user.getEmail_id());
        userDto.setPassword(user.getPassword());
        userDto.setAbout(user.getAbout());
        return userDto;
    }
}
