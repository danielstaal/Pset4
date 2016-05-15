package danielstaal.pset4;

import java.util.ArrayList;

/**
 * Created by Daniël on 9-5-2016.
 */
public class AssignmentManagerSingleton {

    // instance
    private static AssignmentManagerSingleton ourInstance = new AssignmentManagerSingleton();

    // other fields
    private ArrayList<TodoList> lists = new ArrayList<>();

    // constructor
    private AssignmentManagerSingleton(){
    }

    // methods
    public static AssignmentManagerSingleton getOurInstance(){
        return ourInstance;
    }

    public void addTodoList(TodoList newList){
        lists.add(newList);
    }

    public void deleteTodoList(int position){
        lists.remove(position);
    }

    public void readTodos(DBhelper helper){
        lists = helper.read();
    }

    public ArrayList<TodoList> getLists(){
        return lists;
    }
}
