package org.example.warehouse;

import java.util.HashMap;
import java.util.HashSet;

public class Category {
    private String name;

    //constructor
    private Category (String name){this.name =name;}

    private static HashMap<String, Category> cache = new HashMap<>();

    public String getName(){
        return name;
    }

    public static Category of (String name){
        //check name exists
        if(name==null) throw new IllegalArgumentException("Category name can't be null");
        //capitalize
        name=name.substring(0,1).toUpperCase()+name.substring(1);

        Category category=cache.get(name);
        if (category!=null) return category;
        else category = new Category(name);
        cache.put(name, category);
        return category;
    }
}
