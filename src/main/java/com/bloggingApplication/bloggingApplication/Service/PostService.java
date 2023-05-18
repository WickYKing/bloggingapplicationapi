package com.bloggingApplication.bloggingApplication.Service;

import com.bloggingApplication.bloggingApplication.Payloads.PostDto;

import java.util.List;

public interface PostService {
    //Create
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
    // update
    PostDto updatePost(PostDto postDto, Integer postId);
    // Delete
    void deletePost(Integer postId);
    // getByPostId
    PostDto getAllByPostId(Integer postId);
    //get All Post
   List<PostDto> getAllPost(Integer pageNumber, Integer pageSize);
  //  List<PostDto> getAllPost();
    // get ALl Post By CategoryId
    List<PostDto> getPostByCategory(Integer categoryId);
    // get All Post By UserID
    List<PostDto> getPostByUserId(Integer userId);
    // search with Keyword
    List<PostDto> searchPost(String keyword);
}
