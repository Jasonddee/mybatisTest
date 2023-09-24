package tw.idv.william.web.member.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.MutationQuery;
import org.springframework.stereotype.Repository;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import tw.idv.william.web.member.dao.MemberDao;
import tw.idv.william.web.member.entity.Member;

@Repository
public class MemberDaoImpl implements MemberDao {
	@PersistenceContext
	private Session session;

	@Override
	public int insert(Member member) {
		session.persist(member);
		return 1;
	}

	@Override
	public int deleteById(Integer id) {
		Member member = session.getReference(Member.class, id);
		session.remove(member);
		return 1;
	}

	@Override
	public int update(Member member) {
		final StringBuilder hql = new StringBuilder()
				.append("UPDATE Member SET ");
		final String password = member.getPassword();
		if (password != null && !password.isEmpty()) {
			hql.append("password = :password,");
		}
		final byte[] img = member.getImage();
		if (img != null && img.length != 0) {
			hql.append("image = :image,");
		}
		hql.append("nickname = :nickname,")
			.append("pass = :pass,")
			.append("roleId = :roleId,")
			.append("updater = :updater,")
			.append("lastUpdatedDate = NOW() ")
			.append("WHERE username = :username");
		MutationQuery query = session.createMutationQuery(hql.toString());
		if (password != null && !password.isEmpty()) {
			query.setParameter("password", member.getPassword());
		}
		if (img != null && img.length != 0) {
			query.setParameter("image", img);
		}
		return query.setParameter("nickname", member.getNickname())
				.setParameter("pass", member.getPass())
				.setParameter("roleId", member.getRoleId())
				.setParameter("updater", member.getUpdater())
				.setParameter("username", member.getUsername())
				.executeUpdate();
	}

	@Override
	public Member selectById(Integer id) {
		return session.get(Member.class, id);
	}

	@Override
	public List<Member> selectAll() {
		final String hql = "FROM Member ORDER BY id";
		return session
				.createQuery(hql, Member.class)
				.getResultList();
	}

	@Override
	public Member selectByUsername(String username) {
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> root = criteriaQuery.from(Member.class);
		criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));
		return session.createQuery(criteriaQuery).uniqueResult();
	}

	@Override
	public Member selectForLogin(String username, String password) {
		final String sql = "select * from MEMBER "
				+ "where USERNAME = :username and PASSWORD = :password";
		return session.createNativeQuery(sql, Member.class)
				.setParameter("username", username)
				.setParameter("password", password)
				.uniqueResult();
	}
}
