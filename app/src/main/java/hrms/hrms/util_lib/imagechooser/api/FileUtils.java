package hrms.hrms.util_lib.imagechooser.api;

import android.os.Environment;

import java.io.File;

public class FileUtils {
    public FileUtils() {
    }

    public static String getDirectory(String foldername) {
        File directory = null;
        directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + foldername);
        if(!directory.exists()) {
            directory.mkdirs();
        }

        return directory.getAbsolutePath();
    }

    public static String getFileExtension(String filename) {
        String extension = "";

        try {
            extension = filename.substring(filename.lastIndexOf(".") + 1);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return extension;
    }
}
