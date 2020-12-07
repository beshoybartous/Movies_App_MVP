package com.example.moviesappmvp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SharedPref {
    private static Map<Integer,Integer> moviesIDMap=new HashMap<>();
    public static void setMoviesIDMap(List<Integer> moviesIDList){
        moviesIDMap = moviesIDList.stream().distinct().collect(Collectors.toMap(Integer::intValue, Integer::intValue));
    }
    public static void addValue(Integer id){
        moviesIDMap.put(id, id);
    }
    public static void removeValue(Integer id){
        moviesIDMap.remove(id, id);
    }
    public static boolean contain(Integer id){
       return moviesIDMap.containsKey(id);
    }

}
