package com.fuli_center.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

	/**
	 * ��t��ӵ�����array֮��
	 * @param array
	 * @param t
	 * @return
	 */
	public static <T> T[] add(T[] array,T t){
		//��������
		array=Arrays.copyOf(array, array.length+1);
		//��user����users�����һ��Ԫ��
		array[array.length-1]=t;
		return array;
	}
	
    /**
     * 将数组转换为ArrayList集合
     * @param ary
     * @return
     */
    public static <T> ArrayList<T> array2List(T[] ary){
        List<T> list = Arrays.asList(ary);
        ArrayList<T> arrayList=new ArrayList<T>(list);
        return arrayList;
    }
    
}
