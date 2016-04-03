package com.creativejones.andre.simpletodo.data;

import android.content.Context;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TaskRepository {

    Context mContext;

    public TaskRepository(Context context){
        mContext = context;
    }

    public void add(List<String> _items){
        File filesDir = mContext.getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, _items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAll(){

        File filesDir = mContext.getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        ArrayList<String> items = new ArrayList<>();

        try {
            items.addAll(FileUtils.readLines(todoFile));
        } catch (IOException io) {
            io.printStackTrace();
        }finally {
            return items;
        }
    }
}
