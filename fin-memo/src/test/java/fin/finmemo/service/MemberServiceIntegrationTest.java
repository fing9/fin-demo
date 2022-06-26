package fin.finmemo.service;

import fin.finmemo.domain.Member;
import fin.finmemo.repository.MemberRepository;
import fin.finmemo.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest //스프링을 이용한 DB연동 Test : 스프링 컨테이너와 테스트를 함께 실행한다.
@Transactional //테스트 케이스에 달면 시작할때 트랜잭션을 시작하고 각 테스트가 끝날때 DB에 반영하지않고(롤백) 지워주는 어노테이션 (반복가능한 테스트)
class MemberServiceIntegrationTest {

    //원래는 생성자로 받는걸 추천하지만 테스트는 마지막이기때문에 그냥 필드로 받는것도 괜찮다.
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given : 무엇이
        Member member = new Member();
        member.setName("spring");

        //when : 주어졌을 때
        Long saveId = memberService.join(member);

        //then : 어떤 결과가 나와야한다.
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given : 무엇이
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when : 주어졌을 때
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
/*
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/


        //then : 어떤 결과가 나와야한다.

    }

}