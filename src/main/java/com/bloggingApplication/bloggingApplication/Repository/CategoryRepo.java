package com.bloggingApplication.bloggingApplication.Repository;

import com.bloggingApplication.bloggingApplication.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
