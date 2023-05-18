package com.bloggingApplication.bloggingApplication.Repository;

import com.bloggingApplication.bloggingApplication.Entity.Category;
import com.bloggingApplication.bloggingApplication.Entity.Post;
import com.bloggingApplication.bloggingApplication.Entity.User;
import com.bloggingApplication.bloggingApplication.Payloads.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {

    List<Post> findAllByUser(User user);
    List<Post> findAllByCategory(Category category);
}
