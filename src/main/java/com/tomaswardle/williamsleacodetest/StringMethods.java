package com.tomaswardle.williamsleacodetest;

public class StringMethods {

    private StringMethods() {}
    
    public static String getSubstring(String line,int index1, int index2){
        if (line == null) {
            return null;
        }
        
        int len = line.length();
        if (index1 > len) {
            return null;
        }

        return line.substring(index1,Math.min(index2,len)).trim();
    }

    public static String getSubstring(String line,int index1){
        return getSubstring(line, index1, Integer.MAX_VALUE);
    }
}
