package tw.idv.william.web.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tw.idv.william.web.member.entity.Member;

import java.util.List;
@Mapper
public interface MemberMapper {

    int insert(Member member) ;

    int deleteById(Integer id);

    int update(Member member);

    Member selectById(Integer id);

    List<Member> selectAll();

    Member selectByUsername(String username);

    Member selectForLogin(@Param("username") String username ,@Param("password") String password);
}
