package fin.finmemo.repository;

import fin.finmemo.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em; //jpa를 쓰려면 엔티티메니저를 만들어야한다.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); //jpa가 인서트 쿼리 다 만들어서 집어넣고 알아서 다 해준다.
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); //find (조회할 타입, 식별자 pk값)
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) //jpql이라는 쿼리 언어, m은 멤버 엔티티객체 자체를 셀렉트하는것
                .getResultList();
    }
}
