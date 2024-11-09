package com.sejong.project.pm.food.service;

import com.sejong.project.pm.food.Food;
import com.sejong.project.pm.food.MemberFood;
import com.sejong.project.pm.food.dto.FoodRequest.searchFoodDto;
import com.sejong.project.pm.food.dto.FoodRequest.AddFoodDTO;
import com.sejong.project.pm.food.dto.FoodResponse.FoodDTO;
import com.sejong.project.pm.food.dto.FoodResponse.SearchFood;
import com.sejong.project.pm.food.repository.FoodRepository;
import com.sejong.project.pm.food.repository.MemberFoodRepository;
import com.sejong.project.pm.global.handler.MyExceptionHandler;
import com.sejong.project.pm.member.dto.MemberDetails;
import com.sejong.project.pm.member.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sejong.project.pm.global.exception.codes.ErrorCode.BAD_REQUEST_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
public class FoodServicelmpl implements FoodService{

    private FoodRepository foodRepository;
    private MemberFoodRepository memberFoodRepository;

    public List<SearchFood> searchFood(MemberDetails memberm, searchFoodDto searchFoodDto){
        List<Food> foodList = foodRepository.findByFoodName(searchFoodDto.foodname());
        if(foodList.isEmpty() || foodList==null) throw new MyExceptionHandler(BAD_REQUEST_ERROR);

        List<SearchFood> searchFoods = new ArrayList<>();

        for(Food fd : foodList){
            //탄단지 추가는 조금 나중에 해야할듯

            searchFoods.add(new SearchFood(
                    fd.getFoodName(),
                    fd.getFoodCalories(),
                    fd.getManufacturingCompany()
            ));
        }
        return searchFoods;
    }
    public List<SearchFood> searchAllFood(MemberDetails member){
        List<Food> foodList = foodRepository.findAll();
        if(foodList.isEmpty() || foodList==null) throw new MyExceptionHandler(BAD_REQUEST_ERROR);

        List<SearchFood> searchFoods = new ArrayList<>();

        for(Food fd : foodList){
            //탄단지 추가는 조금 나중에 해야할듯

            searchFoods.add(new SearchFood(
                    fd.getFoodName(),
                    fd.getFoodCalories(),
                    fd.getManufacturingCompany()
            ));
        }
        return searchFoods;
    }
    public List<SearchFood> getEatingFood(MemberDetails member){
        List<SearchFood> searchFoods = new ArrayList<>();
        List<MemberFood> memberFoods = getMemberFood(member);

        for(MemberFood mf : memberFoods) searchFoods.add(
                new SearchFood(
                        mf.getFood().getFoodName(),
                        mf.getFood().getFoodCalories(),
                        mf.getFood().getManufacturingCompany()
                )
        );

        return searchFoods;
    }

    public List<SearchFood> getEatingFoodToday(MemberDetails member){
        List<SearchFood> searchFoods = new ArrayList<>();
        List<MemberFood> memberFoods = getMemberFood(member);

        for(MemberFood mf : memberFoods){
            if(checkDate(mf.getCreatedAt())){
                searchFoods.add(
                        new SearchFood(
                                mf.getFood().getFoodName(),
                                mf.getFood().getFoodCalories(),
                                mf.getFood().getManufacturingCompany()
                        )
                );
            }
        }
        return searchFoods;
    }

    public boolean checkDate(LocalDateTime created){
        LocalDateTime now =  LocalDateTime.now();
        if(now.toLocalDate().isEqual(created.toLocalDate())) return true;
        return false;
    }

    public List<MemberFood> getMemberFood(MemberDetails member){
        List<MemberFood> memberFoods = memberFoodRepository.findByMember(member.getUsername());

        if(memberFoods.isEmpty() || memberFoods==null) throw new MyExceptionHandler(BAD_REQUEST_ERROR);

        return memberFoods;
    }

//우선순위 이거부터 해야함.


    public List<FoodDTO> addFood(MemberDetails member, AddFoodDTO request){
        List<FoodDTO> foods = new ArrayList<>();


        return foods;

    }


    public List<FoodDTO> addEatingFood(MemberDetails member, searchFoodDto request){
        List<FoodDTO> foods = new ArrayList<>();


        return foods;
    }


}
