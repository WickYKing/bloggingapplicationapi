package com.bloggingApplication.bloggingApplication.Payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private Integer id;
    @NotEmpty(message = "Name Cannot be Blank")
    private String name;
    @Email
    private String email_id;
    @NotEmpty
    private String password;
    @NotEmpty
    @Size(min = 10 ,max = 200, message = "About must be between 10 to 200 characters")
    private String about;
}
