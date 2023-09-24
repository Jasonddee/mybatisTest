package tw.idv.william.web.member.dao;

import tw.idv.william.core.dao.CoreDao;
import tw.idv.william.web.member.entity.Member;

public interface MemberDao extends CoreDao<Member, Integer> {
	
	Member selectByUsername(String username);
	
	Member selectForLogin(String username, String password);
}
