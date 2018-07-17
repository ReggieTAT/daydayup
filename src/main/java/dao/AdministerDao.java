package dao;

import javabean.Administer;
import javabean.PassedCard;
import javabean.User;
import javabean.WaitedCard;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdministerDao extends BaseDao {
    private static QueryRunner qr = new QueryRunner(BaseDao.getDataSource());
    /**
     * 读管理员账户
     * @param name
     * @return
     */
    public Administer readAccount(String name){
        Administer administer=null;
        String sql = "select * from Admin where Name=?";
        try {
            administer=qr.query(sql,new BeanHandler<Administer>(Administer.class),name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return administer;
    }

    /**
     * 读已审核的卡片列表
     * @return
     */
    public List<PassedCard> readPCards(){
        List<PassedCard> cardsList = new ArrayList<>();
        String sql="select * from PassedCard";
        try {
            cardsList=(List<PassedCard>) qr.query(sql,new BeanListHandler<PassedCard>(PassedCard.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardsList;
    }

    /**
     * 写入卡片到已审核库
     * @param passedCard
     * @return
     */
    public boolean addPCard(PassedCard passedCard){
        String sql = "insert into PassedCard values(?,?,?,?,?,?,?,?,?)";
        try {
            Object[] params = {passedCard.getId(),passedCard.getContent(),
                    passedCard.getSource(),passedCard.getPicture(),passedCard.getTheme(),
                    passedCard.getPraise(),passedCard.getTime(),passedCard.getAdminName(),passedCard.getUserName()};
            qr.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 从已审核库中删除卡片
     * @param id
     * @return
     */
    public boolean deletePCard(String id){
        String sql = "delete from PassedCard where Id=?";
        try {
            qr.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 读待审核的卡片列表
     * @return
     */
    public List<WaitedCard> readWCards(){
        List<WaitedCard> WCardsList = new ArrayList<>();
        String sql="select * from WaitedCard";
        try {
            WCardsList=(List<WaitedCard>) qr.query(sql,new BeanListHandler<WaitedCard>(WaitedCard.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return WCardsList;
    }

    /**
     * 读待审核的某张卡片
     * @return
     */
    public WaitedCard readWCard(String id){
        WaitedCard WCard=null;
        String sql = "select * from WaitedCard where Id=?";
        try {
            WCard=qr.query(sql,new BeanHandler<WaitedCard>(WaitedCard.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return WCard;
    }

    /**
     * 删除待审核的某张卡片
     * @param id
     * @return
     */
    public boolean deleteWCard(String id){
        String sql = "delete from WaitedCard where Id=?";
        try {
            qr.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 读已审核的某张卡片
     * @param id
     * @return
     */
    public PassedCard readPCard(String id){
        PassedCard PCard=null;
        String sql = "select * from PassedCard where Id=?";
        try {
            PCard=qr.query(sql,new BeanHandler<PassedCard>(PassedCard.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PCard;
    }

    /**
     * 查询用户数目
     * @return
     */
    public int readUserNum(){
        int num=0;
        long temp;
        String sql = "select count(Name) from User";
        try {
            temp=qr.query(sql,new ScalarHandler<Long>());
            num=(int)temp;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 查询已审核卡片数目
     * @return
     */
    public int readPCardNum(){
        int num=0;
        long temp;
        String sql = "select count(Id) from PassedCard";
        try {
            temp=qr.query(sql,new ScalarHandler<Long>());
            num=(int)temp;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 查询待审核卡片数目
     * @return
     */
    public int readWCardNum(){
        int num=0;
        long temp;
        String sql = "select count(Id) from WaitedCard";
        try {
            temp=qr.query(sql,new ScalarHandler<Long>());
            num=(int)temp;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 读用户列表
     * @return
     */
    public List<User> readUsers(){
        List<User> userList = new ArrayList<>();
        String sql="select * from User";
        try {
            userList=(List<User>) qr.query(sql,new BeanListHandler<User>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * 添加用户账户
     * @param user
     * @return
     */
    public boolean addUser(User user){
        String sql = "insert into User values(?,?)";
        try {
            Object[] params = {user.getName(),user.getPassword()};
            qr.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除用户账户
     * @param name
     * @return
     */
    public boolean deleteUser(String name){
        String sql = "delete from User where Name=?";
        try {
            qr.update(sql, name);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 查询某个用户的信息
     * @param name
     * @return 查到时返回用户对象，否则返回null
     */
    public User readUser(String name){
        User user=null;
        String sql = "select * from User where Name=?";
        try {
            user=qr.query(sql,new BeanHandler<User>(User.class),name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
