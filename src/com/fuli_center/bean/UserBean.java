package com.fuli_center.bean;

import java.io.Serializable;
/** ��ʾע���û�
 *  �����ݿ���user���һ����¼*/
public class UserBean implements Serializable{
	private int id;//user�������
	private String userName;//�����˺�
	private String nick;//�ǳ�
	private String password;//����
	private String avatar;//ͷ���ڷ����Ӳ�̵ĵ�ַ
	private int unreadMsgCount;//δ����Ϣ��
	/**�ֻ�����ϵ�˶�λ������*/
	private String header;
	
	private String result;//���ؿͻ��������Ƿ�ɹ����ɹ���ok
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public int getUnreadMsgCount() {
		return unreadMsgCount;
	}
	public void setUnreadMsgCount(int unreadMsgCount) {
		this.unreadMsgCount = unreadMsgCount;
	}
	public UserBean() {
		// TODO Auto-generated constructor stub
	}
	
	public UserBean(String userName) {
		super();
		this.userName = userName;
	}
	
	public UserBean(String userName, String nick, String password) {
		super();
		this.userName = userName;
		this.nick = nick;
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "UserBean [id=" + id + ", userName=" + userName + ", nick="
				+ nick + ", password=" + password + ", avatar=" + avatar
				+ ", unreadMsgCount=" + unreadMsgCount + ", header=" + header
				+ ", result=" + result + "]";
	}
	
}
