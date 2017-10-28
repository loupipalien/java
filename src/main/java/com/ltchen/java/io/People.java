package com.ltchen.java.io;

import java.io.IOException;
import java.util.ArrayList;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

/**
 * @file : People.java
 * @date : 2017年7月8日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 读取xml并反序列化
 */
public class People extends ArrayList<Person>{

	private static final long serialVersionUID = -6454911280859454560L;

	public People(String fileName) throws ValidityException, ParsingException, IOException {
		Document doc = new Builder().build(fileName);
		Elements elements = doc.getRootElement().getChildElements();
		for (int i = 0; i < elements.size(); i++) {
			this.add(new Person(elements.get(i)));
		}
	}
	
	public static void main(String[] args) throws ValidityException, ParsingException, IOException {
		People people = new People("pom.out");
		System.out.println(people);
	}
}
