package study.datajpa.repository;

public interface NestedClosedProjections {

	// 중첩구조에서는 첫번째 depth의 필드값은 정확하게 최적화가 된다.
	// 두번째 depth(중첩된 엔티티)는 엔티티 전체를 불러온다. 최적화가 안된다.
	// 조인은 left join을 사용한다
	String getUsername();
	TeamInfo getTeam();

	interface TeamInfo {
		String getName();
	}
}
