package com.sejong.project.pm.weight.Service;

import com.sejong.project.pm.global.exception.BaseException;
import com.sejong.project.pm.member.dto.MemberDetails;
import com.sejong.project.pm.member.model.Member;
import com.sejong.project.pm.member.repository.MemberRepository;
import com.sejong.project.pm.weight.Repository.WeightRepository;
import com.sejong.project.pm.weight.Weight;
import com.sejong.project.pm.weight.dto.WeightRequest;
import com.sejong.project.pm.weight.dto.WeightResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.sejong.project.pm.global.exception.codes.ErrorCode.MEMBER_NOT_FOUND;

@Service
public class WeightService {

    @Autowired
    WeightRepository weightRepository;
    @Autowired
    MemberRepository memberRepository;

    public void createweight(WeightRequest.weightRequestDto weightRequestDto, MemberDetails memberDetails) {
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        Weight newweight = new Weight(weightRequestDto, member);
        member.setMemberWeight(newweight.getMemberWeight());
        weightRepository.save(newweight);
        memberRepository.save(member);
    }

    public void rewriteweight(WeightRequest.weightRequestDto weightRequestDto,MemberDetails memberDetails, Long weightId) {
        Optional<Weight> optionalWeight = weightRepository.findById(weightId);
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));        Weight findWeight = optionalWeight.get();

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


    public WeightResponse.weightResponseDto dayweight(MemberDetails memberDetails, LocalDate today) {
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        Weight findweight = weightRepository.findWeightByMember_IdAndToday(member.getId(), today);
        WeightResponse.weightResponseDto weightResponseDto = new WeightResponse.weightResponseDto(member.getMemberName(), findweight.getMemberWeight(), findweight.getMemberBodyfat(),
                findweight.getMemberSkeletalmuscle(), findweight.getToday());
        return weightResponseDto;
    }

    public List<WeightResponse.MonthWeightDto> monthweight(MemberDetails memberDetails, LocalDate today) {
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        int currentMonth = today.getMonthValue();
        List<Weight> findWeights = weightRepository.findWeightsByCurrentMonthAndMember_Id(currentMonth, member.getId());

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

