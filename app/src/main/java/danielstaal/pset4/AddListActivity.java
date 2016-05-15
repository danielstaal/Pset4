package danielstaal.pset4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Daniël on 14-5-2016.
 *
 * Activity to add a list to the database
 */
public class AddListActivity extends AppCompatActivity{

    EditText addListET;
    DBhelper database = new DBhelper(this);
    private AssignmentManagerSingleton manager = AssignmentManagerSingleton.getOurInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
        addListET = (EditText)findViewById(R.id.listName);

    }
    /*
     * function to add a list to the database
     */
    public void addList(View v){
        // add item to listItems
        String listName = addListET.getText().toString();
        TodoList newList = new TodoList(listName);
        manager.addTodoList(newList);
        addListET.setText("");

        // add item to sql
        database.create("", listName);

        Intent goBack = new Intent(this, MainActivity.class);
        startActivity(goBack);;
    }
}
