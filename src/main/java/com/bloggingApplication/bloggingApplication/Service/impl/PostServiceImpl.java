package com.bloggingApplication.bloggingApplication.Service.impl;

import com.bloggingApplication.bloggingApplication.Entity.Category;
import com.bloggingApplication.bloggingApplication.Entity.Post;
import com.bloggingApplication.bloggingApplication.Entity.User;
import com.bloggingApplication.bloggingApplication.Exceptions.ResourceNotFoundException;
import com.bloggingApplication.bloggingApplication.Payloads.PostDto;
import com.bloggingApplication.bloggingApplication.Payloads.PostResponse;
import com.bloggingApplication.bloggingApplication.Repository.CategoryRepo;
import com.bloggingApplication.bloggingApplication.Repository.PostRepo;
import com.bloggingApplication.bloggingApplication.Repository.UserRepo;
import com.bloggingApplication.bloggingApplication.Service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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
        public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDirc) {
        Sort sort = null;
        if(sortDirc.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }else{
            sort=Sort.by(sortBy).descending();
        }
        Pageable p = PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> allPost = pagePost.getContent();
        List <PostDto> allPosts = allPost.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(allPosts);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElement(pagePost.getTotalElements());
        postResponse.setTotalPage(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }
    @Override
    public PostDto getAllByPostId(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getPostByCategory(Integer categoryId,Integer pageNumber, Integer pageSize,String sortBy,String sortDirc) {
        Sort sort=null;
        if (sortDirc.equalsIgnoreCase("asc")) {
            sort=Sort.by(sortBy).ascending();
        }else{
            sort=Sort.by(sortBy).descending();
        }
        Pageable p = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        Page<Post> postPage= this.postRepo.findAll(p);

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
        List<Post> posts = postPage.getContent();
       List<PostDto> postDtoCat= posts.stream().map((post)-> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
       PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoCat);
        postResponse.setPageNumber(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElement(postPage.getTotalElements());
        postResponse.setTotalPage(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());
        return postResponse;
    }

    @Override
    public PostResponse getPostByUserId(Integer userId, Integer pageNumber, Integer pageSize,String sortBy,String sortDirc) {
//        Sort sort=null;
//        if(sortDirc.equalsIgnoreCase("asc")){
//            sort=Sort.by(sortBy).ascending();
//        }else{
//            sort = Sort.by(sortBy).descending();
//        }
        Sort sort = (sortDirc.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        Page<Post> postPageid= this.postRepo.findAll(p);
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        List<Post> posts = postPageid.getContent();
        List<PostDto> postDtoUser = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoUser);
        postResponse.setPageNumber(postPageid.getNumber());
        postResponse.setPageSize(postPageid.getSize());
        postResponse.setTotalElement(postPageid.getTotalElements());
        postResponse.setTotalPage(postPageid.getTotalPages());
        postResponse.setLastPage(postPageid.isLast());
        return postResponse;
    }

    @Override
    public List<PostDto> searchKeyword(String keywords) {
        List<Post> posts = this.postRepo.findByTitleContaining(keywords);
        List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
