package com.sejong.project.pm.food;

import com.sejong.project.pm.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "food")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Food extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String foodName;

    private int foodCalories;

    private String manufacturingCompany;

    private double protein;

    private double carbohydrate;

    private double fat;

    private double dietaryFiber;

    private double sodium;

    private double sugar;

    @OneToMany(mappedBy = "food")
    private List<MemberFood> memberFoodList = new ArrayList<>();
}
