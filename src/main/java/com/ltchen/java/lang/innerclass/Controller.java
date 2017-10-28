package com.ltchen.java.lang.innerclass;

import java.util.ArrayList;
import java.util.List;

/**
 * @file : Controller.java
 * @date : 2017年7月27日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 控制类
 */
public class Controller {

	private List<Event> events = new ArrayList<Event>();
	
	public void addEvent(Event event){
		events.add(event);
	} 
	
	public void run(){
		while(events.size() > 0){
			//复制一份
			for (Event event : new ArrayList<Event>(events)) {
				if(event.ready()){
					System.out.println(event);
					event.action();
					events.remove(event);
				}
			}
		}
	}
}
