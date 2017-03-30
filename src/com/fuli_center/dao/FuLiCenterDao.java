package com.fuli_center.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fuli_center.bean.AlbumBean;
import com.fuli_center.bean.BoutiqueBean;
import com.fuli_center.bean.CartBean;
import com.fuli_center.bean.CategoryChildBean;
import com.fuli_center.bean.CategoryParentBean;
import com.fuli_center.bean.CollectBean;
import com.fuli_center.bean.ColorBean;
import com.fuli_center.bean.GoodDetailsBean;
import com.fuli_center.bean.NewGoodBean;
import com.fuli_center.bean.PropertyBean;
import com.fuli_center.bean.UserAvatar;
import com.fuli_center.biz.IFuLiCenterBiz;
import com.fuli_center.pojo.User;
import com.fuli_center.utils.DBUtils;
import com.fuli_center.utils.I;
import com.fuli_center.utils.Utils;

/**
 * 数据访问层
 * 
 * @author yao
 */
public class FuLiCenterDao implements IFuLiCenterDao {

	@Override
	public boolean addCart(CartBean cart) {
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "insert into " + IFuLiCenterBiz.Cart.TABLE_NAME + "(" + IFuLiCenterBiz.Cart.GOODS_ID + ","
				+ IFuLiCenterBiz.Cart.COUNT + "," + IFuLiCenterBiz.Cart.USER_NAME + "," + IFuLiCenterBiz.Cart.IS_CHECKED
				+ ")values(?,?,?,?)";
		int count = 0;
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, cart.getGoodsId());
			statement.setInt(2, cart.getCount());
			statement.setString(3, cart.getUserName());
			statement.setBoolean(4, cart.isChecked());
			count = statement.executeUpdate();
			return count == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(null, statement, connection);
		}
		/*if (count > 0) {
			int id = findCatLastId();
			System.out.println("dao.addCart.lastId=" + id);
			return id;
			return count;// 表示成功
		}*/
		return false;
	}

/*	private int findCatLastId() {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "select id from " + IFuLiCenterBiz.Cart.TABLE_NAME + " order by id desc limit 0,1";
		try {
			statement = connection.prepareStatement(sql);
			set = statement.executeQuery();
			if (set.next()) {
				int id = set.getInt(IFuLiCenterBiz.Collect.ID);
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}
		return 0;
	}
*/
	@Override
	public boolean deleteCart(int cartId) {
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "delete from " + IFuLiCenterBiz.Cart.TABLE_NAME + " where " + IFuLiCenterBiz.Cart.ID + "=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, cartId);
			int count = statement.executeUpdate();
			return count == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public boolean updateCart(int cartId, int count) {
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "update " + IFuLiCenterBiz.Cart.TABLE_NAME + " set " + IFuLiCenterBiz.Cart.COUNT + "=?" + " where "
				+ IFuLiCenterBiz.Cart.ID + "=?";
		try {
			System.out.println("updateCart--"+sql);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, count);
			statement.setInt(2, cartId);
			return statement.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public boolean updateCart(int cartId, int count, boolean isChecked) {
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "update " + IFuLiCenterBiz.Cart.TABLE_NAME + " set " + IFuLiCenterBiz.Cart.COUNT + "=?,"
				+ IFuLiCenterBiz.Cart.IS_CHECKED + "=?" + " where " + IFuLiCenterBiz.Cart.ID + "=?";
		System.out.println("sql=" + sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, count);
			statement.setInt(2, isChecked ? 1 : 0);
			statement.setInt(3, cartId);
			return statement.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(null, statement, connection);
		}
		return false;
	}
	
	@Override
	public CartBean findCartExist(String userName,int goods_id){
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "select * from " + IFuLiCenterBiz.Cart.TABLE_NAME
				+ " where " + IFuLiCenterBiz.Cart.USER_NAME + " = ? and "
				+ IFuLiCenterBiz.Cart.GOODS_ID + " = ?";
		CartBean cart = null;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			statement.setInt(2, goods_id);
			set = statement.executeQuery();
			while (set.next()) {
				cart = readCart(set);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}
		return cart;
	}

	@Override
	public List<CartBean> findCarts(String userName/*, int pageId, int pageSize*/) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "select * from " + IFuLiCenterBiz.Cart.TABLE_NAME
		// +" where "+IFuLiCenterBiz.Cart.USER_NAME+"=? limit ?,?";
				+ " where " + IFuLiCenterBiz.Cart.USER_NAME + "=?";
		List<CartBean> list = new ArrayList<CartBean>();
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
//			statement.setInt(2, (pageId - 1) * pageSize);
//			statement.setInt(3, pageSize);
			set = statement.executeQuery();
			while (set.next()) {
				CartBean cart = readCart(set);
				list.add(cart);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}

		return list;
	}

	private CartBean readCart(ResultSet set) throws SQLException {
		CartBean cart = new CartBean();
		cart.setId(set.getInt(IFuLiCenterBiz.Cart.ID));
		cart.setCount(set.getInt(IFuLiCenterBiz.Cart.COUNT));
		cart.setGoodsId(set.getInt(IFuLiCenterBiz.Cart.GOODS_ID));
		cart.setUserName(set.getString(IFuLiCenterBiz.Cart.USER_NAME));
		cart.setChecked(set.getInt(IFuLiCenterBiz.Cart.IS_CHECKED) == 1);
		return cart;
	}

	@Override
	public BoutiqueBean[] findBoutiques() {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "select * from " + IFuLiCenterBiz.Boutique.TABLE_NAME;
		try {
			statement = connection.prepareStatement(sql);
			set = statement.executeQuery();
			BoutiqueBean[] boutiques = new BoutiqueBean[0];
			while (set.next()) {
				BoutiqueBean boutique = new BoutiqueBean();
				boutique.setDescription(set.getString(IFuLiCenterBiz.Boutique.DESCRIPTION));
				boutique.setId(set.getInt(IFuLiCenterBiz.Boutique.ID));
				boutique.setImageurl(set.getString(IFuLiCenterBiz.Boutique.IMAGE_URL));
				boutique.setName(set.getString(IFuLiCenterBiz.Boutique.NAME));
				boutique.setTitle(set.getString(IFuLiCenterBiz.Boutique.TITLE));
				boutiques = Utils.add(boutiques, boutique);
			}
			return boutiques;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public NewGoodBean[] findNewAndBoutiqueGoods(int catId, int pageId, int pageSize) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "select * from " + IFuLiCenterBiz.NewAndBoutiqueGood.TABLE_NAME + " where "
				+ IFuLiCenterBiz.NewAndBoutiqueGood.CAT_ID + "=?" + " limit ?,?";
		System.out.println("findNewAndBoutiqueGoods.sql=" + sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, catId);
			statement.setInt(2, (pageId - 1) * pageSize);
			statement.setInt(3, pageSize);
			set = statement.executeQuery();
			NewGoodBean[] goods = new NewGoodBean[0];
			while (set.next()) {
				NewGoodBean good = new NewGoodBean();
				good.setAddTime(set.getLong(IFuLiCenterBiz.NewAndBoutiqueGood.ADD_TIME));
				good.setCatId(set.getInt(IFuLiCenterBiz.NewAndBoutiqueGood.CAT_ID));
				good.setColorCode(set.getString(IFuLiCenterBiz.Property.COLOR_CODE));
				good.setColorId(set.getInt(IFuLiCenterBiz.Property.COLOR_ID));
				good.setColorName(set.getString(IFuLiCenterBiz.Property.COLOR_NAME));
				good.setColorUrl(set.getString(IFuLiCenterBiz.Property.COLOR_URL));
				good.setCurrencyPrice(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.CURRENT_PRICE));
				good.setGoodsId(set.getInt(IFuLiCenterBiz.NewAndBoutiqueGood.GOODS_ID));
				good.setGoodsBrief(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.GOODS_BRIEF));
				good.setGoodsEnglishName(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.GOODS_ENGLISH_NAME));
				good.setGoodsImg(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.GOODS_IMG));
				good.setGoodsName(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.GOODS_NAME));
				good.setGoodsThumb(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.GOODS_THUMB));
				good.setId(set.getInt(IFuLiCenterBiz.NewAndBoutiqueGood.ID));
				good.setPromote(set.getInt(IFuLiCenterBiz.NewAndBoutiqueGood.IS_PROMOTE) == 1 ? true : false);
				good.setPromotePrice(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.PROMOTE_PRICE));
				good.setRankPrice(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.RANK_PRICE));
				good.setShopPrice(set.getString(IFuLiCenterBiz.NewAndBoutiqueGood.SHOP_PRICE));
				goods = Utils.add(goods, good);
			}
			return goods;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public CategoryParentBean[] findCategoryParent() {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "select * from " + IFuLiCenterBiz.CategoryParent.TABLE_NAME;
		try {
			statement = connection.prepareStatement(sql);
			set = statement.executeQuery();
			CategoryParentBean[] parents = new CategoryParentBean[0];
			while (set.next()) {
				CategoryParentBean parent = new CategoryParentBean();
				parent.setId(set.getInt(IFuLiCenterBiz.CategoryParent.ID));
				parent.setImageUrl(set.getString(IFuLiCenterBiz.CategoryParent.IMAGE_URL));
				parent.setName(set.getString(IFuLiCenterBiz.CategoryParent.NAME));
				parents = Utils.add(parents, parent);
			}
			return parents;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public CategoryChildBean[] findCategoryChildren(int parentId/*, int pageId, int pageSize*/) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "select * from " + IFuLiCenterBiz.CategoryChild.TABLE_NAME + " where "
//				+ IFuLiCenterBiz.CategoryChild.PARENT_ID + "=?" + " limit ?,?";
				+ IFuLiCenterBiz.CategoryChild.PARENT_ID + "=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, parentId);
//			statement.setInt(2, (pageId - 1) * pageSize);
//			statement.setInt(3, pageSize);
			set = statement.executeQuery();
			CategoryChildBean[] children = new CategoryChildBean[0];
			while (set.next()) {
				CategoryChildBean child = new CategoryChildBean();
				child.setId(set.getInt(IFuLiCenterBiz.CategoryChild.ID));
				child.setImageUrl(set.getString(IFuLiCenterBiz.CategoryChild.IMAGE_URL));
				child.setName(set.getString(IFuLiCenterBiz.CategoryChild.NAME));
				child.setParentId(set.getInt(IFuLiCenterBiz.CategoryChild.PARENT_ID));
				children = Utils.add(children, child);
			}
			return children;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public CategoryChildBean[] findCategoryChildren(int parentId, int pageId, int pageSize) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "select * from " + IFuLiCenterBiz.CategoryChild.TABLE_NAME + " where "
				+ IFuLiCenterBiz.CategoryChild.PARENT_ID + "=?" + " limit ?,?";
//				+ IFuLiCenterBiz.CategoryChild.PARENT_ID + "=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, parentId);
			statement.setInt(2, (pageId - 1) * pageSize);
			statement.setInt(3, pageSize);
			set = statement.executeQuery();
			CategoryChildBean[] children = new CategoryChildBean[0];
			while (set.next()) {
				CategoryChildBean child = new CategoryChildBean();
				child.setId(set.getInt(IFuLiCenterBiz.CategoryChild.ID));
				child.setImageUrl(set.getString(IFuLiCenterBiz.CategoryChild.IMAGE_URL));
				child.setName(set.getString(IFuLiCenterBiz.CategoryChild.NAME));
				child.setParentId(set.getInt(IFuLiCenterBiz.CategoryChild.PARENT_ID));
				children = Utils.add(children, child);
			}
			return children;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public GoodDetailsBean findGoodDetails(int goodsId) {
		GoodDetailsBean good = null;
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "select * from " + IFuLiCenterBiz.GoodDetails.TABLE_NAME + " where "
				+ IFuLiCenterBiz.GoodDetails.GOODS_ID + "=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, goodsId);
			set = statement.executeQuery();
			if (set.next()) {
				good = readGoodDetails(set);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}
		if (good == null) {
			return null;
		}
		PropertyBean[] properties = findProperties(good.getGoodsId());
		// ArrayList<PropertyBean> list = Utils.array2List(properties);
		if (properties != null) {
			good.setProperties(properties);
			for (PropertyBean p : properties) {
				AlbumBean[] albums = findAlbums(good.getGoodsId());
				if (albums != null) {
					// ArrayList<AlbumBean> albumList =
					// Utils.array2List(albums);
					p.setAlbums(albums);
				}
			}
		}
		return good;
	}

	private AlbumBean[] findAlbums(int goodsId) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "select * from " + IFuLiCenterBiz.Album.TABLE_NAME + " where " + IFuLiCenterBiz.Album.PID + "=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, goodsId);
			set = statement.executeQuery();
			AlbumBean[] albums = new AlbumBean[0];
			while (set.next()) {
				AlbumBean album = new AlbumBean();
				album.setPid(set.getInt(IFuLiCenterBiz.Album.PID));
				album.setImgId(set.getInt(IFuLiCenterBiz.Album.IMG_ID));
				album.setImgUrl(set.getString(IFuLiCenterBiz.Album.IMG_URL));
				album.setThumbUrl(set.getString(IFuLiCenterBiz.Album.THUMB_URL));
				albums = Utils.add(albums, album);
			}
			return albums;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	/**
	 * 查询指定商品id的属性
	 * 
	 * @param goodId
	 * @return
	 */
	private PropertyBean[] findProperties(int goodsId) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "select * from " + IFuLiCenterBiz.Property.TABLE_NAME + " where "
				+ IFuLiCenterBiz.Property.GOODS_ID + "=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, goodsId);
			set = statement.executeQuery();
			PropertyBean[] properties = new PropertyBean[0];
			while (set.next()) {
				PropertyBean p = new PropertyBean();
				p.setId(set.getInt(IFuLiCenterBiz.Property.ID));
				p.setColorCode(set.getString(IFuLiCenterBiz.Property.COLOR_CODE));
				p.setColorId(set.getInt(IFuLiCenterBiz.Property.COLOR_ID));
				p.setColorImg(set.getString(IFuLiCenterBiz.Property.COLOR_IMG));
				p.setColorName(set.getString(IFuLiCenterBiz.Property.COLOR_NAME));
				p.setColorUrl(set.getString(IFuLiCenterBiz.Property.COLOR_URL));
				properties = Utils.add(properties, p);
			}
			return properties;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public ColorBean[] findColorsByCatId(int catId) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "select * from " + IFuLiCenterBiz.Color.TABLE_NAME + " where " + IFuLiCenterBiz.Color.CAT_ID
				+ "=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, catId);
			set = statement.executeQuery();
			ColorBean[] colors = new ColorBean[0];
			while (set.next()) {
				ColorBean color = new ColorBean();
				color.setCatId(catId);
				color.setColorCode(set.getString(IFuLiCenterBiz.Color.COLOR_CODE));
				color.setColorId(set.getInt(IFuLiCenterBiz.Color.COLOR_ID));
				color.setColorImg(set.getString(IFuLiCenterBiz.Color.COLOR_IMG));
				color.setColorName(set.getString(IFuLiCenterBiz.Color.COLOR_NAME));
				colors = Utils.add(colors, color);
			}
			return colors;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public int addCollect(CollectBean collect) {
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "insert into " + IFuLiCenterBiz.Collect.TABLE_NAME + "(" + IFuLiCenterBiz.Collect.GOODS_ID + ","
				+ IFuLiCenterBiz.Collect.USER_NAME + ","
				// + IFuLiCenterBiz.Collect.GOODS_NAME + ","
				// + IFuLiCenterBiz.Collect.GOODS_ENGLISH_NAME+ ","
				// + IFuLiCenterBiz.Collect.GOODS_THUMB+ ","
				// + IFuLiCenterBiz.Collect.GOODS_IMG+ ","
				+ IFuLiCenterBiz.Collect.ADD_TIME
				// + ")values(?,?,?,?,?,?,?)";
				+ ")values(?,?,?)";
		int count = 0;
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, collect.getGoodsId());
			statement.setString(2, collect.getUserName());
			// statement.setString(3, collect.getGoodsName());
			// statement.setString(4, collect.getGoodsEnglishName());
			// statement.setString(5, collect.getGoodsThumb());
			// statement.setString(6, collect.getGoodsImg());
			statement.setLong(3, collect.getAddTime());
			count = statement.executeUpdate();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(null, statement, connection);
		}
		/*
		 * if(count==1){ int id=findCollecLastId(); return id; }
		 */
		return 0;
	}

	/**
	 * 查询刚插入衣橱的记录的id
	 * 
	 * @return
	 */
	private int findCollecLastId() {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "select id from " + IFuLiCenterBiz.Collect.TABLE_NAME + " order by id desc limit 0,1";
		try {
			statement = connection.prepareStatement(sql);
			set = statement.executeQuery();
			if (set.next()) {
				int id = set.getInt(IFuLiCenterBiz.Collect.ID);
				return id;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}
		return 0;
	}

	@Override
	public boolean deleteCollect(String userName, int id) {
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "delete from " + IFuLiCenterBiz.Collect.TABLE_NAME + " where " + IFuLiCenterBiz.Collect.GOODS_ID
				+ "=? and " + IFuLiCenterBiz.Collect.USER_NAME + "=?";
		// +I.User.USER_NAME+"=?";
		System.out.println("deleteCollect,sql=" + sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.setString(2, userName);
			int count = statement.executeUpdate();
			return count == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public List<CollectBean> findCollects(String userName, int pageId, int pageSize) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		// String sql = "select * from " + IFuLiCenterBiz.Collect.TABLE_NAME
		// + " where " + IFuLiCenterBiz.Collect.USER_NAME + "=? limit ?,?";
		String sql = "SELECT DISTINCT coll.id,tuser.m_user_name,good.goods_id,good.goods_name,good.goods_english_name,"
				+ "good.goods_thumb,good.goods_img,good.add_time " + "FROM	t_superwechat_user tuser,"
				+ "tb_good_details good,tb_collect coll " + "WHERE tuser.m_user_name = coll.userName "
				+ "AND good.goods_id = coll.goods_id " + "AND tuser.m_user_name =? limit ?,?";
		// CollectBean[] collects=new CollectBean[0];

		List<CollectBean> list = new ArrayList<CollectBean>();
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			statement.setInt(2, (pageId - 1) * pageSize);
			statement.setInt(3, pageSize);
			set = statement.executeQuery();
			while (set.next()) {
				CollectBean bean = new CollectBean();
				bean = readCollect(set);
				list.add(bean);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public int findCollectCount(String userName) {
		System.out.println("findCollectCount,userName=" + userName);
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "select count(*) as count from " + IFuLiCenterBiz.Collect.TABLE_NAME + " where "
				+ IFuLiCenterBiz.Collect.USER_NAME + "=?";
		try {
			// CollectBean w=new CollectBean();
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			set = statement.executeQuery();
			if (set.next()) {
				int count = set.getInt(IFuLiCenterBiz.Collect.COUNT);
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}
		return 0;
	}

	/**
	 * 读取一条收藏的商品的记录
	 * 
	 * @param set
	 * @return
	 */
	private CollectBean readCollect(ResultSet set) {
		CollectBean collect = new CollectBean();
		try {
			collect.setId(set.getInt(IFuLiCenterBiz.Collect.ID));
			collect.setAddTime(set.getLong(IFuLiCenterBiz.GoodDetails.ADD_TIME));
			collect.setGoodsEnglishName(set.getString(IFuLiCenterBiz.GoodDetails.GOODS_ENGLISH_NAME));
			collect.setGoodsId(set.getInt(IFuLiCenterBiz.GoodDetails.GOODS_ID));
			collect.setGoodsImg(set.getString(IFuLiCenterBiz.GoodDetails.GOODS_IMG));
			collect.setGoodsName(set.getString(IFuLiCenterBiz.GoodDetails.GOODS_NAME));
			collect.setGoodsThumb(set.getString(IFuLiCenterBiz.GoodDetails.GOODS_THUMB));
			collect.setUserName(set.getString(I.User.USER_NAME));
			return collect;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isCollect(String userName, int goodsId) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "select id from " + IFuLiCenterBiz.Collect.TABLE_NAME + " where "
				+ IFuLiCenterBiz.Collect.USER_NAME + "=? and " + IFuLiCenterBiz.Collect.GOODS_ID + "=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			statement.setInt(2, goodsId);
			set = statement.executeQuery();
			if (set.next()) {
				int id = set.getInt(IFuLiCenterBiz.Collect.ID);
				return id > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}
		return false;
	}

	@Override
	public ArrayList<GoodDetailsBean> findGoodsDetails(int catId, int pageId, int pageSize) {
		ArrayList<GoodDetailsBean> goodDetailsList = new ArrayList<GoodDetailsBean>();
		GoodDetailsBean good = null;
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = DBUtils.getConnection();
		String sql = "select * from " + IFuLiCenterBiz.GoodDetails.TABLE_NAME + " where "
				+ IFuLiCenterBiz.GoodDetails.CAT_ID + "=? limit ?,?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, catId);
			statement.setInt(2, (pageId - 1) * pageSize);
			statement.setInt(3, pageSize);
			set = statement.executeQuery();
			while (set.next()) {
				good = readGoodDetails(set);
				goodDetailsList.add(good);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(set, statement, connection);
		}
		if (goodDetailsList == null) {
			return null;
		}
		for (GoodDetailsBean goodDetails : goodDetailsList) {
			PropertyBean[] properties = findProperties(good.getGoodsId());
			// ArrayList<PropertyBean> list = Utils.array2List(properties);
			if (properties != null) {
				goodDetails.setProperties(properties);
				for (PropertyBean p : properties) {
					AlbumBean[] albums = findAlbums(good.getGoodsId());
					if (albums != null) {
						p.setAlbums(albums);
					}
				}
			}
		}
		return goodDetailsList;
	}

	private GoodDetailsBean readGoodDetails(ResultSet set) throws SQLException {
		GoodDetailsBean good;
		good = new GoodDetailsBean();
		good.setAddTime(set.getLong(IFuLiCenterBiz.GoodDetails.ADD_TIME));
		good.setCatId(set.getInt(IFuLiCenterBiz.GoodDetails.CAT_ID));
		good.setCurrencyPrice(set.getString(IFuLiCenterBiz.GoodDetails.CURRENT_PRICE));
		good.setGoodsId(set.getInt(IFuLiCenterBiz.GoodDetails.GOODS_ID));
		good.setGoodsBrief(set.getString(IFuLiCenterBiz.GoodDetails.GOODS_BRIEF));
		good.setGoodsEnglishName(set.getString(IFuLiCenterBiz.GoodDetails.GOODS_ENGLISH_NAME));
		good.setGoodsImg(set.getString(IFuLiCenterBiz.GoodDetails.GOODS_IMG));
		good.setGoodsName(set.getString(IFuLiCenterBiz.GoodDetails.GOODS_NAME));
		good.setGoodsThumb(set.getString(IFuLiCenterBiz.GoodDetails.GOODS_THUMB));
		good.setId(set.getInt(IFuLiCenterBiz.GoodDetails.ID));
		good.setPromote(set.getInt(IFuLiCenterBiz.GoodDetails.IS_PROMOTE) == 1 ? true : false);
		good.setPromotePrice(set.getString(IFuLiCenterBiz.GoodDetails.PROMOTE_PRICE));
		good.setRankPrice(set.getString(IFuLiCenterBiz.GoodDetails.RANK_PRICE));
		good.setShopPrice(set.getString(IFuLiCenterBiz.GoodDetails.SHOP_PRICE));
		good.setShareUrl(set.getString(IFuLiCenterBiz.GoodDetails.SHARE_URL));
		return good;
	}

	// 2016年10月12日15:54:03

	/**
	 * 根据用户名查找用户
	 */
	@Override
	public User findUserByUsername(String mUserName) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from " + I.User.TABLE_NAME + " where " + I.User.USER_NAME + " = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, mUserName);
			rs = ps.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setMUserName(rs.getString(I.User.USER_NAME));
				user.setMUserPassword(rs.getString(I.User.PASSWORD));
				user.setMUserNick(rs.getString(I.User.NICK));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(rs, ps, conn);
		}
		return null;
	}

	/**
	 * 插入数据到用户表 插入数据到头像表 事务操作!
	 */
	@Override
	public boolean addUserAndAvatar(User user, String suffix) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		try {
			conn.setAutoCommit(false);
			String sql = "insert into " + I.User.TABLE_NAME + " values (?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getMUserName());
			ps.setString(2, user.getMUserPassword());
			ps.setString(3, user.getMUserNick());
			ps.executeUpdate();
			ps.close();
			// insert into avatar(username,path,type,time) values
			sql = "insert into " + I.Avatar.TABLE_NAME + "(" + I.Avatar.USER_NAME + "," + I.Avatar.AVATAR_PATH + ","
					+ I.Avatar.AVATAR_SUFFIX + "," + I.Avatar.AVATAR_TYPE + "," + I.Avatar.UPDATE_TIME + ")"
					+ "values (?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getMUserName());
			ps.setString(2, I.AVATAR_TYPE_USER_PATH);
			ps.setString(3, suffix);
			ps.setInt(4, I.AVATAR_TYPE_USER);
			ps.setString(5, System.currentTimeMillis() + "");
			ps.executeUpdate();
			ps.close();
			conn.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.closeAll(null, ps, conn);
		}
		return false;
	}

	@Override
	public boolean updateNick(String username, String nick) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		try {
			String sql = "update " + I.User.TABLE_NAME + " set " + I.User.NICK + " = ? where " + I.User.USER_NAME
					+ " = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, nick);
			ps.setString(2, username);
			int count = ps.executeUpdate();
			return count == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(null, ps, conn);
		}
		return false;
	}

	@Override
	public UserAvatar getUserAvatarByUsername(String username) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from " + I.User.TABLE_NAME + "," + I.Avatar.TABLE_NAME + " where " + I.User.USER_NAME
					+ " = ?" + " and " + I.User.USER_NAME + "=" + I.Avatar.USER_NAME + " and " + I.Avatar.AVATAR_TYPE
					+ "=" + I.AVATAR_TYPE_USER;
			System.out.println("getUserAvatarByUsername:" + sql);
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				UserAvatar ua = new UserAvatar();
				initUserAvatar(rs, ua);
				return ua;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(rs, ps, conn);
		}
		return null;
	}

	@Override
	public boolean updatePassword(String username, String password) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		try {
			String sql = "update " + I.User.TABLE_NAME + " set " + I.User.PASSWORD + " = ? where " + I.User.USER_NAME
					+ " = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, password);
			ps.setString(2, username);
			int count = ps.executeUpdate();
			return count == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(null, ps, conn);
		}
		return false;
	}

	@Override
	public boolean updateAvatar(String nameOrHxid, String avatarType, String suffix) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		try {
			String sql = "update " + I.Avatar.TABLE_NAME + " set " + I.Avatar.UPDATE_TIME + " = ?,"
					+ I.Avatar.AVATAR_SUFFIX + " = ?" + " where " + I.Avatar.USER_NAME + " = ? and "
					+ I.Avatar.AVATAR_PATH + " = ? ";
			ps = conn.prepareStatement(sql);
			System.out.println("sql:" + sql);
			ps.setString(1, System.currentTimeMillis() + "");
			// 除了更新时间外，图片后缀名也需要更改，如将.png改为.jpg
			ps.setString(2, suffix);
			ps.setString(3, nameOrHxid);
			ps.setString(4, avatarType);
			int count = ps.executeUpdate();
			return count == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(null, ps, conn);
		}
		return false;
	}

	@Override
	public List<UserAvatar> findContactPagesByUserName(String userName, String pageId, String pageSize) {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = DBUtils.getConnection();
		String sql = "select * from " + I.Contact.TABLE_NAME + "," + I.User.TABLE_NAME + "," + I.Avatar.TABLE_NAME
				+ " where " + I.Contact.USER_NAME + "=?" + " and " + I.User.USER_NAME + "=" + I.Contact.CU_NAME + " "
				+ " and " + I.User.USER_NAME + "=" + I.Avatar.USER_NAME + " " + " limit ?,?";
		System.out.println("connection=" + conn + ",sql=" + sql);
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			Integer niPageId = Integer.parseInt(pageId);
			Integer niPageSize = Integer.parseInt(pageSize);
			// 0 5 (1-1)*5
			// 5 5 2
			// 10 5 3
			ps.setInt(2, (niPageId - 1) * niPageSize);
			ps.setInt(3, niPageSize);
			rs = ps.executeQuery();
			List<UserAvatar> listUserAvatar = new ArrayList<UserAvatar>();
			while (rs.next()) {
				UserAvatar ua = new UserAvatar();
				/*
				 * ua.setMUserName(rs.getString(I.User.USER_NAME));
				 * ua.setMUserNick(rs.getString(I.User.NICK));
				 * ua.setMAvatarId(rs.getInt(I.Avatar.AVATAR_ID));
				 * ua.setMAvatarType(rs.getInt(I.Avatar.AVATAR_TYPE));
				 * ua.setMAvatarPath(rs.getString(I.Avatar.AVATAR_PATH));
				 * ua.setMAvatarLastUpdateTime(rs.getString(I.Avatar.UPDATE_TIME
				 * ));
				 */
				initUserAvatar(rs, ua);
				listUserAvatar.add(ua);
			}
			return listUserAvatar;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(rs, ps, conn);
		}
		return null;
	}

	@Override
	public List<UserAvatar> findContactAllByUserName(String userName) {
		Connection conn = DBUtils.getConnection();
		String sql = "select * from " + I.Contact.TABLE_NAME + "," + I.User.TABLE_NAME + "," + I.Avatar.TABLE_NAME
				+ " where " + I.Contact.USER_NAME + "=?" + " and " + I.User.USER_NAME + "=" + I.Contact.CU_NAME + " "
				+ " and " + I.User.USER_NAME + "=" + I.Avatar.USER_NAME + " ";
		System.out.println("connection=" + conn + ",sql=" + sql);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			List<UserAvatar> listUserAvatar = new ArrayList<UserAvatar>();
			while (rs.next()) {
				UserAvatar ua = new UserAvatar();
				initUserAvatar(rs, ua);
				listUserAvatar.add(ua);
			}
			return listUserAvatar;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(rs, ps, conn);
		}
		return null;
	}

	@Override
	public List<UserAvatar> findUsersForSearch(String userName, String userNick, String pageId, String pageSize) {
		Connection conn = DBUtils.getConnection();
		String sql = "select * from " + I.User.TABLE_NAME + "," + I.Avatar.TABLE_NAME + " where " + I.User.USER_NAME
				+ "=" + I.Avatar.USER_NAME;
		if (userName != null) {
			sql += " and " + I.User.USER_NAME + " like ?";
		}
		if (userNick != null) {
			sql += " and " + I.User.NICK + " like ?";
		}
		sql += "limit ?,?";
		System.out.println("connection=" + conn + ",sql=" + sql);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			if (userName != null) {
				ps.setString(1, "%" + userName + "%");
				if (userNick != null) {
					ps.setString(2, "%" + userNick + "%");
					ps.setInt(3, (Integer.parseInt(pageId) - 1) * Integer.parseInt(pageSize));
					ps.setInt(4, Integer.parseInt(pageSize));
				} else {
					ps.setInt(2, (Integer.parseInt(pageId) - 1) * Integer.parseInt(pageSize));
					ps.setInt(3, Integer.parseInt(pageSize));
				}
			} else {
				if (userNick != null) {
					ps.setString(1, "%" + userNick + "%");
					ps.setInt(2, (Integer.parseInt(pageId) - 1) * Integer.parseInt(pageSize));
					ps.setInt(3, Integer.parseInt(pageSize));
				} else {
					ps.setInt(1, (Integer.parseInt(pageId) - 1) * Integer.parseInt(pageSize));
					ps.setInt(2, Integer.parseInt(pageSize));
				}
			}
			rs = ps.executeQuery();
			List<UserAvatar> uaList = new ArrayList<UserAvatar>();
			while (rs.next()) {
				UserAvatar userAvatar = new UserAvatar();
				initUserAvatar(rs, userAvatar);
				uaList.add(userAvatar);
			}
			return uaList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(rs, ps, conn);
		}
		return null;
	}

	private void initUserAvatar(ResultSet rs, UserAvatar userAvatar) throws SQLException {
		userAvatar.setMUserName(rs.getString(I.User.USER_NAME));
		userAvatar.setMUserNick(rs.getString(I.User.NICK));
		userAvatar.setMAvatarId(rs.getInt(I.Avatar.AVATAR_ID));
		userAvatar.setMAvatarPath(rs.getString(I.Avatar.AVATAR_PATH));
		userAvatar.setMAvatarSuffix(rs.getString(I.Avatar.AVATAR_SUFFIX));
		userAvatar.setMAvatarType(rs.getInt(I.Avatar.AVATAR_TYPE));
		userAvatar.setMAvatarLastUpdateTime(rs.getString(I.Avatar.UPDATE_TIME));
	}

	@Override
	public boolean deleteUser(String userName) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		try {
			// 关闭事务的自动提交
			conn.setAutoCommit(false);
			String sql = "delete from " + I.User.TABLE_NAME + " where " + I.User.USER_NAME + "=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			int countUser = ps.executeUpdate();
			ps.close();

			sql = "delete from " + I.Avatar.TABLE_NAME + " where " + I.Avatar.USER_NAME + "=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			int countAvatar = ps.executeUpdate();
			// 提交事务
			if (countUser > 0 && countAvatar > 0) {
				conn.commit();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DBUtils.closeAll(null, ps, conn);
		}
		return false;
	}

	/**
	 * 查找cname是否是name的好友
	 */
	@Override
	public boolean findContact(String name, String cname) {
		Connection conn = DBUtils.getConnection();
		String sql = "select * from " + I.Contact.TABLE_NAME + " where " + I.Contact.USER_NAME + "=?" + " and "
				+ I.Contact.CU_NAME + "=?";
		System.out.println("connection=" + conn + ",sql=" + sql);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, cname);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(rs, ps, conn);
		}
		return false;
	}

	/**
	 * 创建好友关系
	 */
	@Override
	public boolean addContact(String name, String cname) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		try {
			String sql = "insert into " + I.Contact.TABLE_NAME + "(" + I.Contact.USER_NAME + "," + I.Contact.CU_NAME
					+ ")values(?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, cname);
			int count = ps.executeUpdate();
			return count > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(null, ps, conn);
		}
		return false;
	}

	/**
	 * 删除好友关系
	 */
	@Override
	public boolean delContact(String name, String cname) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		try {
			String sql = "delete from " + I.Contact.TABLE_NAME + " where " + " (" + I.Contact.USER_NAME + "=?" + "and "
					+ I.Contact.CU_NAME + "=? ) or " + " (" + I.Contact.USER_NAME + "=?" + "and " + I.Contact.CU_NAME
					+ "=?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, cname);
			ps.setString(3, cname);
			ps.setString(4, name);
			int count = ps.executeUpdate();
			return count == 2;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DBUtils.closeAll(null, ps, conn);
		}
	}
}
