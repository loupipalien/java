package com.ltchen.java.javase.lang;

/**
 * 
 * @file : Person.java
 * @date : 2017年3月22日
 * @author : LTChen
 * @email : LouPiPAlien@gmail.com
 * @desc : 重写equals(Object obj)方法和hashcode()方法
 */
public class OverloadEqualsAndHashcode {

	private String name;
	private int id;

	public OverloadEqualsAndHashcode() {
	}

	public OverloadEqualsAndHashcode(String name, int id) {
		this.id = id;
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public int getId() {
		return this.id;
	}

	// 重写equals(Object obj)方法
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof OverloadEqualsAndHashcode)) {
			return false;
		}

		final OverloadEqualsAndHashcode person = (OverloadEqualsAndHashcode) obj;
		if (!this.getName().equals(person.getName())) {
			return false;
		}
		if (this.getId() != person.getId()) {
			return false;
		}

		return true;
	}

	//重写hashCode()方法
	@Override
	public int hashCode() {
		return this.name.hashCode() + id;
	}

	public static void main(String[] args) {

		OverloadEqualsAndHashcode p0 = new OverloadEqualsAndHashcode("jack", 18);
		OverloadEqualsAndHashcode p1 = new OverloadEqualsAndHashcode("rose", 17);
		OverloadEqualsAndHashcode p2 = new OverloadEqualsAndHashcode("jack", 18);
		if (p0.equals(p1)) {
			System.out.println("p0 equals p1");
		}
		if (p0.hashCode() == p1.hashCode()) {
			System.out.println("hashcode of p0 == hashcode of p1");
		}
		if (p0.equals(p2)) {
			System.out.println("p0 equals p2");
		}
		if (p0.hashCode() == p2.hashCode()) {
			System.out.println("hashcode of p0 == hashcode of p2");
		}
	}
}
