package study.datajpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {


	// 인터페이스에 메서드 이름만 만들어 놓으면 자동으로 메서드를 구현해준다.
	List<Member> findByUsernameAndAgeGreaterThan(String username, int Age);

	List<Member> findTop2HelloBy();



}
