package com.sejong.project.pm.post.service;

import com.sejong.project.pm.global.exception.BaseException;
import com.sejong.project.pm.global.handler.MyExceptionHandler;
import com.sejong.project.pm.member.dto.MemberDetails;
import com.sejong.project.pm.member.model.Member;
import com.sejong.project.pm.member.repository.MemberRepository;
import com.sejong.project.pm.post.MemberPost;
import com.sejong.project.pm.post.Post;
import com.sejong.project.pm.post.PostApplication;
import com.sejong.project.pm.post.dto.PostRequest;
import com.sejong.project.pm.post.dto.PostResponse;
import com.sejong.project.pm.post.repository.PostApplicationRepository;
import com.sejong.project.pm.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sejong.project.pm.global.exception.codes.ErrorCode.*;


@Service
public class PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostApplicationRepository postApplicationRepository;
    public void createPost(MemberDetails memberDetails, PostRequest.postRequestDto postRequestDto){
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        Post newpost = new Post(postRequestDto, member.getId());
        MemberPost memberPost = MemberPost.createMemberPost(member, newpost);
        newpost.getMemberPostList().add(memberPost);
        postRepository.save(newpost);
    }

    public void rewritePost(PostRequest.postRequestDto postRequestDto, Long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post findPost = optionalPost.get();

        if (postRequestDto.postTitle() != null) {
            findPost.setPostTitle(postRequestDto.postTitle());
        }
        if (postRequestDto.postContent() != null) {
            findPost.setPostContent(postRequestDto.postContent());
        }
        if (postRequestDto.exerciseName() != null) {
            findPost.setExerciseName(postRequestDto.exerciseName());
        }
        if (postRequestDto.meetTime() != null) {
            findPost.setMeetTime(postRequestDto.meetTime());
        }
        if (postRequestDto.meetPlace() != null) {
            findPost.setMeetPlace(postRequestDto.meetPlace());
        }
        if (postRequestDto.numberOfPeople() > 0) { // 숫자의 경우 기본값 체크
            findPost.setNumberOfPeople(postRequestDto.numberOfPeople());
        }
        if (postRequestDto.recruitmentGender() != null) {
            findPost.setRecruitmentGender(postRequestDto.recruitmentGender());
        }
        postRepository.save(findPost);
    }

    public void deletePost(Long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);
        postRepository.delete(optionalPost.get());
    }
    public List<PostResponse.allpostsresponseDto> allposts(MemberDetails memberDetails) {
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        List<Post> allPosts = postRepository.findAll();
        LocalDateTime todayDate = LocalDate.now().atStartOfDay();

        List<PostResponse.allpostsresponseDto> filteredPosts = allPosts.stream()
                .filter(post -> !post.getMeetTime().isBefore(todayDate) && (post.getRecruitmentGender().equals(member.getMemberGender()) || String.valueOf(post.getRecruitmentGender()).equals("ALL")))
                .map(post -> {
                    int currentNumberOfPeople = postApplicationRepository.countByPost_Id(post.getId());
                    return new PostResponse.allpostsresponseDto(
                            post.getId(),
                            post.getMeetTime(),
                            post.getExerciseName(),
                            post.getMeetPlace(),
                            post.getNumberOfPeople(),
                            currentNumberOfPeople, // 계산된 현재 인원 수를 여기에 추가
                            post.getRecruitmentGender()
                    );
                })
                .collect(Collectors.toList());

        return filteredPosts;
    }

    public List<PostResponse.mypostsresponseDto> myposts(MemberDetails memberDetails){
        List<Post> allPosts = postRepository.findAll();
        LocalDateTime todayDate = LocalDate.now().atStartOfDay();

        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        List<PostResponse.mypostsresponseDto> filteredPosts = allPosts.stream()
                .filter(post -> post.getMemberId().equals(member.getId()) && !post.getMeetTime().isBefore(todayDate))
                .map(post -> {
                    int currentNumberOfPeople = postApplicationRepository.countByPost_Id(post.getId());
                    return new PostResponse.mypostsresponseDto(
                            post.getId(),
                            post.getPostTitle(),
                            post.getMeetPlace(),
                            post.getExerciseName(),
                            post.getMeetTime(),
                            post.getNumberOfPeople(),
                            currentNumberOfPeople);
                })
                .collect(Collectors.toList());

        return filteredPosts;
    }

    public List<PostResponse.mypostsresponseDto> myapplicants(MemberDetails memberDetails){
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        List<PostApplication> applications = postApplicationRepository.findByMember_Id(member.getId());

        List<Long> postIds = applications.stream()
                .map(application -> application.getPost().getId())
                .collect(Collectors.toList());

        List<Post> allPosts = postRepository.findAllById(postIds);
        LocalDateTime todayDate = LocalDate.now().atStartOfDay();

        List<PostResponse.mypostsresponseDto> filteredPosts = allPosts.stream()
                .filter(post -> !post.getMeetTime().isBefore(todayDate))
                .map(post -> {
                    int currentNumberOfPeople = postApplicationRepository.countByPost_Id(post.getId());

                    return new PostResponse.mypostsresponseDto(
                            post.getId(),
                            post.getPostTitle(),
                            post.getMeetPlace(),
                            post.getExerciseName(),
                            post.getMeetTime(),
                            post.getNumberOfPeople(),
                            currentNumberOfPeople);
                })
                .collect(Collectors.toList());

        return filteredPosts;
    }
    public void applyToPost(Long postId, MemberDetails memberDetails) throws Exception {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        int status = postApplicationRepository.countByMember_IdAndPost_Id(member.getId(),postId);

        if (status == 0) {
            PostApplication postApplication = PostApplication.createPostApplication(member, optionalPost.get());
            optionalPost.get().getPostApplications().add(postApplication);
            postRepository.save(optionalPost.get());
        } else {
            throw new MyExceptionHandler(ALREADY_ENTER_ROOM);
        }
    }
    public PostResponse.membercount checkmembercount(Long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);
        int currentNumberOfPeople = postApplicationRepository.countByPost_Id(postId);
        PostResponse.membercount membercount = new PostResponse.membercount(optionalPost.get().getNumberOfPeople(), currentNumberOfPeople);
        return membercount;
    }


    public void deleteApplicant(Long postId, MemberDetails memberDetails) {
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        Optional<PostApplication> optionalPostApplication = postApplicationRepository.findByMember_IdAndPost_Id(member.getId(), postId);

        postApplicationRepository.delete(optionalPostApplication.get());
    }
}

