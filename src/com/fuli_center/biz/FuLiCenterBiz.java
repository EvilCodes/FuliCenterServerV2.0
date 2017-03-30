package com.fuli_center.biz;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fuli_center.bean.BoutiqueBean;
import com.fuli_center.bean.CartBean;
import com.fuli_center.bean.CategoryChildBean;
import com.fuli_center.bean.CategoryParentBean;
import com.fuli_center.bean.CollectBean;
import com.fuli_center.bean.ColorBean;
import com.fuli_center.bean.GoodDetailsBean;
import com.fuli_center.bean.NewGoodBean;
import com.fuli_center.bean.Pager;
import com.fuli_center.bean.Result;
import com.fuli_center.bean.UserAvatar;
import com.fuli_center.dao.FuLiCenterDao;
import com.fuli_center.dao.IFuLiCenterDao;
import com.fuli_center.pojo.User;
import com.fuli_center.utils.I;
import com.fuli_center.utils.ImageUtil;
import com.fuli_center.utils.PropertiesUtils;

public class FuLiCenterBiz implements IFuLiCenterBiz{

	IFuLiCenterDao dao = new FuLiCenterDao();;

	@Override
	public boolean addCart(CartBean cart) {
		CartBean cb = dao.findCartExist(cart.getUserName(), cart.getGoodsId());
		// 如果购物车中存在，则更新count +1
		if(cb!=null){
			return dao.updateCart(cb.getId(), cb.getCount()+1);
		}else{// 不存在则插入
			return dao.addCart(cart);
		}
	}

	@Override
	public boolean deleteCart(int cartId) {
		return dao.deleteCart(cartId);
	}

	@Override
	public boolean updateCart(int cartId,int count) {
		return dao.updateCart(cartId,count);
	}
	
	@Override
	public boolean updateCart(int cartId,int count,boolean isChecked) {
		return dao.updateCart(cartId,count,isChecked);
	}
	
	/*@Override
	public CartBean[] findCarts(String userName,int pageId, int pageSize) {
		return dao.findCarts(userName,pageId, pageSize);
	}*/
	@Override
	public List<CartBean> findCarts(String userName/*,int pageId, int pageSize*/) {
		List<CartBean> list = dao.findCarts(userName/*,pageId, pageSize*/);
		for(int i=0;i<list.size();i++){
			GoodDetailsBean goodDetailsBean = dao.findGoodDetails(list.get(i).getGoodsId());
			list.get(i).setGoods(goodDetailsBean);
		}
		return list;
	}
	
	@Override
	public NewGoodBean[] findNewAndBoutiqueGoods(int catId,int pageId, int pageSize) {
		return dao.findNewAndBoutiqueGoods(catId,pageId, pageSize);
	}

	@Override
	public CategoryParentBean[] findCategoryParent() {
		return dao.findCategoryParent();
	}

	@Override
	public CategoryChildBean[] findCategoryChildren(int parentId/*, int pageId,
			int pageSize*/) {
		return dao.findCategoryChildren(parentId/*, pageId, pageSize*/);
	}

	@Override
	public CategoryChildBean[] findCategoryChildren(int parentId, int pageId,
			int pageSize) {
		return dao.findCategoryChildren(parentId, pageId, pageSize);
	}

	@Override
	public GoodDetailsBean findGoodDetails(int goodsId) {
		return dao.findGoodDetails(goodsId);
	}

	@Override
	public ColorBean[] findColorsByCatId(int catId) {
		return dao.findColorsByCatId(catId);
	}

	@Override
	public int addCollect(CollectBean collect) {
		return dao.addCollect(collect);
	}

	@Override
	public boolean  deleteCollect(String userName,int id) {
		return dao.deleteCollect(userName,id);
	}
	
	@Override
	public List<CollectBean> findCollects(String userName, int pageId, int pageSize) {
		return dao.findCollects(userName, pageId, pageSize);
	}
	

	@Override
	public int findCollectCount(String userName) {
		return dao.findCollectCount(userName);
	}
	
	@Override
	public boolean isCollect(String userName, int goodsId) {
		return dao.isCollect(userName, goodsId);
	}

	@Override
	public BoutiqueBean[] findBoutiques() {
		return dao.findBoutiques();
	}
	
	@Override
	public ArrayList<GoodDetailsBean> findGoodsDetails(int catId, int pageId, int pageSize) {
		return dao.findGoodsDetails(catId, pageId, pageSize);
	}
	
	/**
	 * 下载指定地址的图片
	 * @param response
	 * @param imgUrl
	 */
	@Override
	public void downloadImg(HttpServletResponse response, String imgUrl) {
		File file=new File(I.GOODS_IMG_PATH, imgUrl);
		FileInputStream fis=null;
		try {
			fis=new FileInputStream(file);
			int len;
			byte[] buffer=new byte[8*1024];
			while((len=fis.read(buffer))!=-1){
				response.getOutputStream().write(buffer, 0, len);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Result register(User user, HttpServletRequest request) {
		Result result = new Result();
		// 完成注册业务逻辑
		// 查找数据库中有没有重名的用户
		User u = dao.findUserByUsername(user.getMUserName());
		if(u==null){// 没有
			// 获得头像的后缀名
			String suffix = uploadAvatar(user.getMUserName(),I.AVATAR_TYPE_USER_PATH,request);
			// if(suffix!=null){// 头像上传成功
				if(dao.addUserAndAvatar(user,suffix)){// 注册成功
					result.setRetMsg(true);
					result.setRetCode(I.MSG_SUCCESS);
				}else{
					// 删除头像
					deleteAvatar(PropertiesUtils.getValue("avatar_path","path.properties")+I.AVATAR_TYPE_USER_PATH+"/",user.getMUserName()+suffix);
					result.setRetMsg(false);
					result.setRetCode(I.MSG_REGISTER_FAIL);
				}
			/*}else{// 头像上传失败
				result.setRetMsg(false);
				result.setRetCode(I.MSG_REGISTER_UPLOAD_AVATAR_FAIL);
			}*/
		}else{// 已存在
			result.setRetMsg(false);
			result.setRetCode(I.MSG_REGISTER_USERNAME_EXISTS);
		}
		return result;
	}
	
	/**
	 * 删除头像
	 * @param path
	 * @param name
	 */
	private void deleteAvatar(String path,String imageName) {
		File file = new File(path,imageName);
		if(file.exists()){
			file.delete();
		}
	}
	
	private String uploadAvatar(String name,String avatarType,HttpServletRequest request){
		String path = null;
		if (avatarType.equals(I.AVATAR_TYPE_USER_PATH)) {// 用户上传头像
			path = PropertiesUtils.getValue("avatar_path", "path.properties") + I.AVATAR_TYPE_USER_PATH + "/";
		} else if (avatarType.equals(I.AVATAR_TYPE_GROUP_PATH)) {// 群组上传
			path = PropertiesUtils.getValue("avatar_path", "path.properties") + I.AVATAR_TYPE_GROUP_PATH + "/";
		}
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
			// 设置临时文件目录
			factory.setRepository(new File(PropertiesUtils.getValue("temp_path", "path.properties")));// 设置缓冲区目录
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(4194304); // 设置最大文件尺寸，这里是4MB
			List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
			Iterator<FileItem> i = items.iterator();
			String fileName = null;
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				fileName = fi.getName();
				if (fileName != null) {
					File savedFile = null;
					if(name.indexOf(".")!=-1){// 更新头像操作
						// 如果是更新图片，传过来的是shangpeng.jpg,，需要修改为shangpeng.png,上传则不必
						savedFile = new File(path, name.substring(0,name.lastIndexOf(".")) + fileName.substring(fileName.lastIndexOf(".")));
					}else{// 上传头像操作
						savedFile = new File(path, name + fileName.substring(fileName.lastIndexOf(".")));
					}
					fi.write(savedFile);
				}
			}
			return fileName.substring(fileName.lastIndexOf("."));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Result unRegister(String userName) {
		Result result = new Result();
		boolean isDelete = dao.deleteUser(userName);
		if(isDelete){
			String path = PropertiesUtils.getValue("avatar_path","path.properties") + I.AVATAR_TYPE_USER_PATH 
					+ "/" + userName + I.AVATAR_SUFFIX_JPG;
			File file = new File(path);
			if (file.exists()){
				file.delete();
				result.setRetMsg(true);
				result.setRetCode(I.MSG_SUCCESS);
			}
		}else{
			result.setRetMsg(false);
			result.setRetCode(I.MSG_UNREGISTER_FAIL);
		}
		return result;
	}
	
	@Override
	public Result login(User user) {
		Result result = new Result();
		User u = dao.findUserByUsername(user.getMUserName());
		if(u!=null){
			if(u.getMUserPassword().equals(user.getMUserPassword())){
				result.setRetMsg(true);
				result.setRetCode(I.MSG_SUCCESS);
				UserAvatar ua = dao.getUserAvatarByUsername(user.getMUserName());
				result.setRetData(ua);
			}else{
				result.setRetMsg(false);
				result.setRetCode(I.MSG_LOGIN_ERROR_PASSWORD);
			}
		}else{
			result.setRetMsg(false);
			result.setRetCode(I.MSG_LOGIN_UNKNOW_USER);
		}
		return result;
	}

	
	/**
	 * 根据用户名更新昵称
	 */
	@Override
	public Result updateNick(String username, String nick) {
		Result result = new Result();
		if(dao.updateNick(username,nick)){// 更新成功
			result.setRetMsg(true);
			result.setRetCode(I.MSG_SUCCESS);
			UserAvatar ua = dao.getUserAvatarByUsername(username);
			result.setRetData(ua);
		}else{// 更新失败
			result.setRetMsg(false);
			result.setRetCode(I.MSG_USER_UPDATE_NICK_FAIL);
		}
		return result;
	}
	@Override
	public Result updatePassword(String username, String password) {
		Result result = new Result();
		if(dao.updatePassword(username,password)){// 更新成功
			result.setRetMsg(true);
			result.setRetCode(I.MSG_SUCCESS);
			UserAvatar ua = dao.getUserAvatarByUsername(username);
			result.setRetData(ua);
		}else{// 更新失败
			result.setRetMsg(false);
			result.setRetCode(I.MSG_USER_UPDATE_PASSWORD_FAIL);
		}
		return result;
	}
	@Override
	public void downAvatar(String nameOrHxid,String avatarSuffix ,String avatarType, HttpServletResponse response,String width,String height) {
		// 1、从文件中读
		// 2、将读到的内容写入到客户端
		response.setContentType("image/jpeg"); // MIME
		File file = new File(PropertiesUtils.getValue("avatar_path","path.properties")+avatarType+"/",nameOrHxid+avatarSuffix);
		try {
			ImageUtil.zoom(file.getPath(), response.getOutputStream(), Integer.parseInt(width), Integer.parseInt(height));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新用户或群组的头像
	 */
	@Override
	public Result updateAvatar(String nameOrHxid, String avatarType, HttpServletRequest request) {
		Result result = new Result();
		// 先上传新图片覆盖旧图片
		String suffix = uploadAvatar(nameOrHxid,avatarType,request);
		if(suffix!=null){
			if(dao.updateAvatar(nameOrHxid,avatarType,suffix)){// 更新头像表的最后更新时间
				if (avatarType.equals(I.AVATAR_TYPE_USER_PATH)) {// 用户
					UserAvatar ua = dao.getUserAvatarByUsername(nameOrHxid);
					result.setRetData(ua);
				}/* else if (avatarType.equals(I.AVATAR_TYPE_GROUP_PATH)) {// 群组
					GroupAvatar ga = dao.findGroupAvatarByHxId(nameOrHxid);
					result.setRetData(ga);
				}*/
				result.setRetMsg(true);
				result.setRetCode(I.MSG_SUCCESS);
			}else{
				result.setRetMsg(false);
				result.setRetCode(I.MSG_UPLOAD_AVATAR_FAIL);
			}
		}else{
			result.setRetMsg(false);
			result.setRetCode(I.MSG_UPLOAD_AVATAR_FAIL);
		}
		return result;
	}

	@Override
	public Result findContactPagesByUserName(String userName, String pageId, String pageSize) {
		Result result = new Result();
		List<UserAvatar> listUserAvatar = dao.findContactPagesByUserName(userName, pageId, pageSize);
		if(listUserAvatar!=null){
			Pager pager = getPager(pageId, listUserAvatar);
			result.setRetData(pager);
			result.setRetMsg(true);
			result.setRetCode(I.MSG_SUCCESS);
		}else{
			result.setRetMsg(false);
			result.setRetCode(I.MSG_GET_CONTACT_PAGES_FAIL);
		}
		return result;
	}
	
	/**
	 * 将分页查询得到的内容封装为Pager类
	 * @param pageId
	 * @param list
	 * @param maxRecord
	 * @return
	 */
	public Pager getPager(String pageId,List<?> list){
		Pager pager = new Pager();
		pager.setCurrentPage(Integer.parseInt(pageId));
		pager.setMaxRecord(list.size());
		pager.setPageData(list);
		return pager;
	}
	
	@Override
	public Result findContactAllByUserName(String userName) {
		Result result = new Result();
		List<UserAvatar> listUserAvatar = dao.findContactAllByUserName(userName);
		if(listUserAvatar!=null){
			result.setRetData(listUserAvatar);
			result.setRetMsg(true);
			result.setRetCode(I.MSG_SUCCESS);
		}else{
			result.setRetMsg(false);
			result.setRetCode(I.MSG_GET_CONTACT_ALL_FAIL);
		}
		return result;
	}

	/**
	 * 根据用户名查找用户
	 * 1、查找到了则返回成功和查找到的用户信息
	 * 2、查找不到则返回false
	 */
	@Override
	public Result findUserByUserName(String userName) {
		Result result = new Result();
		UserAvatar ua = dao.getUserAvatarByUsername(userName);
		if(ua==null){
			result.setRetMsg(false);
			result.setRetCode(I.MSG_LOGIN_UNKNOW_USER);
		}else{
			result.setRetMsg(true);
			result.setRetCode(I.MSG_SUCCESS);
			result.setRetData(ua);
		}
		return result;
	}

	/**
	 * 根据用户名或昵称，模糊分页查询数据信息
	 */
	@Override
	public Result findUsersForSearch(String userName, String userNick, String pageId, String pageSize) {
		Result result = new Result();
		List<UserAvatar> uaList = dao.findUsersForSearch(userName, userNick, pageId, pageSize);
		if(uaList==null){
			result.setRetMsg(false);
			result.setRetCode(I.MSG_LOGIN_UNKNOW_USER);
		}else{
			result.setRetMsg(true);
			result.setRetCode(I.MSG_SUCCESS);
			Pager pager = getPager(pageId, uaList);
			result.setRetData(pager);
		}
		return result;
	}
	
	/**
	 * 添加好友关系：
	 * 1、如果已经存在好友关系，则返回相关信息
	 * 2、不存在关系，则建立关系
	 * 3、建立失败，返回失败信息，建立成功，返回被添加用户的信息
	 */
	@Override
	public Result addContact(String name, String cname) {
		Result result = new Result();
		boolean isContact = dao.findContact(name,cname);
		if(isContact){
			result.setRetMsg(false);
			result.setRetCode(I.MSG_CONTACT_FIRENDED);
		}else{
			boolean addContact = dao.addContact(name,cname);
			if(addContact){
				result.setRetMsg(true);
				result.setRetCode(I.MSG_SUCCESS);
				UserAvatar ua = dao.getUserAvatarByUsername(cname);
				result.setRetData(ua);
			}else{
				result.setRetMsg(false);
				result.setRetCode(I.MSG_CONTACT_ADD_FAIL);
			}
		}
		return result;
	}

	/**
	 * 删除好友关系
	 * 1、删除成功，返回true
	 * 2、删除失败，返回false即可
	 */
	@Override
	public Result delContact(String name, String cname) {
		Result result = new Result();
		boolean delContact = dao.delContact(name,cname);
		if(delContact){
			result.setRetMsg(true);
			result.setRetCode(I.MSG_SUCCESS);
		}else{
			result.setRetMsg(false);
			result.setRetCode(I.MSG_CONTACT_DEL_FAIL);
		}
		return result;
	}

}
