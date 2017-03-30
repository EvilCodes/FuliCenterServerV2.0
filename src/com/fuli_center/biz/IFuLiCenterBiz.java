package com.fuli_center.biz;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fuli_center.bean.BoutiqueBean;
import com.fuli_center.bean.CartBean;
import com.fuli_center.bean.CategoryChildBean;
import com.fuli_center.bean.CategoryParentBean;
import com.fuli_center.bean.CollectBean;
import com.fuli_center.bean.ColorBean;
import com.fuli_center.bean.GoodDetailsBean;
import com.fuli_center.bean.NewGoodBean;
import com.fuli_center.bean.Result;
import com.fuli_center.pojo.User;

public interface IFuLiCenterBiz {

	class Cart{
		public static final String TABLE_NAME="tb_cart";
		public static final String ID="id";
		public static final String GOODS_ID="goods_id";
		public static final String GOODS_THUMB="goodsThumb";
		public static final String USER_NAME="userName";
		/**购物车中该商品的件数*/
		public static final String COUNT="count";
		/**商品是否已被选中*/
		public static final String IS_CHECKED="isChecked";
	}
	
	class Good{
		public static final String TABLE_NAME="good";
		public static final String CURRENCY_PRICE="currencyPrice";
		public static final String ENGLISH_NAME="englishName";
		public static final String THUMB_URL="thumbUrl";
		public static final String GOODS_ID="goods_id";
	}
	class Boutique{
		public static final String TABLE_NAME="tb_boutique";
		public static final String ID="id";
		public static final String CAT_ID="catId";
		public static final String TITLE="title";
		public static final String DESCRIPTION="description";
		public static final String NAME="name";
		public static final String IMAGE_URL="imageurl";
	}
	
	class NewAndBoutiqueGood extends GoodDetails{
		public static final String TABLE_NAME="tb_new_boutique_good";
		public static final String CAT_ID="cat_id";
		/** 颜色id*/
		public static final String COLOR_ID="color_id";
		/** 颜色名*/
		public static final String COLOR_NAME="color_name";
		/** 颜色代码*/
		public static final String COLOR_CODE="color_code";
		/** 导购链接*/
		public static final String COLOR_URL="color_url";
	}
	
	class CategoryParent{
		public static final String TABLE_NAME="tb_category_parent";
		public static final String ID="id";
		public static final String NAME="name";
		public static final String IMAGE_URL="imageurl";
	}
	
	class CategoryChild extends CategoryParent{
		public static final String TABLE_NAME="tb_category_child";
		public static final String PARENT_ID="parent_id";
		public static final String CAT_ID="catId";
	}
	
	class GoodDetails{
		public static final String TABLE_NAME="tb_good_details";
		public static final String ID="id";
		/** 商品id*/
		public static final String GOODS_ID="goods_id";
		/** 所属类别的id*/
		public static final String CAT_ID="cat_id";
		/** 商品的中文名称*/
		public static final String GOODS_NAME="goods_name";
		/** 商品的英文名称*/
		public static final String GOODS_ENGLISH_NAME="goods_english_name";
		/** 商品简介*/
		public static final String GOODS_BRIEF="goods_brief";
		/** 商品原始价格*/
		public static final String SHOP_PRICE="shop_price";
		/** 商品的RMB价格 */
		public static final String CURRENT_PRICE="currency_price";
		/** 商品折扣价格 */
		public static final String PROMOTE_PRICE="promote_price";
		/** 人民币折扣价格*/
		public static final String RANK_PRICE="rank_price";
		/**是否折扣*/
		public static final String IS_PROMOTE="is_promote";
		/** 商品缩略图地址*/
		public static final String GOODS_THUMB="goods_thumb";
		/** 商品图片地址*/
		public static final String GOODS_IMG="goods_img";
		/** 分享地址*/
		public static final String ADD_TIME="add_time";
		/** 分享地址*/
		public static final String SHARE_URL="share_url";
	}
	
	class Property{
		public static final String TABLE_NAME="tb_property";
		public static final String ID="id";
		public static final String GOODS_ID="goods_id";
		public static final String COLOR_ID="colorid";
		public static final String COLOR_NAME="colorname";
		public static final String COLOR_CODE="colorcode";
		public static final String COLOR_IMG="colorimg";
		public static final String COLOR_URL="colorurl";
	}
	
	class Album{
		public static final String TABLE_NAME="tb_album";
		public static final String ID="id";
		public static final String PID="pid";
		public static final String IMG_ID="img_id";
		public static final String IMG_URL="img_url";
		public static final String THUMB_URL="thumb_url";
		public static final String IMG_DESC="img_desc";
	}

	class Color{
		public static final String TABLE_NAME="tb_color";
		public static final String COLOR_ID="colorid";
		public static final String CAT_ID="cat_id";
		public static final String COLOR_NAME="colorname";
		public static final String COLOR_CODE="colorcode";
		public static final String COLOR_IMG="colorimg";
	}
	
	class Collect{
		public static final String TABLE_NAME="tb_collect";
		/** 商品id*/
		public static final String ID="id";
		
		public static final String GOODS_ID="goods_id";
		
		public static final String USER_NAME="userName";
		
		/** 商品的中文名称*/
		public static final String GOODS_NAME="goodsName";
		/** 商品的英文名称*/
		public static final String GOODS_ENGLISH_NAME="goodsEnglishName";
		public static final String GOODS_THUMB="goodsThumb";
		public static final String GOODS_IMG="goodsImg";
		public static final String ADD_TIME="addTime";
		
		public static final String COUNT = "count";
	}
	
	public static final String AVATAR_PATH = "F:/0-Android/project/FuLiCenter/";
	
	/**
	 * 页号
	 */
	public static final String PAGE_ID="page_id";
	
	/**
	 * 每页加载的数据
	 */
	public static final String PAGE_SIZE="page_size";
	
	/**
	 * 添加商品至购物车
	 * @param cart
	 * @return
	 */
	public boolean addCart(CartBean cart);
	
	/**
	 * 删除购物车中指定id的商品
	 * @param cartId:所选商品的id
	 * @return
	 */
	public boolean deleteCart(int cartId);
	
	/**
	 * 修改购物车中的商品信息
	 * @param cart
	 * @return
	 */
	public boolean updateCart(int cartId,int count);

	/**
	 * 修改购物车中的商品信息
	 * @param cart
	 * @return
	 */
	public boolean updateCart(int cartId,int count,boolean isChecked);
	
	/**
	 * 查找购物车中的一组数据
	 * @param userName:用户账号
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
//	public CartBean[] findCarts(String userName/*,int pageId,int pageSize*/);
	public List<CartBean> findCarts(String userName/*,int pageId,int pageSize*/);
	
	/**
	 * 从tb_new_good表中查询所有的新品的商品详情数据
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	NewGoodBean[] findNewAndBoutiqueGoods(int catId,int pageId,int pageSize);
	
	/**
	 * 从tb_boutique表中查询所有精选的列表数据
	 * @return
	 */
	BoutiqueBean[] findBoutiques();
	
	/** 查询分类中的大类型信息*/
	CategoryParentBean[] findCategoryParent();
	
	/**
	 * 查询小类别商品列表信息
	 * @param catId:小类别id
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	CategoryChildBean[] findCategoryChildren(int parentId/*,int pageId,int pageSize*/);
	CategoryChildBean[] findCategoryChildren(int parentId,int pageId,int pageSize);
	
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
	
	/**
	 * 下载指定地址的图片
	 * @param response
	 * @param imgUrl
	 */
	public void downloadImg(HttpServletResponse response, String imgUrl);
	
	/**
	 * 注册
	 * @param user
	 * @param request
	 * @return
	 */
	Result register(User user, HttpServletRequest request);
	/**
	 * 解除注册，主要业务包括用户表和头像表中删除数据，并删除服务器本地用户图片
	 * @param userName
	 * @return
	 */
	Result unRegister(String userName);

	/**
	 * 登录
	 * @param user
	 * @return
	 */
	Result login(User user);
	/**
	 * 根据用户名更新昵称
	 * @param username
	 * @param nick
	 * @return
	 */
	Result updateNick(String username, String nick);
	/**
	 * 根据用户名更新密码
	 * @param username
	 * @param password
	 * @return
	 */
	Result updatePassword(String username, String password);
	
	/**
	 * 提供用户或群组的头像，供客户端下载
	 * @param nameOrHxid
	 * @param avatarType
	 * @param response
	 * @param width:图片的宽度
	 * @param height:图片的高度
	 */
	void downAvatar(String nameOrHxid,String avatarSuffix, String avatarType, HttpServletResponse response,String width,String height);
	/**
	 * 更新头像
	 * @param nameOrHxid
	 * @param avatarType
	 * @param response
	 * @return
	 */
	Result updateAvatar(String nameOrHxid, String avatarType, HttpServletRequest request);
	
	/**
	 * 分页下载好友信息
	 * @param userName
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	Result findContactPagesByUserName(String userName, String pageId, String pageSize);
	
	/**
	 * 下载还有全部信息
	 * @param userName
	 * @return
	 */
	Result findContactAllByUserName(String userName);
	
	/**
	 * 根据用户名查找用户
	 * @param userName
	 * @return
	 */
	Result findUserByUserName(String userName);
	/**
	 * 根据用户名或昵称模糊查询用户信息
	 * @param userName
	 * @param userNick
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	Result findUsersForSearch(String userName, String userNick, String pageId, String pageSize);
	
	/**
	 * 添加好友关系
	 * @param name
	 * @param cname
	 * @return
	 */
	Result addContact(String name, String cname);

	/**
	 * 解除好友关系
	 * @param name
	 * @param cname
	 * @return
	 */
	Result delContact(String name, String cname);
	
}
