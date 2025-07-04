package com.sejong.project.pm.member.model;

import com.sejong.project.pm.exercise.MemberExercise;
import com.sejong.project.pm.eyebody.Eyebody;
import com.sejong.project.pm.food.MemberFood;
import com.sejong.project.pm.global.entity.BaseEntity;
import com.sejong.project.pm.member.dto.MemberRequest;
import com.sejong.project.pm.post.MemberPost;
import com.sejong.project.pm.weight.Weight;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberImage;

    private String memberName;

    private int memberAge;

    private String memberPassword;

    private String memberPhoneNum;

    private String memberEmail;

    private int memberCalories;

    private double memberWeight;
    private double memberHeight;

    private double memberTargetWeight;

    @Enumerated(EnumType.STRING)
    private DietType memberDietType;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private int memberTargetCalories;

    private String memberCarprofat;

    @Enumerated(EnumType.STRING)
    private Gender memberGender;

    @OneToMany(mappedBy = "member")
    private List<MemberExercise> memberExerciseList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberPost> memberPostList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Eyebody> eyebodyList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Weight> weightList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberFood> memberFoodList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberOAuth> memberOAuths = new ArrayList<>();

    private boolean isProfile=false;

    public void encodePassword(String encodedPassword){
        this.memberPassword = encodedPassword;
    }

    public enum DietType{
        DIET("DIET","3:5:2"),
        BULK("BULK","4:4:2");

        DietType(String type,String percent) {
            this.type=type;
            this.percent=percent;
        }

        private String type;
        private String percent;

        public String getPercent(){
            return this.percent;
        }
    }

    public enum Role{
        USER("USER"),
        ADMIN("ADMIN");

        Role(String role){}
        private String role;
    }

    public enum Gender{
        MALE("MALE"),
        FEMALE("FEMALE"),
        ALL("ALL");

        Gender(String gender){}
        private String gender;
    }

    @Builder
    private Member(String memberName, String memberPassword, String memberPhoneNum, String memberEmail) {
        this.memberName = memberName;
        this.memberPassword = memberPassword;
        this.memberPhoneNum = memberPhoneNum;
        this.memberEmail = memberEmail;
    }

    public static Member createMember(MemberRequest.MemberSignupRequestDto dto) {
            return Member.builder()
                    .memberPassword(dto.password())
                    .memberEmail(dto.email())
                    .memberPhoneNum(dto.phoneNumber())
                    .build();
    }

    public void addProfileSetting(String name,
                                  int memberAge,
                                  double memberHeight,
                                  double memberWeight,
                                  double memberTargetWeight,
                                  int memberTargetCalories,
                                  Member.DietType memberDietType,
                                  Gender memberGender,
                                  String memberCarprofat
    ) {
        this.memberName = name;
        this.memberAge = memberAge;
        this.memberHeight = memberHeight;
        this.memberWeight = memberWeight;
        this.memberTargetWeight = memberTargetWeight;
        this.memberTargetCalories = memberTargetCalories;
        this.memberDietType = memberDietType;
        this.memberGender = memberGender;
        this.memberCarprofat = memberCarprofat;
    }

}
