package danielstaal.pset4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Created by Daniel Staal on 4/25/2016.
 *
 * Program to implement a ToDo-list app
 * separate lists with separate items
 * this activity contains 2 listviews, one for the lists, and one to show the items in the
 * selected list
 */
public class MainActivity extends AppCompatActivity {

    private AssignmentManagerSingleton manager = AssignmentManagerSingleton.getOurInstance();

    TodoList selectedTodoList;

    ArrayList<String> titlesOfItems = new ArrayList<>();
    ArrayList<String> titlesOfLists = new ArrayList<>();

    ListView selectedLV;
    ListView listsLV;

    EditText addItemET;

    ArrayAdapter<String> toDoItemsAdapter;
    ArrayAdapter<String> toDoListsAdapter;
    DBhelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new DBhelper(this);
        manager.readTodos(database);

        getLists();
        setListsLV();
        setSelectedLV();

        addItemET = (EditText)findViewById(R.id.newItem);

        setupOnClickListener();
        setupLongClickListener();
    }

    /*
     * setup a listener to select a list
     */
    private void setupOnClickListener(){
        listsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick (AdapterView <?> adapter, View v, int position, long id){

                selectedTodoList = manager.getLists().get(position);
                getTitles();
                setSelectedLV();
            }
        });
    }

    /*
     * setting up the longClickListeners to remove an item or a list from the database
     */
    private void setupLongClickListener(){
        selectedLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v,
                                           int pos, long id) {
                // delete item from sql
                database.delete(titlesOfItems.get(pos), selectedTodoList.getListName());
                // remove item from listItems
                selectedTodoList.getList().remove(pos);
                getTitles();
                toDoItemsAdapter.notifyDataSetChanged();

                return true;
            }
        });
        // from here to remove a list
        listsLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v,
                                           int pos, long id) {
                // delete list from sql
                database.deleteList(manager.getLists().get(pos).getListName());
                // remove list from lists
                manager.getLists().remove(pos);
                getLists();
                toDoListsAdapter.notifyDataSetChanged();
                titlesOfItems.clear();
                toDoItemsAdapter.notifyDataSetChanged();

                return true;
            }
        });
    }

    /*
     * add item to the database list of items
     */
    public void addItem(View v){
        if(selectedTodoList == null){
            Toast.makeText(getApplicationContext(), "please select a list first",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            // add item to listItems
            String item = addItemET.getText().toString();
            selectedTodoList.addItemToList(new TodoItem(item));
            getTitles();
            addItemET.setText("");
            toDoItemsAdapter.notifyDataSetChanged();

            // add item to sql
            database.create(item, selectedTodoList.getListName());
        }
    }

    /*
     * add list
     */
    public void goToAddListActivity(View v){
        Intent addListAct = new Intent(this, AddListActivity.class);
        startActivity(addListAct);
    }

    /*
     * function to initially set the selected Todolist listView
     */
    private void setSelectedLV(){
        selectedLV = (ListView)findViewById(R.id.itemlist);
        toDoItemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titlesOfItems);
        selectedLV.setAdapter(toDoItemsAdapter);
        selectedLV.setTextFilterEnabled(true);
    }

    /*
     * function to set the todoLists listView
     */
    private void setListsLV(){
        listsLV = (ListView)findViewById(R.id.newList);
        toDoListsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titlesOfLists);
        listsLV.setAdapter(toDoListsAdapter);
        listsLV.setTextFilterEnabled(true);
    }

    /*
     * Put the titles of items in an arraylist
     */
    private void getTitles(){
        titlesOfItems.clear();
        for(int i=0; i<selectedTodoList.getList().size(); i++){
            titlesOfItems.add(selectedTodoList.getList().get(i).getTitle());
        }
    }

    /*
     * Put the names of the lists in an arraylist
     */
    private void getLists(){
        titlesOfLists.clear();
        for(int i=0; i<manager.getLists().size(); i++){
            titlesOfLists.add(manager.getLists().get(i).getListName());
        }
    }
}