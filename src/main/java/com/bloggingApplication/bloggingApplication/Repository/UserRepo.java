package com.bloggingApplication.bloggingApplication.Repository;

import com.bloggingApplication.bloggingApplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer>{
}
