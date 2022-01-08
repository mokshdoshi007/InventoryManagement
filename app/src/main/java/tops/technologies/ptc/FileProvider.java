package tops.technologies.ptc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

public class FileProvider extends androidx.core.content.FileProvider {

    public Uri getDatabaseURI(Context c) {
        File exportFile = c.getDatabasePath("StockManager.db");
        return getFileUri(c, exportFile);
    }

    public Uri getFileUri(Context c, File f){
        return getUriForFile(c, "tops.technologies.ptc.fileprovider", f);
    }

}