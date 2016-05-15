package danielstaal.pset4;

import java.util.ArrayList;

/**
 * Created by Daniël on 9-5-2016.
 */
public class TodoList {

    private String listName;
    private ArrayList<TodoItem> list = new ArrayList<>();

    public TodoList(String newListName){
        listName = newListName;
    }

    public void addItemToList(TodoItem newItem){
        list.add(newItem);
    }

    public ArrayList<TodoItem> getList(){
        return list;
    }

    public String getListName(){
        return listName;
    }
}
