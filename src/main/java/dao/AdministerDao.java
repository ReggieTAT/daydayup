package dao;

import javabean.Administer;
import javabean.PassedCard;
import javabean.User;
import javabean.WaitedCard;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

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

    }

    /**
     * 查询用户数目
     * @return
     */
    public int readUserNum(){

    }

    /**
     * 查询已审核卡片数目
     * @return
     */
    public int readPCardNum(){

    }

    /**
     * 查询待审核卡片数目
     * @return
     */
    public int readWCardNum(){

    }
}
