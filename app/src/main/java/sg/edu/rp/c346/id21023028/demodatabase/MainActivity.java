package sg.edu.rp.c346.id21023028.demodatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnInsert, btnGetTasks;
    TextView tvResults;
    ArrayAdapter adapter;
    ArrayList<String> dataString;
    ListView lv;
    EditText etTaskName,etTaskDate;
    String order = " ASC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTaskDate = findViewById(R.id.taskDate);
        etTaskName = findViewById(R.id.taskName);

        btnInsert = findViewById(R.id.btnInsert);
        btnGetTasks = findViewById(R.id.btnGetTasks);
        tvResults = findViewById(R.id.tvResults);
        dataString = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,dataString);
        lv = findViewById(R.id.lv);
        lv.setAdapter(adapter);




        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Create the DBHelper object, passing in the
                // activity's Context
                DBHelper db = new DBHelper(MainActivity.this);

                //get user input
                String description = etTaskName.getText().toString();
                String date = etTaskDate.getText().toString();

                // Insert a task
                //db.insertTask("Submit RJ", "25 Apr 2021");
                db.insertTask(description, date);

            }
        });

        btnGetTasks.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Create the DBHelper object, passing in the
                // activity's Context
                DBHelper db = new DBHelper(MainActivity.this);

                // Insert a task
                ArrayList<String> data = db.getTaskContent();
                db.close();

                String txt = "";
                for (int i = 0; i < data.size(); i++) {
                    Log.d("Database Content", i +". "+data.get(i));
                    txt += i + ". " + data.get(i) + "\n";
                }
                tvResults.setText(txt);

                //clear arraylist
                dataString.clear();

                //get data for arrayadapter
                ArrayList<Task> data2 = db.getTasks(order);
                for (Task task : data2){
                    dataString.add(task.toString());
                }
                adapter.notifyDataSetChanged();



            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        if (id == R.id.Ascending) {
            order = " ASC";
            return true;
        } else if (id == R.id.Descending) {
            order = " DESC";
            return true;
        } else {
            order = " ASC";
        }
        return super.onOptionsItemSelected(item);
    }
}
