package kr.jobtc.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MemberController {
    @Autowired
    MemberDao dao;


    @RequestMapping(path="/")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping(path="/search")
    public ModelAndView search(String findStr){
        System.out.println("controll : " + findStr);
        ModelAndView mv = new ModelAndView();
        List<MemberVo> list = dao.search(findStr);
        mv.addObject("list", list);
        mv.setViewName("index_items");
        return mv;
    }

    @RequestMapping(path="/view")
    public ModelAndView view(String id){
        System.out.println("controll : " + id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view");
        return mv;
    }


}
