package kr.jobtc.member;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String changePhoto(String id, String photo){
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("photo", photo);
        session = new MyFactory().getSession();
        String msg = "";
        int cnt = session.update("member.change_photo", map);
        if(cnt>0){
            msg = "대표 이미지가 수정되었습니다.";
            session.commit();
        }else{
            msg = "대표 이미지 수정중 오류 발생";
            session.rollback();
        }

        session.close();
        return msg;
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
            //삭제할 파일명 가져오기
            List<PhotoVo> delPhotos = session.selectList("member.photos", id);
            if(delPhotos != null){
                for(PhotoVo v : delPhotos){
                    File delFile = new File(MemberController.uploadPath + v.getPhoto());
                    if(delFile.exists()) delFile.delete();
                }
            }

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

    public String modify(MemberVo vo, String[] delFiles){
        String msg="";
        session = new MyFactory().getSession();
        int cnt = session.update("member.update", vo);

        if(cnt>0){
            msg="정상 수정됨";
            if(delFiles != null){
                List<String> delList = new ArrayList<>(Arrays.asList(delFiles));
                session.delete("member.delete_files", delList);

                //파일 삭제
                for(String delPhoto : delFiles){
                    File delFile = new File(MemberController.uploadPath+ delPhoto);
                    if(delFile.exists()) delFile.delete();
                }
            }
            session.commit();
        }else{
            msg="수정중 오류 발생";
            session.rollback();
        }
        session.close();

        return msg;
    }
    
    

}
