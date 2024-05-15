package kr.jobtc.member;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import kr.jobtc.member.mybatis.MyFactory;

@Component
public class MemberDao {
    SqlSession session;
    public MemberDao(){
        session = MyFactory.getSession();
    }


    public List<MemberVo> search(String findStr){
        List<MemberVo> list = session.selectList("member.search", findStr);
        System.out.println("dao : " + list.size());
        return list;
    }

    public MemberVo view(String id){
        MemberVo vo = session.selectOne("member.view", id);
        List<PhotoVo> photos = session.selectList("member.photos", id);
        vo.setPhotos(photos);
        return vo;
    }
    

}
