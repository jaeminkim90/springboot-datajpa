package study.datajpa.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

public class MemberSpec {

	public static Specification<Member> teamName(final String teamName) {
		return (root, query, criteriaBuilder) -> {

			if(StringUtils.isEmpty(teamName)) {
				return null;
			}
			Join<Member, Team> t = root.join("team", JoinType.INNER);// 회원과 조인
			return criteriaBuilder.equal(t.get("name"), teamName);
		};
	}

	public static Specification<Member> username(final String username) {

		return(Specification<Member>)(root, query, criteriaBuilder) ->
			criteriaBuilder.equal(root.get("username"), username);
	}
}
