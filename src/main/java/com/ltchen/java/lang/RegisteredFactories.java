package com.ltchen.java.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @file : RegisteredFactories.java
 * @date : 2017年7月12日
 * @author : ltchen
 * @email : loupipalien@gmail.com
 * @desc : 每个类内部有一个工厂类,可用于创建该类实例.将各类的工厂类注册到RegisteredFactories中,
 * 则可由RegisteredFactories统一创建返回已注册到RegisteredFactories的类实例
 */
public class RegisteredFactories {

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(Part.createRandom());
		}
	}
}

class Part{
	private static Random random = new Random(47);
	public static List<Factory<? extends Part>> partFactoies = new ArrayList<Factory<? extends Part>>();
	
	static{
		partFactoies.add(new FuelFilter.Factory());
		partFactoies.add(new AirFilter.Factory());
		partFactoies.add(new CabinFilter.Factory());
		partFactoies.add(new OilFilter.Factory());
		partFactoies.add(new FanBelt.Factory());
		partFactoies.add(new GeneratorBelt.Factory());
		partFactoies.add(new PowerSteeringBelt.Factory());
	}
	
	public static Part createRandom(){
		int i = random.nextInt(partFactoies.size());
		return partFactoies.get(i).create();
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}

class Filter extends Part{}

class FuelFilter extends Filter{
	public static class Factory implements com.ltchen.java.lang.Factory<FuelFilter>{
		@Override
		public FuelFilter create() {
			return new FuelFilter();
		}
	}
}

class AirFilter extends Filter{
	public static class Factory implements com.ltchen.java.lang.Factory<AirFilter>{
		@Override
		public AirFilter create() {
			return new AirFilter();
		}
	}
}

class CabinFilter extends Filter{
	public static class Factory implements com.ltchen.java.lang.Factory<CabinFilter>{
		@Override
		public CabinFilter create() {
			return new CabinFilter();
		}
	}
}

class OilFilter extends Filter{
	public static class Factory implements com.ltchen.java.lang.Factory<OilFilter>{
		@Override
		public OilFilter create() {
			return new OilFilter();
		}
	}
}

class Belt extends Part{}

class FanBelt extends Belt{
	public static class Factory implements com.ltchen.java.lang.Factory<FanBelt>{
		@Override
		public FanBelt create() {
			return new FanBelt();
		}
	}
}

class GeneratorBelt extends Belt{
	public static class Factory implements com.ltchen.java.lang.Factory<GeneratorBelt>{
		@Override
		public GeneratorBelt create() {
			return new GeneratorBelt();
		}
	}
}

class PowerSteeringBelt extends Belt{
	public static class Factory implements com.ltchen.java.lang.Factory<PowerSteeringBelt>{
		@Override
		public PowerSteeringBelt create() {
			return new PowerSteeringBelt();
		}
	}
}