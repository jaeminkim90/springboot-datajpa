package study.datajpa.repository;


import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;


/**
 * 일반 JPA 방식의 Repository
 */
@Repository
public class MemberJpaRepository {


	@PersistenceContext // springboot를 이용하여 EntityManager를 삽입
	private EntityManager em;

	public Member save(Member member) {
		em.persist(member);
		return member;
	}

	public void delete(Member member) {
		em.remove(member); // DB에 remove query가 나가면서 삭제된다
	}

	// 전체 조회
	public List<Member> findAll() {
		// 전체 조회는 JPQL을 이용해 Query를 직접 작성해야 한다
		// JPQL은 객체를 대상으로 하는 쿼리다
		return em.createQuery("select m from Member m", Member.class).getResultList(); // List 반환
	}

	public Optional<Member> findById(Long id) {
		Member member = em.find(Member.class, id);
		return Optional.ofNullable(member);
	}

	// Member 갯수 조회
	public long count() {
		return em.createQuery("select count(m) from Member m", Long.class).getSingleResult(); // 단건 반환
	}

	// 단건 조회
	public Member find(Long id) {
		return em.find(Member.class, id);
	}

	public List<Member> findByUsernameAndAgeGreaterThen(String username, int age) {
		return em.createQuery("select m from Member m where m.username = :username and m.age > :age")
			.setParameter("username", username)
			.setParameter("age", age)
			.getResultList();
	}

	public List<Member> findByUsername(String username) {
		return em.createNamedQuery("Member.findByUsername", Member.class) // 지정해 놓은 이름을 이용해 쿼리를 불러오는 방식
			.setParameter("username", username) // Query에 변수로 들어가는 username 세팅
			.getResultList(); // 결과를 받는다
	}


	// 페이징에 필요한 컨텐츠를 offset과 limit로 자라서 가져오는 쿼리
	// offset: 페이징 시작 지점 설정, limit: 페이징 당 들어가는 데이터 수량 설정
	public List<Member> findByPage(int age, int offset, int limit) {
		return em.createQuery("select m from Member m where m.age = :age order by m.username desc")
			.setParameter("age", age)
			.setFirstResult(offset) // 어디서부터(몇번째부터) 가져올 건지 설정
			.setMaxResults(limit) // 페이징 한 번에 가져오는 갯수를 설정
			.getResultList();
	}

	// 전체 수량을 파악하는 쿼 리
	public long totalCount(int age) {
		return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
			.setParameter("age", age)
			.getSingleResult();
	}

	public int bulkAgePlus(int age) {
		// 응답 값의 갯수를 반환한다

		return em.createQuery(
				"update Member m set m.age = m.age +1 " +
					"where m.age >= :age")
			.setParameter("age", age)
			.executeUpdate();
	}
}
