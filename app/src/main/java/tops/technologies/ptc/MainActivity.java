package tops.technologies.ptc;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewstock(View view) {
        Intent intent = new Intent(MainActivity.this, list.class);
        startActivity(intent);
    }

    public void addstock(View view) {
        Intent intent = new Intent(MainActivity.this, addstock.class);
        startActivity(intent);
    }

    public void backup(View view) {
        copyContentToPrivateStorage();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/octet-stream");
        Uri uri = new FileProvider().getDatabaseURI(this);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, "Backup via:"));
    }

    private void copyContentToPrivateStorage() {
        try {
            File data = Environment.getDataDirectory();
            File sd = getFilesDir();
            if (sd.canWrite()) {
                String currentDBPath = "/data/tops.technologies.ptc/databases/StockManager.db";
                String backupDBPath = "StockManager.db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
            else
                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}