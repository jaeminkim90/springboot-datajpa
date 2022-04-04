package study.datajpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
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

}

