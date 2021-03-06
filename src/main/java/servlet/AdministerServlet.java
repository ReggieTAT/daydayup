package servlet;

import com.google.gson.Gson;
import dao.AdministerDao;
import javabean.Administer;
import javabean.PassedCard;
import javabean.User;
import javabean.WaitedCard;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "admin",urlPatterns = "/admin")
public class AdministerServlet extends BaseServlet {
    private static AdministerDao dao=new AdministerDao();
    private static Gson gson=new Gson();
    /**
     * 管理员登录
     * @param req 传入name password参数
     * @param resp 验证通过返回admin对象的Json串，否则返回0
     * @throws Exception
     */
    public void login(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        String name=req.getParameter("name");
        String password=req.getParameter("password");
        Administer admin=dao.readAccount(name);
        if (admin.getPassword().equals(password)){
            req.getSession().setAttribute("admin",admin);
            String json=gson.toJson(admin);
            resp.getWriter().write(json);
        }else{
            resp.getWriter().write("0");
        }
    }

    /**
     * 查询全部已审核卡片
     * @param req
     * @param resp 返回已审核的卡片列表
     * @throws Exception
     */
    public void queryPassedCards(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        List<PassedCard> passedCards=dao.readPCards();
        String json=gson.toJson(passedCards);
        resp.getWriter().write(json);
    }

    /**
     * 查询某张已审核卡片的详情
     * @param req 传入已审核卡片的id
     * @param resp 返回已审核卡片对象的json
     * @throws Exception
     */
    public void queryPCardDetail(HttpServletRequest req,HttpServletResponse resp)throws Exception{
        String id=req.getParameter("id");
        PassedCard passedCard=dao.readPCard(id);
        String json=gson.toJson(passedCard);
        resp.getWriter().write(json);
    }

    /**
     * 直接添加卡片到已审核卡片库中
     * @param req 传入卡片content,source,picture,theme
     * @param resp 添加成功返回1，否则返回0
     * @throws Exception
     */
    public void addCard(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        String content=req.getParameter("content");
        String source=req.getParameter("source");
        String picture=req.getParameter("picture");
        String theme=req.getParameter("theme");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String time=sdf.format(new Date());
        String id=String.valueOf(content.hashCode());
        Administer admin= (Administer) req.getSession().getAttribute("admin");
        PassedCard passedCard=new PassedCard();
        passedCard.setId(id);
        passedCard.setContent(content);
        passedCard.setSource(source);
        passedCard.setPicture(picture);
        passedCard.setTheme(theme);
        passedCard.setTime(time);
        passedCard.setAdminName(admin.getName());
        passedCard.setUserName("admin");
        if (dao.addPCard(passedCard)){
            resp.getWriter().write("1");
        }else {
            resp.getWriter().write("0");
        }
    }

    /**
     * 删除已审核过的卡片
     * @param req 传入卡片id
     * @param resp 删除成功返回1，否则返回0
     * @throws Exception
     */
    public void deletePCard(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        String id=req.getParameter("id");
        if (dao.deletePCard(id)){
            resp.getWriter().write("1");
        }else {
            resp.getWriter().write("0");
        }
    }

    /**
     * 查询全部待审核内容
     * @param req
     * @param resp 返回待审核的卡片列表
     * @throws Exception
     */
    public void queryWaitedCards(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        List<WaitedCard> waitedCards=dao.readWCards();
        String json=gson.toJson(waitedCards);
        resp.getWriter().write(json);
    }

    /**
     * 查询某张待审核卡片的详情
     * @param req 传入要查询的待审核卡片id
     * @param resp 返回该卡片对象的json串
     * @throws Exception
     */
    public void queryWCardDetail(HttpServletRequest req,HttpServletResponse resp)throws Exception{
        String id=req.getParameter("id");
        WaitedCard waitedCard=dao.readWCard(id);
        String json=gson.toJson(waitedCard);
        resp.getWriter().write(json);
    }

    /**
     * 审核通过
     * @param req 传入待审核卡片id
     * @param resp 操作成功返回1，否则返回0
     * @throws Exception
     */
    public void isPassed(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        String id=req.getParameter("id");
        WaitedCard waitedCard=dao.readWCard(id);
        Administer admin= (Administer) req.getSession().getAttribute("admin");
        if (dao.deleteWCard(id)){
            PassedCard passedCard=new PassedCard();
            passedCard.setId(waitedCard.getId());
            passedCard.setContent(waitedCard.getContent());
            passedCard.setSource(waitedCard.getSource());
            passedCard.setPicture(waitedCard.getPicture());
            passedCard.setTheme(waitedCard.getTheme());
            passedCard.setTime(waitedCard.getTime());
            passedCard.setUserName(waitedCard.getUserName());
            passedCard.setAdminName(admin.getName());
            if (dao.addPCard(passedCard)){
                resp.getWriter().write("1");
            }else {
                resp.getWriter().write("0");
            }
        }else {
            resp.getWriter().write("0");
        }
    }

    /**
     * 审核不通过
     * @param req 传入待审核卡片id
     * @param resp 操作成功返回1，否则返回0
     * @throws Exception
     */
    public void notPassed(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        String id=req.getParameter("id");
        if (dao.deleteWCard(id)){
            resp.getWriter().write("1");
        }else {
            resp.getWriter().write("0");
        }
    }

    /**
     * 统计用户数目、待审核卡片数目、已审核卡片数目
     * @param req
     * @param resp 返回用户数目nums的json串,数组中数字顺序同上
     * @throws Exception
     */
    public void readNums(HttpServletRequest req,HttpServletResponse resp)throws Exception{
        int userNum=dao.readUserNum();
        int waitedCardNum=dao.readWCardNum();
        int passedCardNum=dao.readPCardNum();
        int[] nums={userNum,waitedCardNum,passedCardNum};
        String json=gson.toJson(nums);
        resp.getWriter().write(json);
    }

    /**
     * 读取全部用户信息
     * @param req
     * @param resp 返回用户列表的Json串
     * @throws Exception
     */
    public void readAllUsers(HttpServletRequest req,HttpServletResponse resp)throws Exception{
        List<User> users=dao.readUsers();
        String json=gson.toJson(users);
        resp.getWriter().write(json);
    }

    /**
     * 添加用户账户
     * @param req 传入用户name,password
     * @param resp 添加成功返回用户对象的json，否则返回0
     * @throws Exception
     */
    public void addUser(HttpServletRequest req,HttpServletResponse resp)throws Exception{
        String name=req.getParameter("name");
        String password=req.getParameter("password");
        User user=new User();
        user.setName(name);
        user.setPassword(password);
        if (dao.addUser(user)){
            String json=gson.toJson(user);
            resp.getWriter().write(json);
        }else {
            resp.getWriter().write("0");
        }
    }

    /**
     * 删除用户账户
     * @param req 传入用户name
     * @param resp 成功返回1，否则返回0
     * @throws Exception
     */
    public void deleteUser(HttpServletRequest req,HttpServletResponse resp)throws Exception{
        String name=req.getParameter("name");
        if (dao.deleteUser(name)){
            resp.getWriter().write("1");
        }else {
            resp.getWriter().write("0");
        }
    }

    /**
     * 查询用户信息
     * @param req 传入用户name
     * @param resp 返回用户对象的json，不存在则返回0
     * @throws Exception
     */
    public void queryUser(HttpServletRequest req,HttpServletResponse resp)throws Exception{
        String name=req.getParameter("name");
        User user=dao.readUser(name);
            String json=gson.toJson(user);
            resp.getWriter().write(json);

    }
}
