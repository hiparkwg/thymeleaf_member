package kr.jobtc.member.mybatis;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyFactory {
    public static SqlSession session;

    static{
       try{
        Reader reader = 
            Resources.getResourceAsReader("kr/jobtc/member/mybatis/config.xml");
        SqlSessionFactory factory = 
            new SqlSessionFactoryBuilder().build(reader);
        session = factory.openSession();            
       }catch(Exception ex){
        ex.printStackTrace();
       } 
    }
    public static SqlSession getSession(){
        return session;
    }
}
