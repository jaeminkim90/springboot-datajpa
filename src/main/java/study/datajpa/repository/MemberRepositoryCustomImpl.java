package study.datajpa.repository;

import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

// MemberRepository를 이용해 custom의 findMemberCustom을 실행하면 Impl 클래스의 구현 메서드가 실행된다. 이 기능은 스프링데이터JPA가 지원한다.
// 특히 QueryDSL을 사용할 때 커스텀을 해서 많이 사용한다
// 사용자 정의 구현체의 이름은 스프링데이터 JPA가 인식해서 스프링 빈으로 등록할 때 사용한다. 'custom 인터페이스 이름 + Impl'로 맞춰줘야 한다
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

	// Entitymanager를 의존성주입하는 방법은 총 3가지가 있다
	// 1. @RequiredArgsConstructor를 클래스에 추가 -> fianl이 붙은 필드를 의존성 주입한다
	// 2. @PersistenceContext를 사용하여 의존성 추가
	// 3. 생성자가 하나만 있을 경우 @PersistenceContext를 생략해도 의존성 주입이 가능하다
	private final EntityManager em;

	// 생성자가 하나만 있으면 스프링이 자동으로 의존성 주입한다
//	public MemberRepositoryImpl(EntityManager em) {
//		this.em = em;
//	}

	@Override
	public List<Member> findMemberCustom() {
		return em.createQuery("select m from Member m")
			.getResultList();
	}
}
