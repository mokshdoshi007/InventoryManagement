package tops.technologies.ptc;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

public class addstock extends AppCompatActivity {
    EditText category,reference,size,quantity,location;
    String c,r,s,q,l;
    private DatabaseHelper databaseHelper;
    private Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        setContentView(R.layout.activity_addstock);
        category=findViewById(R.id.category);
        reference=findViewById(R.id.reference);
        size=findViewById(R.id.size);
        quantity=findViewById(R.id.quantity);
        location=findViewById(R.id.location);
        databaseHelper = new DatabaseHelper(this);
        model = new Model();
    }

    public void add(View view) {
        c=category.getText().toString();
        r=reference.getText().toString();
        s=size.getText().toString();
        q=quantity.getText().toString();
        l=location.getText().toString();
        model.setCategory(c);
        model.setLocation(l);
        model.setQuantity(Integer.parseInt(q));
        model.setSize(s);
        model.setReference(r);
        databaseHelper.addstock(model);
        Toast.makeText(this, "Successfully Added", Toast.LENGTH_SHORT).show();
        finish();
    }


}