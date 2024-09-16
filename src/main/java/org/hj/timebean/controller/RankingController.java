package org.hj.timebean.controller;

import org.hj.timebean.auth.PrincipalDetails;
import org.hj.timebean.dto.MemberRankDTO;
import org.hj.timebean.entity.Ranking;
import org.hj.timebean.service.ranking.RankingDisplayService;
import org.hj.timebean.service.ranking.RankingSchedulerService;
import org.hj.timebean.service.ranking.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ranking")
public class RankingController {
    private final RankingService rankingService;
    private final RankingDisplayService rankingDisplayService;
    private final RankingSchedulerService rankingSchedulerService;

    @Autowired
    public RankingController(RankingService rankingService, RankingDisplayService rankingDisplayService, RankingSchedulerService rankingSchedulerService) {
        this.rankingService = rankingService;
        this.rankingDisplayService = rankingDisplayService;
        this.rankingSchedulerService = rankingSchedulerService;
    }

    @GetMapping("/")
    public String showTodayRanking(Model model, Authentication authentication) {
        long startTime = System.currentTimeMillis(); // 코드 시작 시간

        // 캐시에서 랭킹 데이터를 조회
        List<MemberRankDTO> rankings = rankingDisplayService.getCachedRankings();
        System.out.println("rankings:" + rankings);
        if (rankings == null || rankings.isEmpty()) {
            System.out.println("캐시가 없어서 db에서 조회 중");
            // 캐시 갱신
            rankings = rankingService.getAllRankingsWithRank();
        }

        model.addAttribute("memberRankList", rankings);
        // 테스트용
//        List<MemberRankDTO> list = rankingService.getAllRankingsWithRank();
//        System.out.println(list);
//        model.addAttribute("memberRankList", list);

        // 사용자의 세션 정보가 있을 경우, 사용자 랭킹 정보 조회
        if (authentication != null) {
            System.out.println("세션이 있음");
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            Long memberId = principalDetails.getMember().getId();

            if (principalDetails.getMember().getProfileImage() != null) {
                String base64 = Base64.getEncoder().encodeToString(principalDetails.getMember().getProfileImage());
                base64 = "data:image/png;base64," + base64;

                principalDetails.getMember().setProfileUrl(base64);
            }
            // 사용자 정보
            model.addAttribute("member", principalDetails.getMember());

            // 캐시에서 사용자 랭킹 조회
//            MemberRankDTO defaultRank = new MemberRankDTO(0L, memberId, principalDetails.getMember().getNickname(), 0, null, 0L);
//            MemberRankDTO memberRank = rankingService.getUserRank(memberId).orElseGet(() -> defaultRank);
            Optional<Ranking> myRankInfo = Optional.of(rankingService.findByMemberId(memberId).orElseGet(() ->
                    new Ranking(0L, principalDetails.getMember(), 0, null)));
            Optional<Integer> rank = rankingDisplayService.findMemberRankFromCache(memberId);

            // 나의 랭킹 정보
            if (myRankInfo == null || myRankInfo.isEmpty()) {
                model.addAttribute("myRankInfo", "정보를 찾을 수 없습니다.");
            } else {
                myRankInfo.ifPresent(myRankDetail -> model.addAttribute("myRankInfo", myRankDetail));

            }


            // 순위
            if (rank == null || rank.isEmpty()) {
                model.addAttribute("rank", "Top100에 들지 못하였습니다.");
            } else {
                rank.ifPresent(ranking -> model.addAttribute("rank", ranking + "등"));
            }
        }
        System.out.println("컨트롤러의 끝");
        long endTime = System.currentTimeMillis(); // 코드 끝난 시간
        long durationTimeSec = endTime - startTime;
        System.out.println(durationTimeSec + "m/s"); // 밀리세컨드 출력
        System.out.println((durationTimeSec / 1000) + "sec"); // 초 단위 변환 출력

        return "ranking/today_ranking";
    }
}
