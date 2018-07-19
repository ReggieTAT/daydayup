package dao;

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

public class UserDao extends BaseDao {
    private static QueryRunner qr = new QueryRunner(BaseDao.getDataSource());
    /**
     * 写入新用户
     * @param user
     * @return
     */
    public boolean writeAccount(User user){
        String sql = "insert into User values (?,?)";
        try {
            Object[] params = {user.getName(), user.getPassword()};
            qr.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 读用户信息
     * @param name
     * @return
     */
    public User readAccount(String name){
        User user=null;
        String sql = "select * from User where Name=?";
        try {
            user=qr.query(sql,new BeanHandler<User>(User.class),name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 添加待审核卡片
     * @param waitedCard
     * @return
     */
    public boolean addWCard(WaitedCard waitedCard){
        String sql = "insert into WaitedCard values(?,?,?,?,?,?,?)";
        try {
            Object[] params = {waitedCard.getId(),waitedCard.getContent(),waitedCard.getSource(),
                    waitedCard.getPicture(),waitedCard.getTheme(),waitedCard.getTime(),waitedCard.getUserName()};
            qr.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 读某主题已审核的卡片列表
     * @return
     */
    public List<PassedCard> readPCards(String theme){
        List<PassedCard> PCardsList = new ArrayList<>();
        String sql="select * from PassedCard where Theme=?";
        try {
            PCardsList=(List<PassedCard>) qr.query(sql,new BeanListHandler<PassedCard>(PassedCard.class),theme);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PCardsList;
    }

    /**
     * 修改点赞数
     * @param id
     * @return
     */
    public boolean changePraise(String id,int num){
        String sql = "UPDATE PassedCard SET Praise=? WHERE Id=?";
        try {
            Object[] params = {num,id};
            qr.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 读点赞数
     * @param id
     * @return
     */
    public int readPraise(String id){
        int num=0;
        String sql = "select Praise from PassedCard where Id=?";
        try {
            num=qr.query(sql,new ScalarHandler<Integer>(),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }


}
