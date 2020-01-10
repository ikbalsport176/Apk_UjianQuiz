package com.example.Apk_UjianQuiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Apk_UjianQuiz.model.QuizModel;
import com.example.Apk_UjianQuiz.service.APIClient;
import com.example.Apk_UjianQuiz.service.APIInterfacesRest;
import com.example.Apk_UjianQuiz.R;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class question_activity extends AppCompatActivity implements View.OnClickListener {

    TextView txt_Question,scoreView;
    Button btn_A,btn_B,btn_C,btn_D;
    ImageView gambar1;

    int sizeArr;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_activity);

        //TextView
        txt_Question = findViewById(R.id.txt_Question);
        scoreView = findViewById(R.id.scoreView);

        //Button
        btn_A = findViewById(R.id.btn_A);
        btn_B = findViewById(R.id.btn_B);
        btn_C = findViewById(R.id.btn_C);
        btn_D = findViewById(R.id.btn_D);

        //imageview
        gambar1 = findViewById(R.id.gambar1);

        btn_A.setOnClickListener(this);
        btn_B.setOnClickListener(this);
        btn_C.setOnClickListener(this);
        btn_D.setOnClickListener(this);
        callquiz();

    }




    APIInterfacesRest apiInterface;
    ProgressDialog progressDialog;
    public void callquiz(){

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

                if (data !=null) {
                    sizeArr = data.getData().getSoalQuizAndroid().size();

                    if (i < sizeArr){


                        txt_Question.setText(data.getData().getSoalQuizAndroid().get(i).getPertanyaan());
                    btn_A.setText(data.getData().getSoalQuizAndroid().get(i).getA());
                    btn_B.setText(data.getData().getSoalQuizAndroid().get(i).getB());
                    btn_C.setText(data.getData().getSoalQuizAndroid().get(i).getC());
                    btn_D.setText(data.getData().getSoalQuizAndroid().get(i).getD());


                    String image = data.getData().getSoalQuizAndroid().get(i).getGambar();
                    Picasso.get().load(image).into(gambar1);
                    }
                    else if( i == sizeArr){
                        Intent score = new Intent(question_activity.this, Score.class);
                        startActivity(score);
                }
                    i++;



                }else{

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
        callquiz();
    }
}
