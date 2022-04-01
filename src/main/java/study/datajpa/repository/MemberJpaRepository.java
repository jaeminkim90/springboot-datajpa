package study.datajpa.repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

@Repository
public class MemberJpaRepository {

	@PersistenceContext // springboot를 이용하여 EntityManager를 삽입
	private EntityManager em;

	public Member save(Member member) {
		em.persist(member);
		return member;
	}

	public Member find(Long id) {
		return em.find(Member.class, id);
	}

}
