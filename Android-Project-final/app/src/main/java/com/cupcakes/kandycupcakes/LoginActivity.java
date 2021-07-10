package com.cupcakes.kandycupcakes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cupcakes.kandycupcakes.IT19152110.customerView;
import com.cupcakes.kandycupcakes.IT19210698.availablevehicles;

public class LoginActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        drawerLayout = findViewById(R.id.navdraw3);

        final   EditText  username =(EditText) findViewById(R.id.txtusername);
        final EditText  pasword = (EditText)findViewById(R.id.txtpassword);

        Button log = findViewById(R.id.logbtn);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String user = username.getText().toString();

                final String p = pasword.getText().toString();




                    validate(user,p);


        }


    });
    }

    public void validate(String username, String password) {

        if ((username.equals("cup")) && (password.equals("1234"))) {

            Intent i = new Intent(LoginActivity.this, Admin.class);
            startActivity(i);

        }
        else if(!username.equals("")&&!username.equals("cup")){
            Toast.makeText(LoginActivity.this, "Please enter correct username", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals("")&&!password.equals("1234")){
            Toast.makeText(LoginActivity.this, "Please enter correct password", Toast.LENGTH_SHORT).show();
        }
        else if(!username.equals("")&&!username.equals("cup")&&!password.equals("")&&!password.equals("cup")){
            Toast.makeText(LoginActivity.this, "Please enter correct username and password", Toast.LENGTH_SHORT).show();
        }

        else if(!username.equals("")&&username.equals("cup")&&password.equals("")){
            Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
        }

        else {
                Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();

        }
    }



    //nav Drawer

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickClose(View view){
        closeDrawer(drawerLayout);
    }

    private void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void Clickbook(View view){
        Intent intent = new Intent(LoginActivity.this, availablevehicles.class);
        startActivity(intent);
    }

    public void ClickAddFaq(View view){
        Intent intent = new Intent(LoginActivity.this, customerView.class);
        startActivity(intent);
    }

    public void adminlogin(View view){
        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}