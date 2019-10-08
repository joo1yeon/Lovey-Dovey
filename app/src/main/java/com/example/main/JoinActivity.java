package com.example.main;

import android.app.DatePickerDialog;
import android.graphics.Color;
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
    Button btnCheck;
    TextView txtDate;
    String name, id, pw, nickname, date, email, gender;
    boolean login = false;
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


        edtPWCheck.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                pw = edtPW.getText().toString();

                if (!hasFocus) {
                    if (pw == edtPWCheck.getText().toString()) {
                        login = true;
                        imgError.setVisibility(View.INVISIBLE);
                    } else {
                        edtPWCheck.setHint("비밀번호를 입력해주세요");
                        edtPWCheck.setHintTextColor(Color.rgb(204, 61, 61));
                        imgError.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        edtPWCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pw = edtPW.getText().toString();
                boolean pwcheck=false;
                if (pw .equals( edtPWCheck.getText().toString())) {
                    login = true;
                    pwcheck=true;
                    Log.d("PPP", "비밀번호1 : " + pw);
                    Log.d("PPP", "비밀번호 확인1 : " + edtPWCheck.getText().toString());

                } else {
                    edtPWCheck.setHint("비밀번호를 입력해주세요");
                    edtPWCheck.setHintTextColor(Color.rgb(204, 61, 61));
                    pwcheck=false;
                    Log.d("PPP", "비밀번호 2: " + pw);
                    Log.d("PPP", "비밀번호 확인 2: " + edtPWCheck.getText().toString());

                }
                if(pwcheck)imgError.setVisibility(View.INVISIBLE);
                else imgError.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                pw = edtPW.getText().toString();
                if (pw == edtPWCheck.getText().toString()) {
                    login = true;
                    imgError.setVisibility(View.INVISIBLE);
                } else {
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
                                login = true;
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
                        txtDate.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                    }
                }, year, month, day);
                dateDialog.show();
            }
        });
    }
}
