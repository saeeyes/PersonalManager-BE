package com.sejong.project.pm.food.service;

import com.sejong.project.pm.food.Food;
import com.sejong.project.pm.food.MemberFood;
import com.sejong.project.pm.food.dto.FoodRequest;
import com.sejong.project.pm.food.dto.FoodRequest.searchFoodDto;
import com.sejong.project.pm.food.dto.FoodRequest.AddFoodDTO;
import com.sejong.project.pm.food.dto.FoodResponse;
import com.sejong.project.pm.food.dto.FoodResponse.FoodDTO;
import com.sejong.project.pm.food.dto.FoodResponse.SearchFood;
import com.sejong.project.pm.food.repository.FoodRepository;
import com.sejong.project.pm.food.repository.MemberFoodRepository;
import com.sejong.project.pm.global.exception.BaseException;
import com.sejong.project.pm.global.handler.MyExceptionHandler;
import com.sejong.project.pm.member.dto.MemberDetails;
import com.sejong.project.pm.member.model.Member;
import com.sejong.project.pm.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.sejong.project.pm.global.exception.codes.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FoodServicelmpl implements FoodService{

    private final MemberRepository memberRepository;
    private final FoodRepository foodRepository;
    private final MemberFoodRepository memberFoodRepository;

    public FoodDTO searchFood(Long request){
        System.out.println("enter searchFood");
        Food food = foodRepository.findById(request).orElseThrow(() -> new BaseException(BAD_REQUEST));;

        FoodDTO foodDetail = new FoodDTO(
                food.getFoodName(),
                food.getFoodCalories(),
                food.getManufacturingCompany(),
                food.getProtein(),
                food.getCarbohydrate(),
                food.getFat()
        );

        return foodDetail;
    }

    public List<SearchFood> searchFoodList(searchFoodDto request){
        List<SearchFood> searchFoodList = searchAllFood();
        List<SearchFood> target = new ArrayList<>();
        for(SearchFood searchFood : searchFoodList){
            if(searchFood.foodname().contains(request.word())){
                target.add(searchFood);
            }
        }
        return target;
    }

    public FoodResponse.foodByDateDto eatingByDate(MemberDetails memberDetails, LocalDate request){

        Member member = memberRepository.findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        List<String> caloriesStr = Arrays.stream(member.getMemberCarprofat().split(":")).toList();//탄단지 :기준으로 나뉨 칼로리
        List<Integer> targetCalories = new ArrayList<>();
        List<Integer> nowCalories = new ArrayList<>();
        for(int i=0;i<3;i++) nowCalories.add(0);

        for(String s : caloriesStr) targetCalories.add(Integer.parseInt(s));

        List<String> morning = new ArrayList<>();
        List<String> lunch = new ArrayList<>();
        List<String> dinner = new ArrayList<>();
        List<String> snack = new ArrayList<>();
        List<FoodResponse.eatingFoodDTO> eatingFoodDTO = getEatingFoodByDate(memberDetails,request);

        Integer car=0,pro=0,fat=0;

        for(FoodResponse.eatingFoodDTO food: eatingFoodDTO){

            car += (int) (food.carbohydrate()*food.eatingAmoung()/100);
            pro +=  (int) (food.protein()*food.eatingAmoung()/100);
            fat += (int) (food.fat()*food.eatingAmoung()/100);

            if(food.foodTime().equals(MemberFood.FoodTime.MORNING)){
                morning.add(food.foodName());
            }
            if(food.foodTime().equals(MemberFood.FoodTime.LUNCH)){
                lunch.add(food.foodName());
            }
            if(food.foodTime().equals(MemberFood.FoodTime.DINNER)){
                dinner.add(food.foodName());
            }
            if(food.foodTime().equals(MemberFood.FoodTime.SNACK)){
                snack.add(food.foodName());
            }
        }

        nowCalories.set(0, car*4);
        nowCalories.set(1, pro*4);
        nowCalories.set(2, fat*8);

        FoodResponse.foodByDateDto foodBydateDto = new FoodResponse.foodByDateDto(
                targetCalories,
                nowCalories,
                morning,
                lunch,
                dinner,
                snack
        );
        return foodBydateDto;
    }


    public List<FoodResponse.eatingFoodDTO> getEatingFoodByDate(MemberDetails memberDetails, LocalDate date){

        List<MemberFood> eatingFood = getMemberFood(memberDetails);
        List<FoodResponse.eatingFoodDTO> foodByDate = new ArrayList<>();

        for(MemberFood memberFood : eatingFood){
            LocalDate memberLocalDate = memberFood.getCreatedAt().toLocalDate();
            if(memberLocalDate.isEqual(date)){
                foodByDate.add(
                        new FoodResponse.eatingFoodDTO(
                                memberFood.getFood().getFoodName(),
                                memberFood.getFood().getFoodCalories(),
                                memberFood.getFood().getManufacturingCompany(),
                                memberFood.getFood().getProtein(),
                                memberFood.getFood().getCarbohydrate(),
                                memberFood.getFood().getFat(),
                                memberFood.getFoodtime(),
                                memberFood.getEatingAmount(),
                                memberFood.getFood().getId()
                        )
                );
            }
        }
        return foodByDate;
    }

    public List<FoodDTO> getAllEatingFood(MemberDetails member){
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

    public List<SearchFood> searchAllFood(){
        List<Food> foodList = foodRepository.findAll();
        if(foodList.isEmpty() || foodList==null) throw new MyExceptionHandler(BAD_REQUEST_ERROR);

        List<SearchFood> searchFoods = new ArrayList<>();

        for(Food fd : foodList){
            //탄단지 추가는 조금 나중에 해야할듯

            searchFoods.add(new SearchFood(
                    fd.getFoodName(),
                    fd.getFoodCalories(),
                    fd.getManufacturingCompany(),
                    fd.getId()
            ));
        }
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

    public List<MemberFood> getMemberFood(MemberDetails memberDetails){
        Member member = memberRepository.findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        List<MemberFood> memberFoods = memberFoodRepository.findByMember(member);
        if(memberFoods.isEmpty() || memberFoods==null) throw new MyExceptionHandler(NOT_VALID_MEMBERFOOD);

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


    public List<FoodResponse.eatingFoodDTO> addEatingFood(MemberDetails memberDetails, FoodRequest.AddEatingFood request){

        Food food = getFood(request.foodId());

        FoodDTO response = new FoodDTO(
                food.getFoodName(),
                food.getFoodCalories(),
                food.getManufacturingCompany(),
                food.getProtein(),
                food.getCarbohydrate(),
                food.getFat()
        );

        double eatingAmount=request.eatingAmount();

        Member member = memberRepository.findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        MemberFood memberFood = new MemberFood(
            eatingAmount,
                (int)(food.getFoodCalories()*eatingAmount),
                food,
                member,
                request.foodTime()
        );

        memberFoodRepository.save(memberFood);

        return getEatingFoodByDate(memberDetails,LocalDate.now());
    }

    private Food getFood(Long foodId){
        Food food = foodRepository.findById(foodId).orElseThrow(() -> new BaseException(BAD_REQUEST));
        if(food==null) throw new MyExceptionHandler(BAD_REQUEST_ERROR);
        return food;
    }

    public List<FoodDTO> deleteEatingFood(MemberDetails member, FoodRequest.FoodIdDto request){
        MemberFood memberFood = memberFoodRepository.findById(request.foodId()).orElseThrow(() -> new BaseException(BAD_REQUEST));
        memberFoodRepository.delete(memberFood);
        return getEatingFoodToday(member);
    }

    public List<Integer> targetCalories(MemberDetails memberDetails){
        Member member = memberRepository.findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        List<String> caloriesStr = Arrays.stream(member.getMemberCarprofat().split(":")).toList();//탄단지 :기준으로 나뉨 칼로리
        List<Integer> targetCalories = new ArrayList<>();
        for(String s : caloriesStr) targetCalories.add(Integer.parseInt(s));

        return targetCalories;
    }

    public Integer todayEatingCalories(Member member){
        int calories = 0;
        List<MemberFood> memberFoods = memberFoodRepository.findByMember(member);
        List<MemberFood> todayMemberFoods = new ArrayList<>();

        for(MemberFood mf : memberFoods){
            if(mf.getCreatedAt().toLocalDate().equals(LocalDate.now())) todayMemberFoods.add(mf);
        }

        for(MemberFood mf : memberFoods){
            calories += mf.getEatingCalories();
        }
        return calories;
    }
}
