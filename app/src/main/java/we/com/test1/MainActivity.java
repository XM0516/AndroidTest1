package we.com.test1;

import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText name;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //此两行强制主线程使用网络
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //此两行强制主线程使用网络

        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.buttonLogin);
        name= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.pwd);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean b=checkUser(name.getText().toString(),password.getText().toString());
                if (b){
                    Toast.makeText(MainActivity.this,"login success",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this,"login fail",Toast.LENGTH_LONG).show();
                }
            }
        });
    }//onCreate over

    public boolean checkUser(String name,String psd){
        LoginToServer httpClientToServer=new LoginToServer();
        String response=httpClientToServer.doGet(name,psd);
        if ("true".equals(response)){
            return true;
        }else {return false;}
    }// checkUser over

}
