package study.datajpa.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

	@PersistenceContext
	EntityManager em;

	@Test
	void testEntity() throws Exception{
		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");

		em.persist(teamA);
		em.persist(teamB);

		Member member1 = new Member("member1", 10 , teamA);
		Member member2 = new Member("member2", 20 , teamA);
		Member member3 = new Member("member3", 30 , teamB);
		Member member4 = new Member("member4", 40 , teamB);

		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
		em.persist(member4);

		// 초기화
		em.flush(); // 강제로 DB에 insert Query를 날린다
		em.clear(); // query를 날리고 영속성 컨텍스트에 있는 캐시를 모두 삭제한다

		// 확인
		// Member 테이블을 조회하고 Member 객체에 담아 List로 반환
		List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

		for (Member member : members) {
			System.out.println("member = " + member);
			System.out.println("-> member.team = " + member.getTeam());
		}
	}

}
