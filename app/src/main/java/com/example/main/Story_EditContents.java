package com.example.main;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Story_EditContents extends AppCompatActivity {
    final int GET_IMG_CODE = 30;
    Button showImg;
    String imagePath1;
    ImageView imgView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_edit_contents);

        showImg = findViewById(R.id.showimg);
        imgView = findViewById(R.id.imageView);

        showImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImages();
            }
        });
    }

    public void getImages() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GET_IMG_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        ArrayList<Uri> imageListUri;
        imageListUri = new ArrayList<Uri>();

        if (requestCode == GET_IMG_CODE) {
            if (data == null) {

            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    if (data.getClipData() == null) {
                        Toast.makeText(this, "다중선택이 불가능한 기기입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        ClipData clipData = data.getClipData();
                        Log.d("clipdata", String.valueOf(clipData.getItemCount()));

                        if (clipData.getItemCount() > 9) {
                            Toast.makeText(Story_EditContents.this, "사진은 9장 까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
                        } else if (clipData.getItemCount() == 1) {
                            imagePath1 = getPath(clipData.getItemAt(0).getUri());
                            File f1 = new File(imagePath1);
                            imgView.setAdjustViewBounds(true);
                            imgView.setImageURI(Uri.fromFile(f1));
                        } else if (clipData.getItemCount() > 1 && clipData.getItemCount() < 9) {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                Log.d("test", String.valueOf(clipData.getItemAt(i).getUri()));
                                imageListUri.add(clipData.getItemAt(i).getUri());
                            }
                            imagePath1 = getPath(imageListUri.get(0));
                            File f2 = new File(imagePath1);
                            imgView.setAdjustViewBounds(true);
                            imgView.setImageURI(Uri.fromFile(f2));

                        }
                    }
                }
            }
        }
    }

    private String getPath(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);

        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }
}
