package com.bloggingApplication.bloggingApplication.Payloads;

import com.bloggingApplication.bloggingApplication.Entity.Category;
import com.bloggingApplication.bloggingApplication.Entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class PostDto {
     private Integer postId;
     @NotEmpty
     @Size(min = 3,max = 100,message = "Please Enter Your Title")

     private String title;
     @NotEmpty
     @Size(min = 10,max = 10000,message = "Write Your Content")
     private String content;
     private Date addDate;
     private UserDto user;
     private CategoryDto category;

     private String imageName;

}
