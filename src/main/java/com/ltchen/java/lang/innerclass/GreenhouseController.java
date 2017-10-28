package com.ltchen.java.lang.innerclass;

/**
 * @file : GreenhouseController.java
 * @date : 2017年7月27日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 温室控制类
 */
public class GreenhouseController {

	public static void main(String[] args) {
		GreenHouseControls gc = new GreenHouseControls();
		gc.addEvent(gc.new Bell(900));
		Event[] events = {
			gc.new ThermostatNight(0),
			gc.new LightOn(200),
			gc.new LightOff(400),
			gc.new WaterOn(600),
			gc.new WaterOff(800),
			gc.new ThermostatNight(1400)
		};
		gc.addEvent(gc.new Restart(2000, events));
		gc.addEvent(new GreenHouseControls.Terminate(5000000));
		gc.run();
	}
}
