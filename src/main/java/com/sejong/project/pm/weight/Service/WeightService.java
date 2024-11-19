package com.sejong.project.pm.weight.Service;

import com.sejong.project.pm.member.Member;
import com.sejong.project.pm.member.MemberRepository;
import com.sejong.project.pm.weight.Repository.WeightRepository;
import com.sejong.project.pm.weight.Weight;
import com.sejong.project.pm.weight.dto.WeightRequest;
import com.sejong.project.pm.weight.dto.WeightResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeightService {

    @Autowired
    WeightRepository weightRepository;
    @Autowired
    MemberRepository memberRepository;

    public void createweight(@RequestBody WeightRequest.weightRequestDto weightRequestDto) {
        Optional<Member> optionalMember = memberRepository.findById(weightRequestDto.memberId());
        Weight newweight = new Weight(weightRequestDto, optionalMember.get());
        optionalMember.get().setMemberWeight(newweight.getMemberWeight());
        weightRepository.save(newweight);
        memberRepository.save(optionalMember.get());
    }

    public void rewriteweight(WeightRequest.weightRequestDto weightRequestDto, Long weightId) {
        Optional<Weight> optionalWeight = weightRepository.findById(weightId);
        Member member = memberRepository.findById(weightRequestDto.memberId()).get();
        Weight findWeight = optionalWeight.get();

        if (weightRequestDto.memberWeight() >= 0.0) {
            findWeight.setMemberWeight(weightRequestDto.memberWeight());
            member.setMemberWeight(weightRequestDto.memberWeight());
            memberRepository.save(member);
        }
        if (weightRequestDto.memberBodyfat() >= 0.0) {
            findWeight.setMemberBodyfat(weightRequestDto.memberBodyfat());
        }
        if (weightRequestDto.memberSkeletalmuscle() >= 0.0) {
            findWeight.setMemberSkeletalmuscle(weightRequestDto.memberSkeletalmuscle());
        }

        weightRepository.save(findWeight);
    }


    public WeightResponse.weightResponseDto dayweight(WeightRequest.searchWeightRequestDto searchWeightRequestDto) {
        LocalDate time = searchWeightRequestDto.today();
        Long memeberId = searchWeightRequestDto.memberId();
        Weight findweight = weightRepository.findWeightByMember_IdAndToday(memeberId, time);
        WeightResponse.weightResponseDto weightResponseDto = new WeightResponse.weightResponseDto(findweight.getMemberWeight(), findweight.getMemberBodyfat(),
                findweight.getMemberSkeletalmuscle(), findweight.getToday());
        return weightResponseDto;
    }

    public List<WeightResponse.MonthWeightDto> monthweight(WeightRequest.searchWeightRequestDto searchWeightRequestDto) {
        int currentMonth = searchWeightRequestDto.today().getMonthValue();
        List<Weight> findWeights = weightRepository.findWeightsByCurrentMonthAndMember_Id(currentMonth, searchWeightRequestDto.memberId());

        List<WeightResponse.MonthWeightDto> filteredWeights = findWeights.stream()
                .map(weight -> {
                    return new WeightResponse.MonthWeightDto(
                            weight.getMemberWeight(),
                            weight.getToday()
                    );
                })
                .collect(Collectors.toList());
        return filteredWeights;
    }
}

