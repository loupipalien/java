package com.ltchen.java.lang.innerclass;

/**
 * @file : GreenHouseControls.java
 * @date : 2017年7月27日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 温室控制
 */
public class GreenHouseControls extends Controller{

	private boolean light = false;
	public class LightOn extends Event {

		public LightOn(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			light = true;
		}
		
		@Override
		public String toString(){
			return System.nanoTime() + ":light is on.";
		}
	}
	public class LightOff extends Event {

		public LightOff(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			light = true;
		}
		
		@Override
		public String toString(){
			return System.nanoTime() + ":light is off.";
		}
	}
	
	private boolean water = false;
	public class WaterOn extends Event {

		public WaterOn(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			light = true;
		}
		
		@Override
		public String toString(){
			return System.nanoTime() + ":water is on.";
		}
	}
	public class WaterOff extends Event {

		public WaterOff(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			light = true;
		}
		
		@Override
		public String toString(){
			return System.nanoTime() + ":water is off.";
		}
	}
	
	private String thermostat = "day";
	public class ThermostatNight extends Event {

		public ThermostatNight(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			light = true;
		}
		
		@Override
		public String toString(){
			return System.nanoTime() + ":thermostat on night setting.";
		}
	}
	public class ThermostatDay extends Event {

		public ThermostatDay(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			light = true;
		}
		
		@Override
		public String toString(){
			return System.nanoTime() + ":thermostat on day setting.";
		}
	}
	
	public class Bell extends Event{
		
		public Bell(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			light = true;
		}
		
		@Override
		public String toString(){
			return System.nanoTime() + ":belling.";
		}
	}
	
	public class Restart extends Event{
		private Event[] events;
		
		public Restart(long delayTime,Event[] events) {
			super(delayTime);
			this.events = events;
			for (Event event : events) {
				addEvent(event);
			}
		}

		@Override
		public void action() {
			for (Event event : events) {
				//重跑每个event
				event.start();
				addEvent(event);
			}
			start();
			//重跑重启的event
			addEvent(this);
		}
		
		@Override
		public String toString(){
			return System.nanoTime() + ":restarting system.";
		}
	}
	
	public static class Terminate extends Event{

		public Terminate(long delayTime) {
			super(delayTime);
		}

		@Override
		public void action() {
			System.exit(0);
		}
		
		@Override
		public String toString() {
			return System.nanoTime() + ":terminating";
		}
		
	}

}
