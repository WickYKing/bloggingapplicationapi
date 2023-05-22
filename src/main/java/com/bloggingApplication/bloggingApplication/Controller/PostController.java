package com.bloggingApplication.bloggingApplication.Controller;

import com.bloggingApplication.bloggingApplication.Config.AppConstant;
import com.bloggingApplication.bloggingApplication.Payloads.ApiResponse;
import com.bloggingApplication.bloggingApplication.Payloads.FileResponse;
import com.bloggingApplication.bloggingApplication.Payloads.PostDto;
import com.bloggingApplication.bloggingApplication.Payloads.PostResponse;
import com.bloggingApplication.bloggingApplication.Service.FileService;
import com.bloggingApplication.bloggingApplication.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;
    @Value("${project.images}")
    private String path;

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
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false)String sortBy,
            @RequestParam(value = "sortDirc",defaultValue = AppConstant.SORT_DIRC,required = false) String sortDirc){
        PostResponse postResponse =this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDirc);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
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
    public ResponseEntity<PostResponse> getPostByUserId(@PathVariable Integer userId,
        @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber,
        @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize,
        @RequestParam(value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false)String sortBy,
        @RequestParam(value = "sortDirc",defaultValue = AppConstant.SORT_DIRC,required = false)String sortDirc){
        PostResponse postResponse =this.postService.getPostByUserId(userId,pageNumber,pageSize,sortBy,sortDirc);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }

//get post by Category ID
    @GetMapping("/category/{categoryId}/posts")

    public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer categoryId,
        @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber,
        @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize,
        @RequestParam(value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false)String sortBy,
        @RequestParam(value = "sortDirc",defaultValue = AppConstant.SORT_DIRC,required = false)String sortDirc) {
        PostResponse postResponse = this.postService.getPostByCategory(categoryId, pageNumber, pageSize,sortBy,sortDirc);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }
// delete post
    @DeleteMapping("/post/delete/{postId}")
    public ResponseEntity<ApiResponse> deletepost (@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse> (new ApiResponse("Post ID has been deleted succsefully", true),HttpStatus.OK);
    }
    @GetMapping("post/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords ){
        List<PostDto> result =this.postService.searchKeyword(keywords);
        return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
    }

    @PostMapping("post/uploadimage/{postId}")
//    public ResponseEntity<FileResponse> fileUpload(@RequestParam("image") MultipartFile image){
//        String fileName = null;
//        try {
//            fileName = this.fileService.uploadImage(path,image);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(new FileResponse(null,"Image not Uploaded Please Try Again"),HttpStatus.INTERNAL_SERVER_ERROR);
////             return ResponseEntity.internalServerError().body(new FileResponse(null, "Image not Uploaded Please Try Again"));
//        }
//        return new ResponseEntity<>(new FileResponse(fileName,"Image Uploaded Successfully"),HttpStatus.OK);
////        return ResponseEntity.ok(new FileResponse(fileName,"Success"));
//    }
    public ResponseEntity<PostDto> imageUpdate(
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId
    ) throws IOException {
        PostDto postDto = this.postService.getAllByPostId(postId);
        String fileName = this.fileService.uploadImage(path,image);
        postDto.setImageName(fileName);
        PostDto updatePost = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);






    }


}
