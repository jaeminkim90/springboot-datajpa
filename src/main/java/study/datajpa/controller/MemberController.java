package study.datajpa.controller;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberRepository memberRepository;

	@GetMapping("/members/{id}")
	public String findMember(@PathVariable("id") Long id) {
		Member member = memberRepository.findById(id).get();
		return member.getUsername();
	}

	@GetMapping("/members2/{id}")
	public String findMember(@PathVariable("id") Member member) {
		// 컨버터 기능: 스프링이 파라미터로 들어온 id를 이용해서 아래 주석에 해당하는 member 조회 기능을 자동으로 수행한다.
		// Member member = memberRepository.findById(id).get();

		return member.getUsername();
	}

	@GetMapping("/members") // @PageableDefault가 글로벌 설정보다 설정 우선권을 갖는다
	public Page<MemberDto> list(@PageableDefault(size = 5, sort = "username", direction = Sort.Direction.DESC)Pageable pageable) {
		// pagable 인터페이스만 넣어도 파라미터로 페이징 설정이 가능하다.
		// Page 객체의 map()을 이용하여 page 객체의 데이터 타입을 DTO 타입으로 변환
		return memberRepository.findAll(pageable)
			.map(MemberDto::new);

		// 인라인 처리 전
		// Page<Member> page = memberRepository.findAll(pageable);
		// Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));
 		// return map;
	}

	@PostConstruct // 스프링이 실행될 때 자동으로 실행된다
	public void init() {
		for (int i = 0; i < 100; i++) {
			memberRepository.save(new Member("user" + i, i));
		}
	}
}

