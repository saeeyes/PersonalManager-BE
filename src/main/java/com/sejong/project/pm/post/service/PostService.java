package com.sejong.project.pm.post.service;

import com.sejong.project.pm.member.Member;
import com.sejong.project.pm.member.MemberRepository;
import com.sejong.project.pm.post.Post;
import com.sejong.project.pm.post.PostApplication;
import com.sejong.project.pm.post.dto.PostRequest;
import com.sejong.project.pm.post.dto.PostResponse;
import com.sejong.project.pm.post.repository.PostApplicationRepository;
import com.sejong.project.pm.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostApplicationRepository postApplicationRepository;
    public void createPost(PostRequest.postRequestDto postRequestDto){
        Post newpost = new Post(postRequestDto);
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
        Post findPost = optionalPost.get();

        postRepository.delete(findPost);
    }
    public List<PostResponse.allpostsresponseDto> allposts(String data) {
        List<Post> allPosts = postRepository.findAll();
        LocalDate today = LocalDate.now();

        List<PostResponse.allpostsresponseDto> filteredPosts = allPosts.stream()
                .filter(post -> !convertToLocalDateViaInstant(post.getMeetTime()).isBefore(today) && String.valueOf(post.getRecruitmentGender()).equals(data))
                .map(post -> {
                    int currentNumberOfPeople = postApplicationRepository.countByPostId(post);

                    return new PostResponse.allpostsresponseDto(
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
    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public List<PostResponse.mypostsresponseDto> myposts(Long memberId){
        List<Post> allPosts = postRepository.findAll();
        LocalDate today = LocalDate.now();

        List<PostResponse.mypostsresponseDto> filteredPosts = allPosts.stream()
                .filter(post -> post.getMemberId().equals(memberId) && !convertToLocalDateViaInstant(post.getMeetTime()).isBefore(today))
                .map(post -> {
                    System.out.println(post);
                    int currentNumberOfPeople = postApplicationRepository.countByPostId(post);
                    // PostResponse.allpostsrequestDto 객체 생성 시 계산된 값을 전달
                    return new PostResponse.mypostsresponseDto(
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

    public List<PostResponse.mypostsresponseDto> myapplicants(Long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        List<PostApplication> applications = postApplicationRepository.findByMemberId(optionalMember.get());

        List<Long> postIds = applications.stream()
                .map(application -> application.getPostId().getId())
                .collect(Collectors.toList());

        List<Post> allPosts = postRepository.findAllById(postIds);
        LocalDate today = LocalDate.now();

        List<PostResponse.mypostsresponseDto> filteredPosts = allPosts.stream()
                .filter(post -> !convertToLocalDateViaInstant(post.getMeetTime()).isBefore(today))
                .map(post -> {
                    System.out.println(post);
                    int currentNumberOfPeople = postApplicationRepository.countByPostId(post);
                    // Creating the PostResponse.mypostsresponseDto with calculated values
                    return new PostResponse.mypostsresponseDto(
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
    public void applyToPost(Long postId, Long memberId) throws Exception {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        int status = postApplicationRepository.countByMemberIdAndPostId(optionalMember.get(),optionalPost.get());
        System.out.println(status);

        if (status == 0) {
            PostApplication postApplication = new PostApplication(optionalPost.get(), optionalMember.get());
            postApplicationRepository.save(postApplication);
        } else {
            throw new Exception("Member has already applied to this post.");
        }
    }
    public PostResponse.membercount checkmembercount(Long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);
        int currentNumberOfPeople = postApplicationRepository.countByPostId(optionalPost.get());
        PostResponse.membercount membercount = new PostResponse.membercount(optionalPost.get().getNumberOfPeople(), currentNumberOfPeople);
        return membercount;
    }


}

