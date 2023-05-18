package com.bloggingApplication.bloggingApplication.Controller;

import com.bloggingApplication.bloggingApplication.Payloads.ApiResponse;
import com.bloggingApplication.bloggingApplication.Payloads.PostDto;
import com.bloggingApplication.bloggingApplication.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/")
public class PostController {

    @Autowired
    private PostService postService;
//create post
    @PostMapping("user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
        return new ResponseEntity<PostDto>(postService.createPost(postDto,userId,categoryId),HttpStatus.CREATED);
    }
//Update Post
    @PutMapping("/post/update/{postId}/")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        return new ResponseEntity<PostDto>(postService.updatePost(postDto,postId),HttpStatus.OK);
    }

// get all posts
    @GetMapping("/posts/all")
    public ResponseEntity<List<PostDto>> getAllPost(
            @RequestParam(value = "pageNumber",defaultValue = "1",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "4",required = false) Integer pageSize){
        List<PostDto> posts =this.postService.getAllPost(pageNumber,pageSize);
        return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
    }
//       public ResponseEntity<List<PostDto>> getAllPost(){
//        List<PostDto> posts =this.postService.getAllPost();
//        return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
//    }

// get post  with ids
    @GetMapping("/post/{postId}/")
    public ResponseEntity<?> getAllByPostId(@PathVariable Integer postId){
       PostDto getPostByUserId =this.postService.getAllByPostId(postId);
        return ResponseEntity.ok(getPostByUserId);
    }


// get post by User Id
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable Integer userId){
        List<PostDto> posts = this.postService.getPostByUserId(userId);
        return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
    }

//get post by Category ID
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
        List<PostDto> posts = this.postService.getPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }
// delete post
    @DeleteMapping("/post/delete/{postId}")
    public ResponseEntity<ApiResponse> deletepost (@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse> (new ApiResponse("Post ID has been deleted succsefully", true),HttpStatus.OK);
    }
}
