package com.example.Apk_UjianQuiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Apk_UjianQuiz.model.QuizModel;
import com.example.Apk_UjianQuiz.service.APIClient;
import com.example.Apk_UjianQuiz.service.APIInterfacesRest;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class question_activity extends AppCompatActivity implements View.OnClickListener {

    TextView txtQuestion, txtSkor;
    Button btnA, btnB, btnC, btnD;
    ImageView imgView;
    RadioGroup rdGroup;

    int i = 0;
    int sizeAbc;
    ArrayList<String> total = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_activity);

        btnA = findViewById(R.id.btn_A);
        btnB = findViewById(R.id.btn_B);
        btnC = findViewById(R.id.btn_C);
        btnD = findViewById(R.id.btn_D);
        txtSkor = findViewById(R.id.txtScor);
        txtQuestion = findViewById(R.id.txt_Question);
        imgView = findViewById(R.id.gambar1);
        rdGroup = findViewById(R.id.radiogrup);

        btnA.setId(Integer.parseInt("1"));
        btnB.setId(Integer.parseInt("2"));
        btnC.setId(Integer.parseInt("3"));
        btnD.setId(Integer.parseInt("4"));

        callJawaban();

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
    }

    APIInterfacesRest apiInterface;
    ProgressDialog progressDialog;
    public void callJawaban(){

        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        progressDialog = new ProgressDialog(question_activity.this);
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
                    sizeAbc = data.getData().getSoalQuizAndroid().size();
                    if (i < sizeAbc) {
                        if (data.getData().getSoalQuizAndroid().get(i).getJenisPertanyaan().equalsIgnoreCase("gambar")) {
                            btnA.setText(data.getData().getSoalQuizAndroid().get(i).getA());
                            btnB.setText(data.getData().getSoalQuizAndroid().get(i).getB());
                            btnC.setText(data.getData().getSoalQuizAndroid().get(i).getC());
                            btnD.setText(data.getData().getSoalQuizAndroid().get(i).getD());
                            txtQuestion.setText(data.getData().getSoalQuizAndroid().get(i).getPertanyaan());
                            txtSkor.setText(data.getData().getSoalQuizAndroid().get(i).getPoint());
                            String gambar = data.getData().getSoalQuizAndroid().get(i).getGambar();
                            Picasso.get().load(gambar).into(imgView);


                        }
                        else {
                            btnA.setText(data.getData().getSoalQuizAndroid().get(i).getA());
                            btnB.setText(data.getData().getSoalQuizAndroid().get(i).getB());
                            btnC.setText(data.getData().getSoalQuizAndroid().get(i).getC());
                            btnD.setText(data.getData().getSoalQuizAndroid().get(i).getD());
                            txtQuestion.setText(data.getData().getSoalQuizAndroid().get(i).getPertanyaan());
                            txtSkor.setText(data.getData().getSoalQuizAndroid().get(i).getPoint());
                            String gambar = data.getData().getSoalQuizAndroid().get(i).getGambar();
                            Picasso.get().load(gambar).into(imgView);

                        /*if (x == array) {

                            int jawaban1 = Integer.parseInt(data.getData().getSoalQuizAndroid().get(0).getPoint());
                            int jawaban2 = Integer.parseInt(data.getData().getSoalQuizAndroid().get(1).getPoint());
                            int jawaban3 = Integer.parseInt(data.getData().getSoalQuizAndroid().get(2).getPoint());
                            hasil = String.valueOf( jawaban1 + jawaban2 + jawaban3);

                           // Toast.makeText(MainActivity.this, hasil, Toast.LENGTH_LONG).show();

                           Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                           intent.putExtra("hasil", hasil);
                           startActivity(intent);
                        }*/
                        }
                        i++;
                    }
                }else

                {

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(question_activity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(question_activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View v) {
        total.add(String.valueOf(v.getId()));
        Toast.makeText(question_activity.this, String.valueOf(total),Toast.LENGTH_LONG).show();
        callJawaban();
        if (i == 3) {
            Intent intent = new Intent(question_activity.this, Score.class);
            intent.putStringArrayListExtra("hasil",total);
            startActivity(intent);
            finish();
        }
    }
}
