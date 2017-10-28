package com.ltchen.java.javase.test;

public class VarArgsTest {

	public static void testVarArgs(int... array){
		for (int i = 0; i < array.length; i++) {
			if(i < array.length -1){
				System.out.print(array[i] + ", ");
			}
			else{
				System.out.println(array[i]);
			}
		}
	}
	
	/**
	 * 此方法说明当能匹配定长参数的重载方法时先选择定长参数的重载方法,含有不定参数的重载方法是最后被选中的。
	 * @param arg1
	 * @param arg2
	 */
	public static void testVarArgs(int arg1, int arg2){
		System.out.println("This is not a var args list");
	}
	
	/**
	 * 会被提示重复的方法,说明参数列表相同,即可变参数是个数组
	 * @param args
	 */
//	public static void testVarArgs(int[] array){}
	
	/**
	 * 在main函数中被调用使会提示不知道选择
	 * testVarArgs(int... array)和testVarArgs(int count,int... array)哪一个方法调用
	 * 因为此方法的可变参数导致了参数个数模糊,并且调用使只传一个参数时,
	 * testVarArgs(int count,int... array)也证明了可变参数长度可为0
	 * @param array
	 * @param count
	 */
//	public static void testVarArgs(int count,int... array){}
	
	/**
	 * 会被提示可变参数只能是最后一个参数
	 * @param array
	 * @param count
	 */
//	public static void testVarArgs(int... array,int count){}
	
	public static void main(String... args) {
		testVarArgs();
		testVarArgs(1);
		testVarArgs(1,2);
	}

}
