package com.bloggingApplication.bloggingApplication.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;

    @Column(name = "post_title", length = 100,nullable = false)
    private String title;
    @Column(length = 10000,nullable = false)
    private String content;
    private String imageName;
    private Date addDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

}
