package danielstaal.pset4;

/**
 * Created by Daniël on 9-5-2016.
 */
public class TodoItem {

    private String title;
    private boolean completed = false;

    public TodoItem(String itemTitle){
        title = itemTitle;
    }

    public String getTitle(){
        return title;
    }

    public boolean isCompleted(){
        return completed;
    }

    public void setCompleted(boolean flag){
        completed = flag;
    }
}
