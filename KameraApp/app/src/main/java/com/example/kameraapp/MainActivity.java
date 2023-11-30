package com.example.kameraapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private ImageView iv;
    private String nmFile;
    ActivityResultLauncher<Intent> cameraStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {
                            prosesData(result.getData());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.cameraButton);
        iv = findViewById(R.id.resultImage);

        btn.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermission();
            }
            else{
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                File imageFolder =  new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "KameraApp");
                imageFolder.mkdirs();
                Date d = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy hh-mm-ss");
                nmFile = imageFolder + File.separator + dateFormat.format(d) + ".jpg";
                File image = new File(nmFile);
                Uri uriSavedImage = FileProvider.getUriForFile(MainActivity.this,  getPackageName()+".provider", image);
                // Comment this line below to disable Put Extra Method //
                it.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

                cameraStartForResult.launch(it);
            }

        });
    }

    protected void requestPermission(){
        String[] permission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permission, 1);
    }

    protected void prosesData(Intent data) throws IOException {
        Bitmap bm;

        // Save Photo to Storage (If not using Put Extra Method)//
        // Start of Manual Writing to Storage //
//        bm = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//        bm.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArray = stream.toByteArray(); // Convert camera photo to ByteArray
//
//        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "KameraApp");
//        dir.mkdirs();
//
//        File output = new File(dir, getFileName(".png"));
//        FileOutputStream fo = new FileOutputStream(output);
//        fo.write(byteArray);
//        fo.flush();
//        fo.close();
        // End of Manual Writing to Storage //

        // Save Photo to Storange (Using Put Extra Method) //
        // Start of Save Photo to Storage //
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        bm = BitmapFactory.decodeFile(nmFile, options);
        // End of Save Photo to Storage //

        iv.setImageBitmap(bm);
        Toast.makeText(this, "Data Telah Terload ke ImageView", Toast.LENGTH_SHORT).show();
    }

    protected String getFileName(String extension){
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy hh-mm-ss");
        return dateFormat.format(d)+extension;
    }
}