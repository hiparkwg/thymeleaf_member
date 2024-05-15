package kr.jobtc.member;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import kr.jobtc.member.mybatis.MyFactory;

@Component
public class MemberDao {
    SqlSession session;
    public MemberDao(){
        session = new MyFactory().getSession();
    }


    public List<MemberVo> search(String findStr){
        session = new MyFactory().getSession();
        List<MemberVo> list = session.selectList("member.search", findStr);
        session.close();
        return list;
    }

    public MemberVo view(String id){
        session = new MyFactory().getSession();
        MemberVo vo = session.selectOne("member.view", id);
        List<PhotoVo> photos = session.selectList("member.photos", id);
        vo.setPhotos(photos);
        session.close();
        return vo;
    }

    public String register(MemberVo vo){
        session = new MyFactory().getSession();
        int cnt = session.insert("member.register", vo);

        String msg = "";
        if(cnt>0){
            msg="정상 입력됨";
            session.commit();
        }else{
            msg="입력중 오류 발생";
            session.rollback();
        }
        session.close();

        return msg;
    }

    public String delete(String id){

        session = new MyFactory().getSession();

        int cnt = session.delete("member.delete_member", id);
        if(cnt>0){
            System.out.println("삭제 정상...");
            session.delete("member.delete_photos", id);
        }

        String msg = "";
        if(cnt>0){
            msg="삭제 됨";
            session.commit();
            
        }else{
            msg="삭제중 오류 발생";
            session.rollback();
        }
        System.out.println(msg);
        session.close();
        return msg;
    }

    
    

}
