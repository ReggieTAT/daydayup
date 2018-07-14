package dao;

import javabean.Administer;
import javabean.PassedCard;
import javabean.WaitedCard;

import java.util.List;

public class AdministerDao extends BaseDao {
    /**
     * 读管理员账户
     * @param name
     * @return
     */
    public Administer readAccount(String name){

    }

    /**
     * 读已审核的卡片列表
     * @return
     */
    public List<PassedCard> readPCards(){

    }

    /**
     * 写入卡片到已审核库
     * @param passedCard
     * @return
     */
    public boolean addPCard(PassedCard passedCard){

    }

    /**
     * 从已审核库中删除卡片
     * @param id
     * @return
     */
    public boolean deletePCard(String id){

    }

    /**
     * 读待审核的卡片列表
     * @return
     */
    public List<WaitedCard> readWCards(){

    }

    /**
     * 读待审核的某张卡片
     * @return
     */
    public WaitedCard readWCard(String id){

    }

    /**
     * 删除待审核的某张卡片
     * @param id
     * @return
     */
    public boolean deleteWCard(String id){

    }
}
