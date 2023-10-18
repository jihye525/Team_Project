package com.example.team_project.controller;

import com.example.team_project.dto.Response;
import com.example.team_project.dto.matching.GetMatchingMemberRist;
import com.example.team_project.dto.matching.MatchingDTO;
import com.example.team_project.dto.matching.MatchingMemberCreate;
import com.example.team_project.dto.matching.MatchingMemberResponse;
import com.example.team_project.entity.Category;
import com.example.team_project.entity.member.Member;
import com.example.team_project.entity.matching.Matching;
import com.example.team_project.security.CustomUserDetails;
import com.example.team_project.service.member.MemberService;
import com.example.team_project.service.matching.MatchingMemberService;
import com.example.team_project.service.matching.MatchingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MatchingController {
    private final MatchingService matchingService;
    private final MatchingMemberService matchingMemberService;
    private final MemberService memberService;

    @GetMapping("/matching")
    public String matching(Model model){
        List<Matching> matches = matchingService.findAll();
        model.addAttribute("categories",Category.values());
        model.addAttribute("matches", matches);
        return "main/matching";
    }

    @PostMapping("/matching")
    public String matching2(MatchingDTO matchingDTO){

        matchingService.createMatching(matchingDTO);
        matchingMemberService.createAndAddMember2Matching();
        return "redirect:/matching";
    }

    @PostMapping("/matchingMember")
    @ResponseBody
    public Response<Void> matchingMember(@RequestBody MatchingMemberCreate matchingMemberCreate, @AuthenticationPrincipal CustomUserDetails principal){
        Member member = memberService.getMember(principal.getMemberId());
        Matching matching = matchingService.getMatching(matchingMemberCreate.getMatchingId());
        matchingMemberService.matchingApply(member,matching,matchingMemberCreate.getIntroduce());

        return Response.success();
    }



//    @GetMapping("/matching/{Category}")
//    public String mat3(@PathVariable String Category){
//        return "redirect:/matching";
//    }
//
//    @GetMapping("/matching/{category}")
//    public String mat3(@PathVariable String category, Model model ,Category cate){
//        String title = cate.getTitle();
//        System.out.println(category);
//        model.addAttribute("matches",matchingService.findByCategory(Category category);
//
//        return "main/matching";
//    }

    @GetMapping("/matchingHistory")
    public String history(){
        return "main/matchingHistory";
    }

    @GetMapping("/matchingMemberList")
    public String matchingMemberList(){
        return "main/matchingMemberList";
    }
    @GetMapping("/matchingStatus")
    public String matchingStatus(Model model){
        List<MatchingMemberResponse> matching = matchingMemberService.getMatching();
        model.addAttribute("matchingList",matching);
        return "main/matchingStatus";
    }

//    @ResponseBody
//    @PostMapping("/matchingStatus")
//    public MatchingMemberResponse matchingAccpet(Long matchingUserId){
////        log.info(matchingMemberService.getMatchingMember(matchingUserId).toString() + "+++++ +++++" );
////        return matchingMemberService.getMatchingMember(matchingUserId);
//    }


    @GetMapping("/matchingApplyList")
    public String matchingApplyList() {
        return "main/matchingApplyList";

    }
}
