package com.sejong.project.pm.eyebody;

import com.sejong.project.pm.global.entity.BaseEntity;
import com.sejong.project.pm.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "eyebody")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Eyebody extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //url조금 생각해야할듯

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
