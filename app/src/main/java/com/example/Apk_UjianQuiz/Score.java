package com.example.Apk_UjianQuiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Apk_UjianQuiz.model.QuizModel;
import com.example.Apk_UjianQuiz.service.APIClient;
import com.example.Apk_UjianQuiz.service.APIInterfacesRest;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Score extends AppCompatActivity {

    TextView txt_nilaiakhir;
    Button btn_selesai, btn_ulang;
    ArrayList<String> stock = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        txt_nilaiakhir = findViewById(R.id.txtNilaiakhir);
        btn_selesai = findViewById(R.id.btnSelesai);
        btn_ulang = findViewById(R.id.btnUlang);


        Intent arr = getIntent();
        stock = arr.getStringArrayListExtra("total");


        callQuiz();

        btn_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Score.this, "TERIMA KASIH", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        btn_ulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());

            }
        });

    }


    APIInterfacesRest apiInterface;
    ProgressDialog progressDialog;

    public void callQuiz() {

        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        progressDialog = new ProgressDialog(Score.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();
        Call<QuizModel> call3 = apiInterface.getModel();
        call3.enqueue(new Callback<QuizModel>() {
            @Override
            public void onResponse(Call<QuizModel> call, Response<QuizModel> response) {
                progressDialog.dismiss();
                QuizModel data = response.body();
                //Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();
                if (data != null) {


                    Integer poin = 0;
                    Integer nilai = 0;

                    for (int x = 0; x < data.getData().getSoalQuizAndroid().size(); x++) {

                        ArrayList<String> hope = new ArrayList<String>(3);
                        hope.add("4");
                        hope.add("1");
                        hope.add("1");

                        if (stock.get(x).equalsIgnoreCase(hope.get(x))) {
                            poin = Integer.parseInt(data.getData().getSoalQuizAndroid().get(x).getPoint());
                            nilai += poin;
                        }


                        txt_nilaiakhir.setText(String.valueOf(nilai));

                    }

                } else {

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(Score.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Score.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<QuizModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Maaf koneksi bermasalah", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });

    }
}
