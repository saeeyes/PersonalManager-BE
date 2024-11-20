package com.sejong.project.pm.post.controller;

import com.sejong.project.pm.global.exception.BaseResponse;
import com.sejong.project.pm.member.dto.MemberDetails;
import com.sejong.project.pm.post.dto.PostRequest;
import com.sejong.project.pm.post.dto.PostResponse;

import com.sejong.project.pm.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/createpost")
    public BaseResponse<?> createpost(@AuthenticationPrincipal MemberDetails member, @RequestBody PostRequest.postRequestDto postRequestDto){
        postService.createPost(member, postRequestDto);
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
    public  BaseResponse<?> allposts(@AuthenticationPrincipal MemberDetails member){
        return BaseResponse.onSuccess(postService.allposts(member));
    }

    @GetMapping("/myposts")
    public BaseResponse<?> myposts(@AuthenticationPrincipal MemberDetails member){
        return BaseResponse.onSuccess(postService.myposts(member));
    }

    @GetMapping("/myapplicants")
    public  BaseResponse<?> myapplicants(@AuthenticationPrincipal MemberDetails member){
        return BaseResponse.onSuccess(postService.myapplicants(member));
    }

    @PostMapping("/applytopost")
    public  BaseResponse<?> applytopost(@AuthenticationPrincipal MemberDetails member, @RequestBody Map<String, Long> data) throws Exception {
        Long postId = data.get("postId");
        postService.applyToPost(postId,member);
        return BaseResponse.onSuccess("success");
    }

   @DeleteMapping("/deleteapplicant")
   public BaseResponse<?> deleteapplicant(@AuthenticationPrincipal MemberDetails member, @RequestBody Map<String, Long> data) throws Exception{
       Long postId = data.get("postId");
       postService.deleteApplicant(postId, member);
       return BaseResponse.onSuccess("success");
   }

    @GetMapping("/checkmembercount")
    public  BaseResponse<?> checkmembercount(@RequestParam(name = "postId") Long postId){
        return BaseResponse.onSuccess(postService.checkmembercount(postId));
    }
}
