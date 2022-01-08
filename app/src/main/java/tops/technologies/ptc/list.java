package tops.technologies.ptc;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class list extends AppCompatActivity {
    private String sortby="reference";
    private EditText editText;
    private TextView textView;
    private RecyclerView recyclerViewUsers;
    private List<Model> modelList;
    private UserRecyclerAdapter userRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onResume() {
        super.onResume();
        getDatafromSQLite();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerViewUsers=findViewById(R.id.rv);
        editText = findViewById(R.id.searchfield);

        modelList=new ArrayList<>();
        userRecyclerAdapter = new UserRecyclerAdapter(modelList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(layoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(userRecyclerAdapter);
        databaseHelper=new DatabaseHelper(this);
        getDatafromSQLite();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

    }

    private void getDatafromSQLite() {

        new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected Void doInBackground(Void... voids) {
                modelList.clear();
                modelList.addAll(databaseHelper.getallstock(sortby));
                Log.d("data",modelList.toString());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                userRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    void filter(String text){
        List<Model> temp = new ArrayList();
        for(Model m: modelList){
            if(m.getReference().contains(text)){
                temp.add(m);
            }
        }
        userRecyclerAdapter.updateList(temp);
    }

    public void location(View view) {
        sortby="location";
        getDatafromSQLite();
    }

    public void reference(View view) {
        sortby="reference";
        getDatafromSQLite();
    }
}