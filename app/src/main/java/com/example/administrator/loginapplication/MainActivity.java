package com.example.administrator.loginapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences register_sp;
    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRegister(View view) {
        EditText edt_username = (EditText) findViewById(R.id.UN_tf);
        EditText edt_password = (EditText) findViewById(R.id.PW_tf);
        username = edt_username.getText().toString();
        password = edt_password.getText().toString();
        if(username.trim().equals("") || password.trim().equals("") ){
            Toast.makeText(MainActivity.this, "The username or password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Dialog dialog=new AlertDialog.Builder(MainActivity.this)
                .setTitle("register")
                .setMessage("Are you sure about the registration information？")
                .setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        register_sp=getSharedPreferences("userInfo",MODE_PRIVATE);
                        SharedPreferences.Editor edit=register_sp.edit();
                        edit.putString("username", username);
                        edit.putString("password", password);
                        edit.commit();

                        Toast.makeText(MainActivity.this, "Congratulations, your registration has been successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                }).create();//
        dialog.show();

    }

    public void onLogin(View view) {
        new DownloadUpdate().execute();
    }


    private class DownloadUpdate extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... params) {
            try {
                SharedPreferences register_sp;
                String input_username;
                String input_psw;
                EditText edt_username;
                EditText edt_password;

                edt_username = (EditText) findViewById(R.id.UN_tf);
                edt_password = (EditText) findViewById(R.id.PW_tf);
                input_username = edt_username.getText().toString();
                input_psw = edt_password.getText().toString();

                register_sp = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
                String savedUsername = register_sp.getString("username", "");
                String savedPassword = register_sp.getString("password", "");
                //查看输入的密码和名字是否一致

                if (input_username.trim().equals(savedUsername) && input_psw.trim().equals(savedPassword)) {

                    return true;

                } else {

                    return false;
                }
            }catch (Exception e){return false;}
        }

        @Override
        protected void onPostExecute(Boolean result) {
          if(result)
          {
              Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
          }
          else{
              Toast.makeText(MainActivity.this, "The user name or password is wrong, please confirm the information or register", Toast.LENGTH_SHORT).show();
          }
        }


    }
}
