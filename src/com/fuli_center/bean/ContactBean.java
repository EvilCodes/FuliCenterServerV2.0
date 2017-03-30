package com.fuli_center.bean;

import java.io.Serializable;
/**
 * ��ϵ��ʵ����
 * ��ʾ��ݱ�contact��һ����¼*/
public class ContactBean implements Serializable {

	private int myuid;//�û���user���е�id
	private int cuid;//myuid��ϵ����user���е�id
	private String result;//���ؿͻ��˽��ɹ���ok
	
	public int getMyuid() {
		return myuid;
	}
	public void setMyuid(int myuid) {
		this.myuid = myuid;
	}
	public int getCuid() {
		return cuid;
	}
	public void setCuid(int cuid) {
		this.cuid = cuid;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public ContactBean() {
		// TODO Auto-generated constructor stub
	}
	public ContactBean(int myuid, int cuid) {
		super();
		this.myuid = myuid;
		this.cuid = cuid;
	}
	@Override
	public String toString() {
		return "ContactBean [myuid=" + myuid + ", cuid=" + cuid + ", result="
				+ result + "]";
	}
}
