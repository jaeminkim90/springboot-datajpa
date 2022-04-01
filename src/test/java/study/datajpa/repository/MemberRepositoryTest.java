package study.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {


	@Autowired
	MemberRepository memberRepository;
	
	@Test
	void testMember() throws Exception{

		// proxy 객체를 반환한다. memberRepository에 데이터 JPA가 구현체를 만들어서 Injection한다
		System.out.println("memberRepository = " + memberRepository.getClass());

		// 저장
		Member member = new Member("memberA");
		Member savedmember = memberRepository.save(member);

		// 조회
		// 반환 값이 Optional
		Optional<Member> findMember1 = memberRepository.findById(savedmember.getId());

		// 호출 코드에서 get()을 이용하면, 바로 member 객체를 반환받을 수도 있다
		Member findMember2 = memberRepository.findById(savedmember.getId()).get();

		// 검증
		assertThat(findMember1.get().getId()).isEqualTo(member.getId());
		assertThat(findMember1.get().getUsername()).isEqualTo(member.getUsername());
		assertThat(findMember1.get()).isEqualTo(member);

		assertThat(findMember2.getId()).isEqualTo(member.getId());
		assertThat(findMember2.getUsername()).isEqualTo(member.getUsername());
		assertThat(findMember2).isEqualTo(member);


	}

}
