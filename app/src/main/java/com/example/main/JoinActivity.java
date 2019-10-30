package com.example.main;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {
    ImageButton btnDate;
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int day = cal.get(Calendar.DATE);
    EditText edtName, edtID, edtPW, edtPWCheck, edtNickName, edtEmail, edtDomain;
    Button btnCheck, btnJoin;
    TextView txtDate;
    String name, id, pw, nickname, date, email, gender;
    boolean bool_id = false, bool_pw = true;
    RadioGroup rgGender;
    RadioButton rgMale, rgFemale;
    ImageView imgError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_layout);

        btnDate = findViewById(R.id.btnDate);
        edtName = findViewById(R.id.edtName);
        edtID = findViewById(R.id.edtID);
        edtPW = findViewById(R.id.edtPW);
        edtPWCheck = findViewById(R.id.edtPWCheck);
        edtNickName = findViewById(R.id.edtNickName);
        edtEmail = findViewById(R.id.edtEmail);
        edtDomain = findViewById(R.id.edtDomain);
        btnCheck = findViewById(R.id.btnCheck);
        txtDate = findViewById(R.id.txtDate);
        rgGender = findViewById(R.id.rgGender);
        rgMale = findViewById(R.id.rgMale);
        rgFemale = findViewById(R.id.rgFemale);
        imgError = findViewById(R.id.imgError);
        name = edtName.getText().toString();
        btnJoin = findViewById(R.id.btnJoin);


//        edtPWCheck.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                pw = edtPW.getText().toString();
//
//                if (!hasFocus) {
//                    if (pw == edtPWCheck.getText().toString()) {
//                        bool_pw = true;
//                        imgError.setVisibility(View.INVISIBLE);
//                    } else {
//                        bool_pw = false;
//                        edtPWCheck.setHint("비밀번호를 입력해주세요");
//                        edtPWCheck.setHintTextColor(Color.rgb(204, 61, 61));
//                        imgError.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        });
        edtPWCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pw = edtPW.getText().toString();
                if (pw.equals(edtPWCheck.getText().toString())) {
                    bool_pw = true;

                } else {
                    edtPWCheck.setHint("비밀번호를 입력해주세요");
                    edtPWCheck.setHintTextColor(Color.rgb(204, 61, 61));
                    bool_pw = false;
                }
                if (bool_pw) imgError.setVisibility(View.INVISIBLE);
                else imgError.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                pw = edtPW.getText().toString();
                if (pw.equals( edtPWCheck.getText().toString())) {
                    bool_pw = true;
                    imgError.setVisibility(View.INVISIBLE);
                } else {
                    bool_pw = false;
                    edtPWCheck.setHint("비밀번호를 입력해주세요");
                    edtPWCheck.setHintTextColor(Color.rgb(204, 61, 61));
                    imgError.setVisibility(View.VISIBLE);
                }
            }
        });

        //TODO 성별선택
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rgMale.isChecked()) {
                    gender = "남";
                } else if (rgFemale.isChecked()) gender = "여";

            }
        });


        //TODO 아이디 중복체크
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = edtID.getText().toString();
                Call<ResponseRepeat> res = Net.getInstance().getApi().getRepeat(id);
                res.enqueue(new Callback<ResponseRepeat>() {
                    @Override
                    public void onResponse(Call<ResponseRepeat> call, Response<ResponseRepeat> response) {
                        if (response.isSuccessful()) {
                            ResponseRepeat responseGet = response.body();
                            if (responseGet.getRepeat() == false) {
                                Toast.makeText(JoinActivity.this, "중복된 아이디입니다.", Toast.LENGTH_LONG).show();
                            } else {
                                bool_id = true;
                                final String Id = id;
                                Toast.makeText(JoinActivity.this, "사용가능한 아이디입니다.", Toast.LENGTH_LONG).show();
                            }
                        } else
                            Toast.makeText(JoinActivity.this, "통신1 에러", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseRepeat> call, Throwable t) {
                        Toast.makeText(JoinActivity.this, "통신3 에러", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        //TODO 날짜 선택
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateDialog = new DatePickerDialog(JoinActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Toast.makeText(JoinActivity.this, year + "/" + (month + 1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
                        cal.set(year, month, dayOfMonth);
                        txtDate.setText(year + "/" + (month + 1) + "/" + dayOfMonth + " ~ ing♥");
                        date = year + "-" + (month + 1) + "-" + dayOfMonth;
                    }
                }, year, month, day);
                dateDialog.show();
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bool_id && bool_pw && !edtName.equals(null) && !edtEmail.equals(null) && !edtDomain.equals(null)) {
                    String api_name = edtName.getText().toString();
                    String api_id = edtID.getText().toString();
                    String api_pw = edtPW.getText().toString();
                    String api_date = date;
                    String api_gender = gender;
                    String api_nick = edtNickName.getText().toString();
                    String api_email = edtEmail.getText().toString() + "@" + edtDomain.getText().toString();

                    if(!api_name.equals(null)&&!api_id.equals(null)&&!api_pw.equals(null)&&!api_date.equals(null)&&!api_gender.equals(null)&&!api_nick.equals(null)&&!api_email.equals(null)){
                        Call<ResponseJoin> res = Net.getInstance().getApi().getJoin(api_name, api_id, api_pw,api_nick,api_date, api_gender ,  api_email);
                        res.enqueue(new Callback<ResponseJoin>() {
                            @Override
                            public void onResponse(Call<ResponseJoin> call, Response<ResponseJoin> response) {
                                if(response.isSuccessful()){
                                    ResponseJoin responseJoin=response.body();
                                    if(responseJoin.getJoin()){
                                        Toast.makeText(JoinActivity.this,"회원가입에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(JoinActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseJoin> call, Throwable t) {
                                Toast.makeText(JoinActivity.this,"회원가입에 실패하였습니다.",Toast.LENGTH_SHORT).show();

                            }
                        });
                    }else{
                        Toast.makeText(JoinActivity.this,"입력되지 않은 정보가 있습니다.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(JoinActivity.this,"잘못된 정보가 입력되었습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
