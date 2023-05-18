package com.bloggingApplication.bloggingApplication.Payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private Integer categoryId;
    @NotEmpty
    @Size(min = 3, max = 20,message = "Please Enter A Valid Title")
    private String categoryTitle;
    @NotEmpty
    @Size(min = 10, max = 200, message ="Please Enter A Valid Description.")
    private String categoryDescription;

}
