package com.bloggingApplication.bloggingApplication.Service;

import com.bloggingApplication.bloggingApplication.Payloads.PostDto;
import com.bloggingApplication.bloggingApplication.Payloads.PostResponse;

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
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDirc);
  //  List<PostDto> getAllPost();
    // get ALl Post By CategoryId
    PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize,String sortBy,String sortDirc);
    // get All Post By UserID
    PostResponse getPostByUserId(Integer userId, Integer pageNumber, Integer pageSize,String sortBy,String sortDirc);
    // search with Keyword
    List<PostDto> searchKeyword(String keywords);

}
