package study.datajpa.repository;

import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom, JpaSpecificationExecutor { // 추가 상속


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


	@Modifying
	@Query("update Member m set m.age = m.age +1 where m.age >= :age")
	int bulkAgePlus(@Param("age") int age);

	@Query("select m from Member m left join fetch m.team") // fetch join을 할 때 member와 연관된 엔티티를 한 번에 조회
	List<Member> findMemberFetchJoin();


	//JpaRepository가 기본적으로 제공하는 메서드를 override하여 사용한다
	@Override
	@EntityGraph(attributePaths = ("team")) // Spring data JPA를 사용할 때 fetchJoin 사용에 제약이 있다면 @EntityGraph를 사용
	List<Member> findAll();


	// 직접 작성한 query에 fetchJoin만 추가하는 것도 가능하다
	@EntityGraph(attributePaths = {"team"})
	@Query("select m from Member m")
	List<Member> findMemberEntityGraph();

	@EntityGraph("Member.all") // JPA 표준에서 제공하는 @NamedEntityGraph를 이용하여 fetchJoin하는 방법
	List<Member> findEntityGraphByUsername(@Param("username") String username);


	// 쿼리힌트는 영속성 컨텍스트에 별도의 객체를 생성하지 않는다. 조회 전용으로 사용될 예정이기 때문에 비교를 위한 스냅샷을 만들지 않는다.
	@QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
	Member findReadOnlyByUsername(String username);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Member> findLockByUsername(String username);

	// 반환타입에 인터페이스를 넣는다. 메서드명은 자유, 반환 타입으로 인지한다
	<T> List<T> findProjectionsByUsername(@Param("username") String username, Class<T> type);
	// Generic type을 주면, 동적으로 프로젝션 데이터 번경 가능. type만 넘기면 사용할 수 있다


}

