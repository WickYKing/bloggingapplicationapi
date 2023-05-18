package com.bloggingApplication.bloggingApplication.Controller;

import com.bloggingApplication.bloggingApplication.Payloads.ApiResponse;
import com.bloggingApplication.bloggingApplication.Payloads.UserDto;
import com.bloggingApplication.bloggingApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    // post - create user
    @PostMapping("/create")
     public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    //put - update user
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer userid){
        UserDto updatedUser = this.userService.updateUser(userDto,userid);
        return ResponseEntity.ok(updatedUser);
    }

    // delete - delete user
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse>deleteUser (@PathVariable("userId") Integer userid){
       this.userService.deleteUser(userid);
       return new ResponseEntity<ApiResponse>(new ApiResponse("User data Deleted Successfully",true), HttpStatus.OK);
    }

    // get - search user by Id
    @GetMapping("/search/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable("userId") Integer userid){
        UserDto getByUserId = this.userService.getUserById(userid);
        return ResponseEntity.ok(getByUserId);

    }
    // get - search all category
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(){
        List<UserDto> getAllUsers = this.userService.getAllUsers();
        return ResponseEntity.ok(getAllUsers);
    }

}
