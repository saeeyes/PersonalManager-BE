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
import com.sejong.project.pm.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sejong.project.pm.global.exception.codes.ErrorCode.BAD_REQUEST_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
public class FoodServicelmpl implements FoodService{

    private final MemberRepository memberRepository;
    private FoodRepository foodRepository;
    private MemberFoodRepository memberFoodRepository;

    public List<SearchFood> searchFood(searchFoodDto searchFoodDto){
        Food foodList = foodRepository.findByFoodName(searchFoodDto.foodname());
        if(foodList==null) throw new MyExceptionHandler(BAD_REQUEST_ERROR);

        List<SearchFood> searchFoods = new ArrayList<>();

            searchFoods.add(new SearchFood(
                    foodList.getFoodName(),
                    foodList.getFoodCalories(),
                    foodList.getManufacturingCompany()
            ));

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
    public List<FoodDTO> getEatingFood(MemberDetails member){
        List<FoodDTO> searchFoods = new ArrayList<>();
        List<MemberFood> memberFoods = getMemberFood(member);

        for(MemberFood mf : memberFoods) searchFoods.add(
                new FoodDTO(
                        mf.getFood().getFoodName(),
                        mf.getFood().getFoodCalories(),
                        mf.getFood().getManufacturingCompany(),
                        mf.getFood().getProtein(),
                        mf.getFood().getCarbohydrate(),
                        mf.getFood().getFat()
                )
        );
        return searchFoods;
    }

    public List<FoodDTO> getEatingFoodToday(MemberDetails member){
        List<FoodDTO> searchFoods = new ArrayList<>();
        List<MemberFood> memberFoods = getMemberFood(member);

        for(MemberFood mf : memberFoods){
            if(checkDate(mf.getCreatedAt())){
                searchFoods.add(
                        new FoodDTO(
                                mf.getFood().getFoodName(),
                                mf.getFood().getFoodCalories(),
                                mf.getFood().getManufacturingCompany(),
                                mf.getFood().getProtein(),
                                mf.getFood().getCarbohydrate(),
                                mf.getFood().getFat()
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
        List<MemberFood> memberFoods = memberFoodRepository.findByMember(null);

        if(memberFoods.isEmpty() || memberFoods==null) throw new MyExceptionHandler(BAD_REQUEST_ERROR);

        return memberFoods;
    }

//우선순위 이거부터 해야함.


    public FoodDTO addFood(MemberDetails member, AddFoodDTO request){
        FoodDTO response = new FoodDTO(
                request.foodName(),
                request.foodCalories(),
                request.manufacturingCompany(),
                request.protein(),
                request.carbohydrate(),
                request.fat()
        );

        Food food = Food.builder()
                .foodName(request.foodName())
                .foodCalories(request.foodCalories())
                .manufacturingCompany(request.manufacturingCompany())
                .protein(request.protein())
                .carbohydrate(request.carbohydrate())
                .fat(request.fat())
                .build();

        foodRepository.save(food);

        return response;

    }


    public FoodDTO addEatingFood(MemberDetails member, searchFoodDto request){
        Food food = getFood(request.foodname());

        FoodDTO response = new FoodDTO(
                food.getFoodName(),
                food.getFoodCalories(),
                food.getManufacturingCompany(),
                food.getProtein(),
                food.getCarbohydrate(),
                food.getFat()
        );

        double eatingAmount=0;
        Optional<Member> mem = memberRepository.findMemberByMemberName(member.getUsername());

        if(!mem.isPresent()) throw new MyExceptionHandler(BAD_REQUEST_ERROR);
        Member tmp=mem.get();
        MemberFood memberFood = new MemberFood(
            eatingAmount,
                (int)(food.getFoodCalories()*eatingAmount),
                food,
                tmp
        );

        return response;
    }

    public Food getFood(String foodName){
        Food food = foodRepository.findByFoodName(foodName);
        if(food==null) throw new MyExceptionHandler(BAD_REQUEST_ERROR);
        return food;
    }

}
