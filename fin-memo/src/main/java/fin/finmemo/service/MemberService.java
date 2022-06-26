package fin.finmemo.service;

import fin.finmemo.domain.Member;
import fin.finmemo.repository.MemberRepository;
import fin.finmemo.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service는 스프링에게 컨테이너에 MemberService클래스를 갖고 있으라는 말임
//@Service
@Transactional
public class MemberService {
    // 커멘드 + 쉬프트 + T -> 테스트파일 생성
    private final MemberRepository memberRepository;

    //@Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
         회원 가입
    */
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) { // 기능을 메서드로 따로 뽑는 단축키 : 컨트롤 + T -> Extract Method
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /*
    전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
