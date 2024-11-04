package com.sejong.project.pm.post.controller;

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
    public ResponseEntity<String> createpost(@RequestBody PostRequest.postRequestDto postRequestDto){
        postService.createPost(postRequestDto);
        //memberpost_list에 넣는법 모름... 도와줘
        return ResponseEntity.ok("success");
    }

    @PutMapping("/rewritepost/{postId}")
    public ResponseEntity<String> rewritepost(@RequestBody PostRequest.postRequestDto postRequestDto, @PathVariable Long postId){
        postService.rewritePost(postRequestDto, postId);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/deletepost")
    public ResponseEntity<String> deletepost(@RequestBody Map<String, Long> data){
        Long postId = data.get("postId");
        postService.deletePost(postId);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/allposts")
    public List<PostResponse.allpostsresponseDto> allposts(@RequestBody Map<String, String> data){
        String gender = data.get("gender");
        return postService.allposts(gender);

    }

    @GetMapping("/myposts")
    public List<PostResponse.mypostsresponseDto> myposts(@RequestBody Map<String, Long> data){
        Long memberId = data.get("memberId");
        return postService.myposts(memberId);
    }

    @GetMapping("/myapplicants")
    public List<PostResponse.mypostsresponseDto> myapplicants(@RequestBody Map<String, Long> data){
        Long memberId = data.get("memberId");
        return postService.myapplicants(memberId);
    }

    @PostMapping("/applytopost")
    public ResponseEntity<String> applytopost(@RequestBody Map<String, Long> data) throws Exception {
        Long postId = data.get("postId");
        Long memberId = data.get("memberId");

        postService.applyToPost(postId,memberId);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/checkmembercount")
    public PostResponse.membercount checkmembercount(@RequestBody Map<String,Long> data){
        Long postId = data.get("postId");
        return postService.checkmembercount(postId);
    }


}
