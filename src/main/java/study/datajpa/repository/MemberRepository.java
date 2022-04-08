package study.datajpa.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {


	// 인터페이스에 메서드 이름만 만들어 놓으면 자동으로 메서드를 구현해준다.
	// 단점: 길어지면 불편하다
	List<Member> findByUsernameAndAgeGreaterThan(String username, int Age);

	List<Member> findTop2HelloBy();

	// Interface를 이용해 NamedQuery를 사용하는 방법
	//@Query(name = "Member.findByUsername") // @Query는 일정한 규칙으로 자동 탐색이 가능하다 => 타입.메서드명
	List<Member> findByUsername(@Param("username") String username);

	// 장점: 복잡한 JPQL을 Repository에서 바로 적용할 수 있다, 컴파일 시점에서 에러를 파악할 수 있다.
	@Query("select m from Member m where m.username = :username and m.age = :age")
	List<Member> findUser(@Param("username") String username, @Param("age") int age);

	@Query("select m.username from Member m")
	List<String> findUsernameList();

	@Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
	List<MemberDto> findMemberDto();

	// 여러개의 Parameter을 적용하여 쿼리 조회를 하고, 결과를 컬렉션으로 받을 수 있다.
	@Query("select m from Member m where m.username in :names")
	List<Member> findByNames(@Param("names") List<String> names);

	// Spring Data JPA는 반환타입을 다양하게 지원한다
	List<Member> findListByUsername(String username); // 컬렉션
	Member findMemberByUsername(String Username); // 단건
	Optional<Member> findOptionalByUsername(String Username); // 단건 Optional

	// 스프링 데이터 JPA를 이용한 페이징과 정렬
	// 인터페이스만으로도 페이징 기능이 포함된 쿼리를 작성할 수 있다
	@Query(value = "select m from Member m left join m.team t ",
		countQuery = "select count(m.username) from Member m") // count 쿼리를 분리하면 불필요한 join을 줄일 수 있다
	Page<Member> findByAge(int age, Pageable pageable);

//	Slice<Member> findByAge(int age, Pageable pageable);


	@Modifying(clearAutomatically = true) // update를 실행하는 Annotation: 일반 JPA의 executeUpdate() 역할을 한다. 뺴고 실행하면 에러
	@Query("update Member m set m.age = m.age +1 where m.age >= :age")
	int bulkAgePlus(@Param("age") int age);

	@Query("select m from Member m left join fetch m.team") // fetch join을 할 때 member와 연관된 엔티티를 한 번에 조회
	List<Member> findMemberFetchJoin();
}

