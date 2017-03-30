package com.fuli_center.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fuli_center.utils.DBUtils;

public class Test {
	public static void main(String[] args) {
		List<Integer> list = getList();
		Random ran = new Random();
		for(int i : list){
			int niRan = (100000000+ran.nextInt(100000000))+(100000+ran.nextInt(1000))+(10000+ran.nextInt(1000))+(10+ran.nextInt(10));
			updateAddTime(i,niRan);
		}
	}
	
	public static void updateAddTime(int id,int ran){
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		try {
			String sql = "update tb_good_details set add_time = ? where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, (1476950340530L-ran)+"");
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(null, ps, conn);
		}
	}

	public static List<Integer> getList() {
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Integer> list = new ArrayList<Integer>();
		try {
			String sql = "select id from tb_good_details";
			System.out.println("getUserAvatarByUsername:" + sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Integer(rs.getInt("id")));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(rs, ps, conn);
		}
		return null;
	}

}
