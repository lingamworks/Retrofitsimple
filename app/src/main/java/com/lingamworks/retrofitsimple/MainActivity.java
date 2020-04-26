package com.lingamworks.retrofitsimple;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {
    Retrofit retrofit;
    List<studentslist> studentnamelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.114/afterschool/")
                .addConverterFactory(GsonConverterFactory.create())//mention which conveter we are using for fetch
                .build();
        Api_Student_Add service=retrofit.create(Api_Student_Add.class);
        Api_Student_fetch service1=retrofit.create(Api_Student_fetch.class);
        Call<ResponseBody> call= service.addstudent("reg","student1","male","1998-01-17","parent1","phone1","parent2","phone2");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String message = response.body().string();
                    Log.d("fetch response", message);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String message=t.getMessage();
                Log.d("fetch response", message);
            }
        });

        Call<List<studentslist>> call1= service1.getactitivitesJson("list");
        call1.enqueue(new Callback<List<studentslist>>() {
            @Override
            public void onResponse(Call<List<studentslist>> call, Response<List<studentslist>> response) {
                    studentnamelist=new ArrayList<>(response.body());
                    studentslist getvalues=studentnamelist.get(studentnamelist.size()-1);
                    Gson gson = new Gson();
                    Log.d("send response", getvalues.getName()+"" +getvalues.studentid);
            }
            @Override
            public void onFailure(Call<List<studentslist>> call, Throwable t) {
                String message=t.getMessage();
                Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();

            }
        });
    }
// without using data class
public interface Api_Student_Add {
    @FormUrlEncoded
    @POST("student_reg.php")
    Call<ResponseBody> addstudent(// its single respose list is not required
            @Field("action") String action,
            @Field("name") String name,
            @Field("gender") String gender,
            @Field("dob") String dob,
            @Field("parent1") String parent1,
            @Field("phone1") String phone1,
            @Field("parent2") String parent2,
            @Field("phone2") String phone2
    );
}
    public interface Api_Student_fetch {
        @FormUrlEncoded
        @POST("student_reg.php")
        Call<List<studentslist>> getactitivitesJson(@Field("action") String action);
    }


    //required to fetch list of values
    class studentslist {
        @SerializedName("studentid")
        @Expose
        private String studentid;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("parent1")
        @Expose
        private String parent1;
        @SerializedName("phone1")
        @Expose
        private String phone1;
        @SerializedName("parent2")
        @Expose
        private String parent2;
        @SerializedName("phone2")
        @Expose
        private String phone2;

        public String getStudentid() {
            return studentid;
        }

        public void setStudentid(String studentid) {
            this.studentid = studentid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getParent1() {
            return parent1;
        }

        public void setParent1(String parent1) {
            this.parent1 = parent1;
        }

        public String getPhone1() {
            return phone1;
        }

        public void setPhone1(String phone1) {
            this.phone1 = phone1;
        }

        public String getParent2() {
            return parent2;
        }

        public void setParent2(String parent2) {
            this.parent2 = parent2;
        }

        public String getPhone2() {
            return phone2;
        }

        public void setPhone2(String phone2) {
            this.phone2 = phone2;
        }
    }

}