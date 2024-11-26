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
    private FoodRepository foodRepository;
    private MemberFoodRepository memberFoodRepository;

    public FoodDTO searchFood(FoodRequest.FoodIdDto request){

        Food food = foodRepository.findById(request.id()).orElseThrow(() -> new BaseException(BAD_REQUEST));;

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

    public FoodResponse.foodByDateDto eatingByDate(MemberDetails memberDetails, FoodRequest.DateDto request){
        Member member = memberRepository.findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        List<String> caloriesStr = Arrays.stream(member.getMemberCarprofat().split(":")).toList();//탄단지 :기준으로 나뉨 칼로리
        List<Integer> targetCalories = new ArrayList<>();
        List<Integer> nowCalories = new ArrayList<>();
        for(int i=0;i<3;i++) nowCalories.add(0);

        for(String s : caloriesStr) targetCalories.add(Integer.parseInt(s));

        List<SearchFood> morning = new ArrayList<>();
        List<SearchFood> lunch = new ArrayList<>();
        List<SearchFood> dinner = new ArrayList<>();
        List<FoodResponse.eatingFoodDTO> eatingFoodDTO = getEatingFoodByDate(memberDetails,request.date());

        for(FoodResponse.eatingFoodDTO food: eatingFoodDTO){

            SearchFood searchFoodDto = new SearchFood(
                    food.foodName(),
                    food.foodCalories(),
                    food.manufacturingCompany()
            );

            nowCalories.set(0, (int) (food.carbohydrate()*food.eatingAmoung()));
            nowCalories.set(1, (int) (food.protein()*food.eatingAmoung()));
            nowCalories.set(2, (int) (food.fat()*food.eatingAmoung()));

            if(food.foodTime() == MemberFood.FoodTime.MORNING){
                morning.add(searchFoodDto);
            }
            else if(food.foodTime() == MemberFood.FoodTime.LUNCH){
                lunch.add(searchFoodDto);
            }
            else if(food.foodTime() == MemberFood.FoodTime.DINNER){
                dinner.add(searchFoodDto);
            }

        }

        FoodResponse.foodByDateDto foodBydateDto = new FoodResponse.foodByDateDto(
                targetCalories,
                nowCalories,
                morning,
                lunch,
                dinner
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
                                memberFood.getEatingAmount()
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
                    fd.getManufacturingCompany()
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


    public FoodDTO addEatingFood(MemberDetails member, FoodRequest.AddEatingFood request){

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

    public List<FoodDTO> deleteEatingFood(MemberDetails member, FoodRequest.FoodIdDto request){
        MemberFood memberFood = memberFoodRepository.findById(request.id()).orElseThrow(() -> new BaseException(BAD_REQUEST));;
        memberFoodRepository.delete(memberFood);
        return getEatingFoodToday(member);
    }

}
