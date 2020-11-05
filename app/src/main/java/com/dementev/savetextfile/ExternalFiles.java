package com.dementev.savetextfile;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ExternalFiles {
    Activity activity;
    String fileName;

    public static final int REQUEST_CODE_PERMISSION_WRITE_STORAGE = 11;

    public ExternalFiles(Activity activity, String fileName) {
        this.activity = activity;
        this.fileName = fileName;

        int permissionStatus = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionStatus == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_WRITE_STORAGE);
        }
    }

    public void saveExternalFile(List<String> list) {
        if (isExternalStorageWritable()) {
            File saveFile = new File(activity.getExternalFilesDir(null), fileName);
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(saveFile);
                for (String s : list) {
                    fileWriter.append(String.format("%s;", s));
                    Toast.makeText(activity, R.string.save_file, Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(activity, R.string.save_file_error, Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(activity, R.string.save_file_error, Toast.LENGTH_SHORT).show();
        }

    }

    public List<String> loadExternalFile() {
        List<String> list;
        File loadFile = new File(activity.getExternalFilesDir(null), fileName);
        try {
            Scanner scanner = new Scanner(loadFile);
            list = Arrays.asList(scanner.nextLine().split(";"));
        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}
