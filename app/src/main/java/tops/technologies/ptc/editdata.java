package tops.technologies.ptc;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class editdata extends AppCompatActivity {
    EditText category,reference,size,quantity,location;
    String id;
    private DatabaseHelper databaseHelper;
    private Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdata);
        category=findViewById(R.id.category3);
        reference=findViewById(R.id.reference3);
        size=findViewById(R.id.size3);
        quantity=findViewById(R.id.quantity3);
        location=findViewById(R.id.location3);
        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        id = String.valueOf(intent.getIntExtra("id",0));

        model = databaseHelper.getstockbyid(id);

        category.setText(model.getCategory());
        reference.setText(model.getReference());
        size.setText(model.getSize());
        quantity.setText(String.valueOf(model.getQuantity()));
        location.setText(model.getLocation());
    }
    public void delete(View view) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete entry");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                databaseHelper.deletebyid(id);
                finish();
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }

    public void edit(View view) {
        databaseHelper.updatebyid(id,category.getText().toString(),reference.getText().toString(),size.getText().toString(),quantity.getText().toString(),location.getText().toString());
        finish();
    }
}