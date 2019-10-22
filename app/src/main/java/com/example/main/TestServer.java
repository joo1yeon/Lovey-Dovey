//package com.example.main;
//
//import android.app.Activity;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//
//public class TestServer extends AppCompatActivity implements View.OnClickListener {
//    private Button image, video, pdf;
//
//    @Override
//    protected void onCreate(Bundle savedInstance) {
//        super.onCreate(savedInstance);
//        setContentView(R.layout.test_server);
//
//        image = findViewById(R.id.image);
//        video = findViewById(R.id.video);
//        pdf = findViewById(R.id.pdf);
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            image.setEnabled(false);
//            video.setEnabled(false);
//            pdf.setEnabled(false);
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, );
//        } else {
//            image.setEnabled(true);
//            video.setEnabled(true);
//            pdf.setEnabled(true);
//        }
//        image.setOnClickListener(this);
//        video.setOnClickListener(this);
//        pdf.setOnClickListener(this);
//    }
//
//    @Override
//    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == 0) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
//            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                image.setEnabled(true);
//                video.setEnabled(true);
//                pdf.setEnabled(true);
//            }
//        }
//    }
//}
