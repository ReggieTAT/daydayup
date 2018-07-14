package dao;

import javabean.PassedCard;
import javabean.User;
import javabean.WaitedCard;

import java.util.List;

public class UserDao extends BaseDao {
    /**
     * 写入新用户
     * @param user
     * @return
     */
    public boolean writeAccount(User user){

    }

    /**
     * 读用户信息
     * @param name
     * @return
     */
    public User readAccount(String name){

    }

    /**
     * 添加待审核卡片
     * @param waitedCard
     * @return
     */
    public boolean addWCard(WaitedCard waitedCard){

    }

    /**
     * 读某主题已审核的卡片列表
     * @return
     */
    public List<PassedCard> readPCards(String theme){

    }

    /**
     * 修改点赞数
     * @param id
     * @return
     */
    public boolean changePraise(String id,int num){

    }

    /**
     * 读点赞数
     * @param id
     * @return
     */
    public int readPraise(String id){

    }


}
