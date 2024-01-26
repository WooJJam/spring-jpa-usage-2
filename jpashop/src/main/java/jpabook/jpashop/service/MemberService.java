package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
// 조회일 경우 @Transactional(readOnly = true) 를 사용하면 최적화 됨
@RequiredArgsConstructor
// final이 있는 필드만 가지고 생성자를 만듦
public class MemberService {

    private final MemberRepository memberRepository;
    // 최신 버전 spring에서는 생성자가 하나일시 자동으로 Autowired 해줌
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        String findName = member.getName();
        List<Member> findMembers = memberRepository.findByName(findName);
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Member member) {
        Long findMemberId = member.getId();
        return memberRepository.findOne(findMemberId);
    }
}
