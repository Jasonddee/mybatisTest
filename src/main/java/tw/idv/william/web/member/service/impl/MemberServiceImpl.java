package tw.idv.william.web.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.idv.william.web.member.entity.Member;
import tw.idv.william.web.member.mapper.MemberMapper;
import tw.idv.william.web.member.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberMapper mapper;

	@Transactional
	@Override
	public Member register(Member member) {
		if (member.getUsername() == null) {
			member.setMessage("使用者名稱未輸入");
			member.setSuccessful(false);
			return member;
		}

		if (member.getPassword() == null) {
			member.setMessage("密碼未輸入");
			member.setSuccessful(false);
			return member;
		}

		if (member.getNickname() == null) {
			member.setMessage("暱稱未輸入");
			member.setSuccessful(false);
			return member;
		}

		if (mapper.selectByUsername(member.getUsername()) != null) {
			member.setMessage("使用者名稱重複");
			member.setSuccessful(false);
			return member;
		}

		member.setRoleId(2);
		final int resultCount = mapper.insert(member);
		if (resultCount < 1) {
			member.setMessage("註冊錯誤，請聯絡管理員!");
			member.setSuccessful(false);
			return member;
		}

		member.setMessage("註冊成功");
		member.setSuccessful(true);
		return member;
	}

	@Override
	public Member login(Member member) {
		final String username = member.getUsername();
		final String password = member.getPassword();

		if (username == null) {
			member.setMessage("使用者名稱未輸入");
			member.setSuccessful(false);
			return member;
		}

		if (password == null) {
			member.setMessage("密碼未輸入");
			member.setSuccessful(false);
			return member;
		}

		member = mapper.selectForLogin(username, password);
		if (member == null) {
			member = new Member();
			member.setMessage("使用者名稱或密碼錯誤");
			member.setSuccessful(false);
			return member;
		}

		member.setMessage("登入成功");
		member.setSuccessful(true);
		return member;
	}

	@Transactional
	@Override
	public Member edit(Member member) {
		final Member oMember = mapper.selectByUsername(member.getUsername());
		member.setPass(oMember.getPass());
		member.setRoleId(oMember.getRoleId());
		member.setUpdater(member.getUsername());
		final int resultCount = mapper.update(member);
		member.setSuccessful(resultCount > 0);
		member.setMessage(resultCount > 0 ? "修改成功" : "修改失敗");
		
		final String password = member.getPassword();
		if (password == null || password.isEmpty()) {
			member.setPassword(oMember.getPassword());
		}
		return member;
	}

	@Override
	public List<Member> findAll() {
		return mapper.selectAll();
	}

	@Transactional
	@Override
	public boolean remove(Integer id) {
		return mapper.deleteById(id) > 0;
	}

	@Transactional
	@Override
	public boolean save(Member member) {
		return mapper.update(member) > 0;
	}
}
