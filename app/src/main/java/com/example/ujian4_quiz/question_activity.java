package com.example.ujian4_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ujian4_quiz.model.QuizModel;
import com.example.ujian4_quiz.service.APIClient;
import com.example.ujian4_quiz.service.APIInterfacesRest;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class question_activity extends AppCompatActivity {

    TextView txt_Question,scoreView;
    Button btn_A,btn_B,btn_C,btn_D;
    ImageView gambar1;

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



                    txt_Question.setText(data.getData().getSoalQuizAndroid().get(0).getPertanyaan().toString());
                    btn_A.setText(data.getData().getSoalQuizAndroid().get(0).getA().toString());
                    btn_B.setText(data.getData().getSoalQuizAndroid().get(0).getB().toString());
                    btn_C.setText(data.getData().getSoalQuizAndroid().get(0).getC().toString());
                    btn_D.setText(data.getData().getSoalQuizAndroid().get(0).getD().toString());



                    String image = "http://dewabrata.com:80/dewa/uploads/soal_quiz_android/" + data.getData().getSoalQuizAndroid().get(0).getGambar()+"20200110101347-2020-01-10soal_quiz_android101342.jpg";
                    Picasso.get().load(image).into(gambar1);

                 /*   AdapterListSimple adapter = new AdapterListSimple(MainActivity.this,data.getData().getMoviedb());

                    lst_Movie.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    lst_Movie.setItemAnimator(new DefaultItemAnimator());
                    lst_Movie.setAdapter(adapter);

                     */





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
}
