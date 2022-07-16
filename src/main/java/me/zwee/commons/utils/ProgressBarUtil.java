package me.zwee.commons.utils;

public class ProgressBarUtil {

    public static String getProgressBar(int bars, float percent){
        StringBuilder bar = new StringBuilder();
        for(int i = 0; i < bars; i++){
            bar.append(Colour.colour("&" + (percent > 100/bars ? "a" : "c") + "|"));
            percent -= 100/bars;
        }
        return bar.toString();
    }
}
