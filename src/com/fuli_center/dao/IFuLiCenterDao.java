package com.fuli_center.dao;

import java.util.ArrayList;
import java.util.List;

import com.fuli_center.bean.BoutiqueBean;
import com.fuli_center.bean.CartBean;
import com.fuli_center.bean.CategoryChildBean;
import com.fuli_center.bean.CategoryParentBean;
import com.fuli_center.bean.CollectBean;
import com.fuli_center.bean.ColorBean;
import com.fuli_center.bean.GoodDetailsBean;
import com.fuli_center.bean.NewGoodBean;
import com.fuli_center.bean.UserAvatar;
import com.fuli_center.pojo.User;

public interface IFuLiCenterDao {

	/**
	 * 向购物车添加数据
	 * @param cart
	 * @return
	 */
	public boolean addCart(CartBean cart);
	
	/**
	 * 删除购物车中指定的数据
	 * @param cartId:
	 * @return
	 */
	public boolean deleteCart(int cartId);
	
	/**
	 * 更新购物车
	 * @param cart
	 * @return
	 */
	public boolean updateCart(int cartId,int count);
	/**
	 * 更新购物车
	 * @param cart
	 * @return
	 */
	public boolean updateCart(int cartId,int count,boolean isChecked);
	
	/**
	 *从购物车下载数据
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
//	public CartBean[] findCarts(String userName/*,int pageId,int pageSize*/);
	public List<CartBean> findCarts(String userName/*,int pageId,int pageSize*/);
	
	/**
	 * 判断购物车中是否已存在某商品，存在则count+1，不存在则插入。
	 * @param userName
	 * @return
	 */
	public CartBean findCartExist(String userName,int goods_id);
	/**
	 * 从tb_boutique表中查询所有精选的列表数据
	 * @return
	 */
	BoutiqueBean[] findBoutiques();
	
	/**
	 * 从tb_boutique_good表中查询精选的商品信息
	 * @param catId:商品小类别id
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	NewGoodBean[] findNewAndBoutiqueGoods(int catId,int pageId,int pageSize);
	
	/** 查询分类中的大类型信息*/
	CategoryParentBean[] findCategoryParent();
	
	/**
	 * 查询小类别商品列表信息
	 * @param catId:小类别id
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	CategoryChildBean[] findCategoryChildren(int catId/*,int pageId,int pageSize*/);
	CategoryChildBean[] findCategoryChildren(int catId,int pageId,int pageSize);
	
	/**
	 * 下载分类列表中指定id的商品详情信息
	 * @param goodsId：商品id
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	GoodDetailsBean findGoodDetails(int goodsId);
	
	/**
	 * 下载catId指定的一组商品详情数据
	 * @param catId:分类的id
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	ArrayList<GoodDetailsBean> findGoodsDetails(int catId,int pageId,int pageSize);
	
	/**
	 * 下载指定类别的颜色数据
	 * @param catId：小类别id
	 * @return
	 */
	ColorBean[] findColorsByCatId(int catId);
	
	/**
	 * 添加收藏
	 * @param w
	 */
	int addCollect(CollectBean collect);
	
	/**
	 * 删除收藏
	 * @param userName
	 * @param goodsId
	 * @return
	 */
	boolean deleteCollect(String userName,int id);
	
	/**
	 * 下载指定用户的收藏
	 * @param userName：用户账号
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	List<CollectBean> findCollects(String userName,int pageId,int pageSize);

	/**
	 * 下载指定用户的收藏数量
	 * @param userName：用户账号
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	int findCollectCount(String userName);

	/**
	 * 获取指定用户是否收藏了指定商品
	 * @param userName
	 * @param goodsId
	 * @return
	 */
	boolean isCollect(String userName,int goodsId);
	
	
	// 2016年10月12日15:50:06
	/**
	 * 根据用户名查找用户是否存在
	 * @param mUserName
	 * @return
	 */
	User findUserByUsername(String mUserName);
	/**
	 * 添加用户和用户头像信息
	 * @param user
	 * @return
	 */
	boolean addUserAndAvatar(User user,String suffix);

	/**
	 * 根据用户名更新昵称
	 * @param username
	 * @param nick
	 * @return
	 */
	boolean updateNick(String username, String nick);

	/**
	 * 根据用户名查找用户和用户头像的信息
	 * @param username
	 * @return
	 */
	UserAvatar getUserAvatarByUsername(String username);
	
	/**
	 * 根据用户名更新密码
	 * @param username
	 * @param nick
	 * @return
	 */
	boolean updatePassword(String username, String password);

	/**
	 * 根据用户名和类型更新头像最后更新时间
	 * @param nameOrHxid
	 * @param avatarType
	 * @return
	 */
	boolean updateAvatar(String nameOrHxid, String avatarType,String suffix);
	/**
	 * 根据用户名删除用户
	 * @param userName
	 * @return
	 */
	public boolean deleteUser(String userName);
	/**
	 * 分页查询好友信息
	 * @param userName
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	List<UserAvatar> findContactPagesByUserName(String userName, String pageId, String pageSize);

	/**
	 * 查询好友全部信息
	 * @param userName
	 * @return
	 */
	List<UserAvatar> findContactAllByUserName(String userName);
	/**
	 * 根据用户名或密码模糊查询用户信息
	 * @param userName
	 * @param userNick
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	public List<UserAvatar> findUsersForSearch(String userName, String userNick, String pageId, String pageSize);
	/**
	 * 查找cname是否已经是name的好友
	 * @param name
	 * @param cname
	 * @return
	 */
	public boolean findContact(String name, String cname);
	/**
	 * 创建好友关系
	 * @param name
	 * @param cname
	 * @return
	 */
	public boolean addContact(String name, String cname);
	/**
	 * 删除好友关系
	 * @param name
	 * @param cname
	 * @return
	 */
	public boolean delContact(String name, String cname);
}
