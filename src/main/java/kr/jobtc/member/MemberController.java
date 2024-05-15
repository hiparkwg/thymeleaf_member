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
    
    @RequestMapping(path="/list")
    public ModelAndView list(){
        ModelAndView mv = new ModelAndView();
        List<MemberVo> list = dao.search("");
        mv.addObject("list", list);
        mv.setViewName("list");
        return mv;
    }



    @RequestMapping(path="/search")
    public ModelAndView search(String findStr){
        ModelAndView mv = new ModelAndView();
        List<MemberVo> list = dao.search(findStr);
        mv.addObject("list", list);
        mv.setViewName("list");
        return mv;
    }

    

    @RequestMapping(path="/register")
    public ModelAndView register(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("register");
        return mv;
    }
    @RequestMapping(path="/registerR")
    public ModelAndView registerR(MemberVo vo, String[] files){
        ModelAndView mv = new ModelAndView();
        System.out.println(vo.getId());
        System.out.println(vo.getName());
        for(String p : files){
            System.out.println(p);
        }
        mv = list();
        return mv;
    }


    @RequestMapping(path="/view")
    public ModelAndView view(String id){
        ModelAndView mv = new ModelAndView();
        MemberVo vo = dao.view(id);

        mv.addObject("vo", vo);
        mv.setViewName("view");
        return mv;
    }

    @RequestMapping(path="/modify")
    public ModelAndView modify(String id){
        ModelAndView mv = new ModelAndView();
        MemberVo vo = dao.view(id);

        mv.addObject("vo", vo);
        mv.setViewName("update");
        return mv;
    }


}
