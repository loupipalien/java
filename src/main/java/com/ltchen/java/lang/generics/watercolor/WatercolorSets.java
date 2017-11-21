package com.ltchen.java.lang.generics.watercolor;

import com.ltchen.java.util.Sets;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author : ltchen
 * @date : 2017/11/22
 * @desc :
 */
public class WatercolorSets {

    public static void main(String[] args) {
        Set<Watercolors> one = EnumSet.range(Watercolors.BRILLIANT_RED, Watercolors.VIRIDIAN_HUE);
        Set<Watercolors> another = EnumSet.range(Watercolors.CERULEAN_BLUE_HUE, Watercolors.BURNT_UMBER);
        System.out.println("one:" + one);
        System.out.println("another:" + another);
        System.out.println("union:" + Sets.union(one, another));
        System.out.println("intersection:" + Sets.intersection(one, another));
        System.out.println("difference:" + Sets.difference(one, another));
        System.out.println("complement:" + Sets.complement(one, another));
    }
}
