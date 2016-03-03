package com.example.anthony.todolists;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListDetail extends AppCompatActivity {
    public static final String TAG_DETAILS = "ListDetail";
    public static final String TODO_LIST_NAME = "listName";
    public static final int SELECT_TODO_LIST = 10;

    FloatingActionButton fab;
    Button backButton;
    TextView emptyListMessage;
    TextView titleListName;
    EditText inputText;
    String todoListName;
    ListView mListView;
    ArrayList<String> mToDoItems;
    ArrayAdapter<String> mArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        setOnClickListeners();
        setOnItemClickListener();
        setOnItemLongClickListener();

        //get the intent extra...
        Intent intent = getIntent();
        todoListName = intent.getStringExtra(TODO_LIST_NAME);
        titleListName.setText(todoListName);
        if (intent.hasExtra("listItems")) {
            ArrayList<String> getItems = new ArrayList<>();
            //mToDoItems = intent.getStringArrayListExtra("listItems");
            getItems = intent.getStringArrayListExtra("listItems");
            mToDoItems.addAll(getItems);
            mArrayAdapter.notifyDataSetChanged();
        }



    }

    private void initViews() {
        fab = (FloatingActionButton) findViewById(R.id.list_details_fab);
        backButton = (Button) findViewById(R.id.list_details_back_button);
        emptyListMessage = (TextView) findViewById(R.id.list_details_empty_list_notes);
        titleListName = (TextView) findViewById(R.id.list_details_header_todo_list_name);
        inputText = (EditText) findViewById(R.id.list_details_edit_text);
        mListView = (ListView) findViewById(R.id.list_details_listview);

        //instantiate arraylist
        mToDoItems = new ArrayList<>(); // maybe set this to 40 or so to save mem from it doubling

        //instantiate adapter
        mArrayAdapter = new ArrayAdapter<String>(ListDetail.this, android.R.layout.simple_list_item_1, mToDoItems);

        //set the adapter to the list
        mListView.setAdapter(mArrayAdapter);

    }

    private void setOnClickListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputText.getText().toString().equals("")) {
                    Toast.makeText(ListDetail.this, "Please fill out the field to add something to the list", Toast.LENGTH_SHORT).show();
                } else {
                    emptyListMessage.setVisibility(View.INVISIBLE);
                    String newToDoItem = inputText.getText().toString();
                    mToDoItems.add(newToDoItem);
                    mArrayAdapter.notifyDataSetChanged();
                    inputText.setText("");
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToMain = new Intent();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("listName", mToDoItems);
                intentToMain.putExtras(bundle);
                setResult(RESULT_OK, intentToMain);
                finish();
            }
        });
    }

    private void setOnItemClickListener() {
        // i might try to add another activity to actually edit the list item
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void setOnItemLongClickListener() {
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String positionItem = mArrayAdapter.getItem(position);
                mToDoItems.remove(positionItem);
                mArrayAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

}
