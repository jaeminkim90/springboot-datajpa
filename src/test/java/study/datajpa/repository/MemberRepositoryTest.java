package study.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
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

	@Test
	public void specBasic() {

		// given
		Team teamA = new Team("teamA");
		em.persist(teamA);

		Member m1 = new Member("m1", 0, teamA);
		Member m2 = new Member("m2", 0, teamA);
		em.persist(m1);
		em.persist(m2);

		em.flush();
		em.clear();

		// when
		Specification<Member> spec = MemberSpec.username("m1").and(MemberSpec.teamName("teamA"));
		List result = memberRepository.findAll(spec);
		assertThat(result.size()).isEqualTo(1);
	}

	@Test
	void queryByExample() throws Exception {
		// given
		Team teamA = new Team("teamA");
		em.persist(teamA);

		Member m1 = new Member("m1", 0, teamA);
		Member m2 = new Member("m2", 0, teamA);
		em.persist(m1);
		em.persist(m2);

		em.flush();
		em.clear();

		// when
		// Probe
		Member member = new Member("m1"); // Member 객체 자체가 검색 조거니 된다
		Team team = new Team("teamA");
		member.setTeam(team);

		// 특정 필드를 일치시키는 상세한 정보 제공, 재사용 가능
		ExampleMatcher matcher = ExampleMatcher.matching()
			.withIgnorePaths("age"); // age라는 속성은 무시하는 설정(기본적으로 null 이면 무시하지만 primitive 타입은 null이 없다)

		// Probe와 ExampleMatcher로 구성, 쿼리를 생성하는데 사용
		Example<Member> example = Example.of(member, matcher); // matcher 조건은 Example의 두 번째 파라미터로 넣을 수 있다

		List<Member> result = memberRepository.findAll(example);
		result.stream().forEach(m -> System.out.println("m = " + m));

		assertThat(result.get(0).getUsername()).isEqualTo("m1");
	}

	@Test
	public void projections() {
		// given
		Team teamA = new Team("teamA");
		em.persist(teamA);

		Member m1 = new Member("m1", 0, teamA);
		Member m2 = new Member("m2", 0, teamA);
		em.persist(m1);
		em.persist(m2);

		em.flush();
		em.clear();

		// when
		// 엔티티의 특정 필드만 조회하고 싶을 때는
		// UsernameOnly 인터페이스만 만들면 실제 구현체는 Spring Data JPA가 만든다
		List<UsernameOnlyDto> result = memberRepository.findProjectionsByUsername("m1", UsernameOnlyDto.class);

		for (UsernameOnlyDto usernameOnlyDto : result) {
			System.out.println("usernameOnly = " + usernameOnlyDto.getUsername());
		}

		// 중첩구조
		// 중첩구조에서는 첫번째 depth의 필드값은 정확하게 최적화가 된다.
		// 두번째 depth(중첩된 엔티티)는 엔티티 전체를 불러온다. 최적화가 안된다.
		// 조인은 left join을 사용한다
		List<NestedClosedProjections> result2 = memberRepository.findProjectionsByUsername("m1", NestedClosedProjections.class);

		for (NestedClosedProjections nestedClosedProjections : result2) {
			String username = nestedClosedProjections.getUsername();
			System.out.println("username = " + username);
			String teamName = nestedClosedProjections.getTeam().getName();
			System.out.println("teamName = " + teamName);
		}
	}

	@Test
	void nativeQuery() throws Exception {

		// given
		Team teamA = new Team("teamA");
		em.persist(teamA);

		Member m1 = new Member("m1", 0, teamA);
		Member m2 = new Member("m2", 0, teamA);
		em.persist(m1);
		em.persist(m2);

		em.flush();
		em.clear();

		// when
		Member result = memberRepository.findByNativeQuery("m1");
		System.out.println("result = " + result);

		// then
	}
}
