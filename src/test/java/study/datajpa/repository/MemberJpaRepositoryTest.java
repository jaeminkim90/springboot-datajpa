package study.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;


@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

	@Autowired
	MemberJpaRepository memberJpaRepository;


	@Test
	void testmember() throws Exception {

		// 저장
		Member member = new Member("memberA");
		Member savedmember = memberJpaRepository.save(member);

		// 조회
		Member findMember = memberJpaRepository.find(savedmember.getId());

		// 검증
		assertThat(findMember.getId()).isEqualTo(member.getId());
		assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		assertThat(findMember).isEqualTo(member);
	}

	@Test
	void basicCRUD() throws Exception {

		Member member1 = new Member("member1");
		Member member2 = new Member("member2");
		memberJpaRepository.save(member1);
		memberJpaRepository.save(member2);

		// 단건 조회 검증
		Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
		Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
		assertThat(findMember1).isEqualTo(member1);
		assertThat(findMember2).isEqualTo(member2);

		// 리스트 조회 검증
		List<Member> members = memberJpaRepository.findAll();
		long count = memberJpaRepository.count();

		// 카운트 검증
		assertThat(members.size()).isEqualTo(count);
		assertThat(members.size()).isEqualTo(2);

		// 삭제 검증
		memberJpaRepository.delete(member1);
		memberJpaRepository.delete(member2);
		long deletedCount = memberJpaRepository.count();
		assertThat(deletedCount).isEqualTo(0);
	}
}
