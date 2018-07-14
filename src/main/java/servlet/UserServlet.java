package servlet;

import com.google.gson.Gson;
import dao.UserDao;
import javabean.PassedCard;
import javabean.User;
import javabean.WaitedCard;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "user",urlPatterns = "/user")
public class UserServlet extends BaseServlet{
    private static UserDao dao=new UserDao();
    private static Gson gson=new Gson();
    /**
     * 用户注册
     * @param req 传入用户name,password
     * @param resp 注册成功返回1，否则返回0
     * @throws Exception
     */
    public void register(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        String name=req.getParameter("name");
        String password=req.getParameter("password");
        User user=new User();
        user.setName(name);
        user.setPassword(password);
        if (dao.writeAccount(user)){
            req.getSession().setAttribute("user",user);
            resp.getWriter().write("1");
        }else {
            resp.getWriter().write("0");
        }
    }

    /**
     * 用户登录
     * @param req 传入用户name，password
     * @param resp 登录成功返回1，否则返回0
     * @throws Exception
     */
    public void login(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        String name=req.getParameter("name");
        String password=req.getParameter("password");
        User user=dao.readAccount(name);
        if (user.getPassword().equals(password)){
            req.getSession().setAttribute("user",user);
            resp.getWriter().write("1");
        }else {
            resp.getWriter().write("0");
        }
    }

    /**
     * 用户上传卡片到待审核库
     * @param req 传入卡片content,source,picture,theme
     * @param resp 上传成功返回1，否则返回0
     * @throws Exception
     */
    public void upload(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        String content=req.getParameter("content");
        String source=req.getParameter("source");
        String picture=req.getParameter("picture");
        String theme=req.getParameter("theme");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String time=sdf.format(new Date());
        String id=String.valueOf(content.hashCode());
        User user= (User) req.getSession().getAttribute("user");
        WaitedCard waitedCard=new WaitedCard();
        waitedCard.setId(id);
        waitedCard.setContent(content);
        waitedCard.setSource(source);
        waitedCard.setPicture(picture);
        waitedCard.setTheme(theme);
        waitedCard.setTime(time);
        waitedCard.setUserName(user.getName());
        if (dao.addWCard(waitedCard)){
            resp.getWriter().write("1");
        }else {
            resp.getWriter().write("0");
        }
    }

    /**
     * 查询某主题已审核卡片
     * @param req 传入theme
     * @param resp
     * @throws Exception
     */
    public void queryPassedCards(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        String theme=req.getParameter("theme");
        List<PassedCard> passedCards=dao.readPCards(theme);
        String json=gson.toJson(passedCards);
        resp.getWriter().write(json);
    }

    /**
     * 点赞
     * @param req 传入卡片id
     * @param resp 点赞成功返回1，否则返回0
     * @throws Exception
     */
    public void upVote(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        String id=req.getParameter("id");
        int num=dao.readPraise(id);
        num++;
        if (dao.changePraise(id,num)){
            resp.getWriter().write("1");
        }else {
            resp.getWriter().write("0");
        }
    }
}
