package study.datajpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {


	// 인터페이스에 메서드 이름만 만들어 놓으면 자동으로 메서드를 구현해준다.
	List<Member> findByUsernameAndAgeGreaterThan(String username, int Age);

	List<Member> findTop2HelloBy();

	// Interface를 이용해 NamedQuery를 사용하는 방법
	//@Query(name = "Member.findByUsername") // @Query는 일정한 규칙으로 자동 탐색이 가능하다 => 타입.메서드명
	List<Member> findByUsername(@Param("username") String username);

}
