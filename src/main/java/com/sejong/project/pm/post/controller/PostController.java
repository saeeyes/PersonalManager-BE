package com.sejong.project.pm.post.controller;

import com.sejong.project.pm.global.exception.BaseResponse;
import com.sejong.project.pm.post.dto.PostRequest;
import com.sejong.project.pm.post.dto.PostResponse;

import com.sejong.project.pm.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/createpost")
    public BaseResponse<?> createpost(@RequestBody PostRequest.postRequestDto postRequestDto){
        postService.createPost(postRequestDto);
        return BaseResponse.onSuccess("success");
    }

    @PutMapping("/rewritepost/{postId}")
    public  BaseResponse<?> rewritepost(@RequestBody PostRequest.postRequestDto postRequestDto, @PathVariable("postId") Long postId){
        postService.rewritePost(postRequestDto, postId);
        return BaseResponse.onSuccess("success");
    }

    @DeleteMapping("/deletepost")
    public  BaseResponse<?> deletepost(@RequestBody Map<String, Long> data){
        Long postId = data.get("postId");
        postService.deletePost(postId);
        return BaseResponse.onSuccess("success");
    }

    @GetMapping("/allposts")
    public  BaseResponse<?> allposts(@RequestBody Map<String, String> data){
        String gender = data.get("gender");
        return BaseResponse.onSuccess(postService.allposts(gender));

    }

    @GetMapping("/myposts")
    public BaseResponse<?> myposts(@RequestBody Map<String, Long> data){
        Long memberId = data.get("memberId");
        return BaseResponse.onSuccess(postService.myposts(memberId));
    }

    @GetMapping("/myapplicants")
    public  BaseResponse<?> myapplicants(@RequestBody Map<String, Long> data){
        Long memberId = data.get("memberId");
        return BaseResponse.onSuccess(postService.myapplicants(memberId));
    }

    @PostMapping("/applytopost")
    public  BaseResponse<?> applytopost(@RequestBody Map<String, Long> data) throws Exception {
        Long postId = data.get("postId");
        Long memberId = data.get("memberId");
        System.out.println("?");
        postService.applyToPost(postId,memberId);

        return BaseResponse.onSuccess("success");
    }

    @GetMapping("/checkmembercount")
    public  BaseResponse<?> checkmembercount(@RequestBody Map<String,Long> data){
        Long postId = data.get("postId");
        return BaseResponse.onSuccess(postService.checkmembercount(postId));
    }
}
