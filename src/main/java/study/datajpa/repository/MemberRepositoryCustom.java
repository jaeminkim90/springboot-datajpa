package study.datajpa.repository;

import java.util.List;
import study.datajpa.entity.Member;

public interface MemberRepositoryCustom {

	// 구현은 다른 클래스에서 한다
	List<Member> findMemberCustom();



}
