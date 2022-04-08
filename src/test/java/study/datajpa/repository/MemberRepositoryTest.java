package study.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {


	@Autowired
	MemberRepository memberRepository;
	@Autowired
	TeamRepository teamRepository;

	@PersistenceContext
	EntityManager em;


	@Test
	void testMember() throws Exception {

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

	@Test
	void basicCRUD() throws Exception {

		Member member1 = new Member("member1");
		Member member2 = new Member("member2");
		memberRepository.save(member1);
		memberRepository.save(member2);

		// 단건 조회 검증
		Member findMember1 = memberRepository.findById(member1.getId()).get();
		Member findMember2 = memberRepository.findById(member2.getId()).get();
		assertThat(findMember1).isEqualTo(member1);
		assertThat(findMember2).isEqualTo(member2);

		// 리스트 조회 검증
		List<Member> members = memberRepository.findAll();
		long count = memberRepository.count();

		// 카운트 검증
		assertThat(members.size()).isEqualTo(count);
		assertThat(members.size()).isEqualTo(2);

		// 삭제 검증
		memberRepository.delete(member1);
		memberRepository.delete(member2);
		long deletedCount = memberRepository.count();
		assertThat(deletedCount).isEqualTo(0);
	}


	@Test
	public void findByUsernameAndAgeGreaterThenTest() {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("AAA", 20);
		memberRepository.save(m1);
		memberRepository.save(m2);

		// 인터페이스에 메서드만 만들어 놓으면, 메서드 생성 규칙에 의해 자동으로 메서드를 구현해 준다.
		List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);

		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.get(0).getAge()).isEqualTo(20);
	}

	@Test
	void findHelloBy() throws Exception {
		List<Member> helloBy = memberRepository.findTop2HelloBy();
	}


	@Test
	void namedQuery() throws Exception {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("BBB", 20);
		memberRepository.save(m1);
		memberRepository.save(m2);

		List<Member> result = memberRepository.findByUsername("AAA");
		Member findMember = result.get(0);
		assertThat(findMember).isEqualTo(m1);
	}

	@Test
	void testQuery() throws Exception {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("BBB", 20);
		memberRepository.save(m1);
		memberRepository.save(m2);

		List<Member> result = memberRepository.findUser("AAA", 10);
		assertThat(result.get(0)).isEqualTo(m1);
	}

	@Test
	void findUsernameList() throws Exception {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("BBB", 20);
		memberRepository.save(m1);
		memberRepository.save(m2);

		List<String> usernameList = memberRepository.findUsernameList();
		for (String username : usernameList) {
			System.out.println("username = " + username);
		}
	}

	@Test
	void findMemberDto() throws Exception {
		Team team = new Team("teamA");
		teamRepository.save(team); // team 세팅

		Member m1 = new Member("AAA", 10);
		m1.setTeam(team);
		memberRepository.save(m1); // member 세팅

		List<MemberDto> memberDto = memberRepository.findMemberDto();
		for (MemberDto dto : memberDto) {
			System.out.println("dto = " + dto);
			System.out.println("memberDto = " + memberDto);
		}
	}

	@Test
	void findByNames() throws Exception {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("BBB", 20);
		memberRepository.save(m1);
		memberRepository.save(m2);

		List<Member> memberList = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));

		for (Member member : memberList) {
			System.out.println("member = " + member);
		}
	}

	@Test
	void returnType() throws Exception {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("BBB", 20);
		memberRepository.save(m1);
		memberRepository.save(m2);

//		List<Member> aaa = memberRepository.findListByUsername("AAA");
//		System.out.println("aaa = " + aaa);
//
//		Member findMember = memberRepository.findMemberByUsername("AAA");
//		System.out.println("findMember = " + findMember);
//
//		Optional<Member> findOptionalMember = memberRepository.findOptionalByUsername("AAA");
//		System.out.println("findOptionalMember = " + findOptionalMember);
//		System.out.println("findOptionalMember.get() = " + findOptionalMember.get());

		// 단건 조회시 데이터가 없는 경우
		Member findNoMember = memberRepository.findMemberByUsername("asdf");
		System.out.println("findNoMember = " + findNoMember); // Spring Data JPA는 단건 조회시 데이터가 없을 경우 예외 발생 대신 null을 반환한다.

		// 단건 조회 시, 결과가 2개 이상일 경우 예외 발생
		Optional<Member> findMember = memberRepository.findOptionalByUsername("AAA");
		System.out.println("findMember = " + findMember);
	}

	@Test
	void paging() throws Exception {

		// given
		memberRepository.save(new Member("member1", 10));
		memberRepository.save(new Member("member2", 10));
		memberRepository.save(new Member("member3", 10));
		memberRepository.save(new Member("member4", 10));
		memberRepository.save(new Member("member5", 10));
		memberRepository.save(new Member("member6", 10));
		memberRepository.save(new Member("member7", 10));
		memberRepository.save(new Member("member8", 10));
		memberRepository.save(new Member("member9", 10));
		memberRepository.save(new Member("member99", 10));

		int age = 10;
		// PageRequest 객체를 만들고 조건을 세팅한다
		PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Direction.DESC, "username"));

		// when
		Page<Member> page = memberRepository.findByAge(age, pageRequest);

		// 엔티티를 DTO로 쉽게 변환하는 방법
		Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));

		// Page를 사용하면 반환되는 데이터에 total count가 기본으로 포함된다
		List<Member> content = page.getContent(); // getContent()를 이용해 데이터를 꺼낼 수 있다
		long totalElements = page.getTotalElements();// total count와 동일한 기능을 한다

		for (Member member : content) {
			System.out.println("member = " + member);
		}
		System.out.println("totalElements = " + totalElements);

		assertThat(content.size()).isEqualTo(3); // 페이지당 사이즈
		assertThat(page.getTotalElements()).isEqualTo(10); // 전체 수량
		assertThat(page.getNumber()).isEqualTo(0); // 페이지 번호
		assertThat(page.getTotalPages()).isEqualTo(4); // 전체 페이지 갯수
		assertThat(page.isFirst()).isTrue();
		assertThat(page.hasNext()).isTrue();
	}

	@Test
	void sliceTest() throws Exception {

		// given
		memberRepository.save(new Member("member1", 10));
		memberRepository.save(new Member("member2", 10));
		memberRepository.save(new Member("member3", 10));
		memberRepository.save(new Member("member4", 10));
		memberRepository.save(new Member("member5", 10));
		memberRepository.save(new Member("member6", 10));
		memberRepository.save(new Member("member7", 10));
		memberRepository.save(new Member("member8", 10));
		memberRepository.save(new Member("member9", 10));
		memberRepository.save(new Member("member99", 10));

		int age = 10;
		// PageRequest 객체를 만들고 조건을 세팅한다
		PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Direction.DESC, "username"));

		// when
		Slice<Member> slice = memberRepository.findByAge(age, pageRequest);

		// Page를 사용하면 반환되는 데이터에 total count가 기본으로 포함된다
		List<Member> content = slice.getContent(); // getContent()를 이용해 데이터를 꺼낼 수 있다

		assertThat(content.size()).isEqualTo(3); // 페이지당 사이즈
//		assertThat(slice.getTotalElements()).isEqualTo(10); // 전체 수량
		assertThat(slice.getNumber()).isEqualTo(0); // 페이지 번호
//		assertThat(slice.getTotalPages()).isEqualTo(4); // 전체 페이지 갯수
		assertThat(slice.isFirst()).isTrue();
		assertThat(slice.hasNext()).isTrue();

	}

	@Test
	void bulkUpdate() throws Exception {

		// given
		memberRepository.save(new Member("member1", 10));
		memberRepository.save(new Member("member2", 19));
		memberRepository.save(new Member("member3", 20));
		memberRepository.save(new Member("member4", 21));
		memberRepository.save(new Member("member5", 40));

		// when
		int resultCount = memberRepository.bulkAgePlus(20); // expect 3
		List<Member> dbMemberData = memberRepository.findAll();

		for (Member dbMemberDatum : dbMemberData) {
			System.out.println("dbMemberDatum = " + dbMemberDatum);

		}

		// 벌크 연산 후에는 영속성 컨텍스트를 모두 비워줘야 한다. 그래야 업데이트 처리된 DB 데이터를 조회할 수 있다

		//em.flush(); // 영속성 컨텍스트에 남아있는 내용을 모두 DB에 반영한다
		// clear()는 Repository에 @Modifying(clearAutomatically = true) 옵션을 적용해도 같은 기능을 수행한다.
		// em.clear(); // 영속성 컨텍스트에 담겨있는 모든 1차 캐시 데이터를 비운다.

		Member findMember = memberRepository.findById(3L).get();
		findMember.setAge(50);
		memberRepository.bulkAgePlus(20);
		// when
		assertThat(resultCount).isEqualTo(3);
		List<Member> members = memberRepository.findAll();

		for (Member member : members) {
			System.out.println("member.getAge() = " + member.getAge());
		}

		em.clear();
		List<Member> finalmembers = memberRepository.findAll();
		for (Member finalmember : finalmembers) {
			System.out.println("finalmember.getAge() = " + finalmember.getAge());
		}
	}

	@Test
	void findMemberLazy() throws Exception {
		// given
		// member1 -> teamA
		// member2 -> teamB

		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");
		teamRepository.save(teamA);
		teamRepository.save(teamB);
		Member member1 = new Member("member1", 10, teamA);
		Member member2 = new Member("member2", 10, teamB);
		memberRepository.save(member1);
		memberRepository.save(member2);

		em.flush();
		em.clear();

		// when
		List<Member> members = memberRepository.findEntityGraphByUsername("member1");

		for (Member member : members) {

			System.out.println("member = " + member.getUsername());
			System.out.println("member.team.name = " + member.getTeam().getName());
		}
	}

	@Test
	void queryHint() throws Exception {

		// given
		Member member1 = new Member("member1", 10);
		memberRepository.save(member1);
		em.flush();
		em.clear();

		// when
		Member findMember = memberRepository.findReadOnlyByUsername("member1"); // queryHints 적용
		findMember.setUsername("member2");

		em.flush();
	}

	@Test
	void lock() throws Exception {

		// given
		Member member1 = new Member("member1", 10);
		memberRepository.save(member1);
		em.flush();
		em.clear();

		// when
		List<Member> result = memberRepository.findLockByUsername("member1");
	}

	@Test
	void callCustom() throws Exception {
		List<Member> result = memberRepository.findMemberCustom();
	}
}
