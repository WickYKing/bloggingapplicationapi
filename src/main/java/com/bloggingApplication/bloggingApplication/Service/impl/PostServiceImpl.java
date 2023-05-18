package com.bloggingApplication.bloggingApplication.Service.impl;

import com.bloggingApplication.bloggingApplication.Entity.Category;
import com.bloggingApplication.bloggingApplication.Entity.Post;
import com.bloggingApplication.bloggingApplication.Entity.User;
import com.bloggingApplication.bloggingApplication.Exceptions.ResourceNotFoundException;
import com.bloggingApplication.bloggingApplication.Payloads.PostDto;
import com.bloggingApplication.bloggingApplication.Repository.CategoryRepo;
import com.bloggingApplication.bloggingApplication.Repository.PostRepo;
import com.bloggingApplication.bloggingApplication.Repository.UserRepo;
import com.bloggingApplication.bloggingApplication.Service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId,Integer categoryId) {

       User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
       Post post = this.modelMapper.map(postDto, Post.class);
       post.setImageName("default.png");
       post.setAddDate(new Date());
       post.setUser(user);
       post.setCategory(category);
       Post newPost = this.postRepo.save(post);
       return this.modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post Id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatePost = this.postRepo.save(post);

        return this.modelMapper.map(updatePost, PostDto.class);
    }
    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post Id", postId));
        this.postRepo.delete(post);
    }

//    @Override
//    public List<PostDto> getAllPost() {
//        List<Post> allPost = this.postRepo.findAll();
//        List <PostDto> allPosts = allPost.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
//        return allPosts ;
//    }
    @Override
        public List<PostDto> getAllPost(Integer pageNumber,Integer pageSize) {
        Pageable p = PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> allPost = pagePost.getContent();
        List <PostDto> allPosts = allPost.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return allPosts ;
    }
    @Override
    public PostDto getAllByPostId(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
        List<Post> posts =this.postRepo.findAllByCategory(category);
       List<PostDto> postDtoCat= posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtoCat;
    }

    @Override
    public List<PostDto> getPostByUserId(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        List<Post> posts = this.postRepo.findAllByUser(user);
        List<PostDto> postDtoUser = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtoUser;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        return null;
    }


}
