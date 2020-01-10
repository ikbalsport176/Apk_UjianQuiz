package com.example.Apk_UjianQuiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Score extends AppCompatActivity {

    EditText txt_view,txt_nilaiakhir;
    Button btn_selesai, btn_ulang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


    }
}
