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

    ArrayList<String> hasil = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        btn_selesai = findViewById(R.id.btnSelesai);
        btn_ulang = findViewById(R.id.btnUlang);
        txt_nilaiakhir = findViewById(R.id.txtNilaiAkhir);

        Intent arr = getIntent();
        hasil = arr.getStringArrayListExtra("hasil");
        callSoal();

        /*String txthasil = getIntent().getStringExtra("hasil");
        txtSkor.setText(txthasil);

         */
        btn_ulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Score.this, question_activity.class);
                startActivity(intent);
            }
        });

        btn_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    APIInterfacesRest apiInterface;
    ProgressDialog progressDialog;
    public void callSoal(){

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
                if (data !=null) {

                    Integer point=0;
                    Integer nilai=0;

                    for (int x = 0;x<data.getData().getSoalQuizAndroid().size();x++){
                        ArrayList<String> Add = new ArrayList<>(3);
                        Add.add("4");
                        Add.add("1");
                        Add.add("1");

                        if(hasil.get(x).equalsIgnoreCase(Add.get(x))){
                            point = Integer.parseInt(data.getData().getSoalQuizAndroid().get(x).getPoint());
                            nilai += point;
                        }
                        txt_nilaiakhir.setText(String.valueOf(nilai));
                    }

                }else{

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
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }
}

