package com.ltchen.java.io;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

/**
 * @file : Person.java
 * @date : 2017年7月8日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 将对象序列化为xml格式
 */
public class Person {

	private String first;
	private String last;
	
	public Person(String first, String last) {
		this.first = first;
		this.last = last;
	}
	
	public Person(Element element){
		first = element.getFirstChildElement("first").getValue();
		last = element.getFirstChildElement("last").getValue();
	}
	
	@Override
	public String toString() {
		return "Person [first=" + first + ", last=" + last + "]";
	}

	//将person对象转化为xml对象
	public Element toXml(){
		Element person = new Element("person");
		Element firstName = new Element("first");
		firstName.appendChild(first);
		Element lastName = new Element("last");
		lastName.appendChild(last);
		person.appendChild(firstName);
		person.appendChild(lastName);
		return person;
	}
	
	//格式化
	public static void format(OutputStream os, Document doc) throws IOException{
		Serializer serializer = new Serializer(os, "UTF-8");
		serializer.setIndent(4);
		serializer.setMaxLength(60);
		serializer.write(doc);
		serializer.flush();
	}
	
	public static void main(String[] args) throws IOException {
		List<Person> people = Arrays.asList(new Person("Dr.Busen", "Honeydew"),
				new Person("Gonzo", "The Great"),
				new Person("Phillip J.", "Fry"));
		System.out.println(people);
		Element root = new Element("people");
		for (Person person : people) {
			root.appendChild(person.toXml());
		}
		Document doc = new Document(root);
		Person.format(System.out, doc);
		Person.format(new BufferedOutputStream(new FileOutputStream("pom.out")), doc);
	}
}
