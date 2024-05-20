package kr.jobtc.member;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MemberController {
    @Autowired
    MemberDao dao;

    static String uploadPath = "C:/myjob/member/src/main/resources/static/upload/";

    @RequestMapping(path = "/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping(path = "/search")
    public ModelAndView search(String findStr) {
        ModelAndView mv = new ModelAndView();
        List<MemberVo> list = dao.search(findStr);
        mv.addObject("list", list);
        mv.setViewName("list");
        return mv;
    }

    @RequestMapping(path = "/register")
    public ModelAndView register() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("register");
        return mv;
    }

    @RequestMapping(path = "/registerR")
    public ModelAndView registerR(
            @RequestParam("files") List<MultipartFile> photo,
            @ModelAttribute MemberVo vo) {
        ModelAndView mv = new ModelAndView();
        List<PhotoVo> photos = new ArrayList<>();

        if (photo != null ) {
            UUID uuid = null;
            String sysFile = "";
            for (MultipartFile f : photo) {
                if (f.getOriginalFilename().equals(""))
                    continue;

                // 파일 업로드
                uuid = UUID.randomUUID();
                sysFile = String.format("%s-%s", uuid, f.getOriginalFilename());
                File saveFile = new File(uploadPath+sysFile);
                try{
                    f.transferTo(saveFile);
                }catch(Exception ex){
                    ex.printStackTrace();
                }

                PhotoVo v = new PhotoVo();
                vo.setPhoto(sysFile);
                v.setPhoto(sysFile);
                v.setOriPhoto(f.getOriginalFilename());
                photos.add(v);
            }

            if (photos.size() > 0) {
                vo.setPhotos(photos);
            }

        }
        String msg = dao.register(vo);
        mv = search("");
        mv.addObject("msg", msg);
        return mv;
    }

    @RequestMapping(path = "/view")
    public ModelAndView view(String id) {
        ModelAndView mv = new ModelAndView();
        MemberVo vo = dao.view(id);

        mv.addObject("vo", vo);
        mv.setViewName("view");
        return mv;
    }

    @RequestMapping(path = "/changePhoto")
    public String changePhoto(String id, String photo){
        String msg = dao.changePhoto(id, photo);
        return msg;
    }

    @RequestMapping(path = "/modify")
    public ModelAndView modify(String id) {
        ModelAndView mv = new ModelAndView();
        MemberVo vo = dao.view(id);

        mv.addObject("vo", vo);
        mv.setViewName("update");
        return mv;
    }

    @RequestMapping(path = "/modifyR")
    public ModelAndView modifyR(
            @RequestParam("files") List<MultipartFile> photo,
            String[] delFiles,
            @ModelAttribute MemberVo vo) {
        ModelAndView mv = new ModelAndView();
        List<PhotoVo> photos = new ArrayList<>();

        UUID uuid=null;
        String sysFile = null;
        if (photo != null && photo.size() > 0) {

            for (MultipartFile f : photo) {
                if (f.getOriginalFilename().equals(""))
                    continue;
                PhotoVo v = new PhotoVo();
                uuid = UUID.randomUUID();
                sysFile = String.format("%s-%s", uuid, f.getOriginalFilename());

                File saveFile = new File(uploadPath+sysFile);
                try{
                    f.transferTo(saveFile);
                }catch(Exception ex){
                    ex.printStackTrace();
                }


                vo.setPhoto(sysFile);
                v.setPhoto(sysFile);
                v.setOriPhoto(f.getOriginalFilename());
                photos.add(v);
            }

            if (photos.size() > 0) {
                vo.setPhotos(photos);
            }

        }
        String msg = dao.modify(vo, delFiles);
        mv = search("");
        mv.addObject("msg", msg);
        return mv;
    }

    @RequestMapping(path = "/deleteR")
    public String deletrR(String id) {
        String msg = dao.delete(id);
        return msg;
    }
}
