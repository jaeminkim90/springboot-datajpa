package study.datajpa.controller;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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
		// 컨버팅 기능스: 스프링이 파라미터로 들어온 id를 이용해서 아래 주석에 해당하는 member 조회 기능을 자동으로 수행한다.
		// Member member = memberRepository.findById(id).get();

		return member.getUsername();

	@PostConstruct
	public void init() {
		memberRepository.save(new Member("userA"));

	}
}
