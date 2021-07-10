package com.cupcakes.kandycupcakes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cupcakes.kandycupcakes.IT19152110.customerView;
import com.cupcakes.kandycupcakes.IT19210698.availablevehicles;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add=findViewById(R.id.viewfaq);
        Button login = findViewById(R.id.btnadminlogin);
        Button bookvehical = findViewById(R.id.book);
        Button pay = findViewById(R.id.paymentbtn);

        drawerLayout = findViewById(R.id.navdraw3);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(MainActivity.this, customerView.class);
                startActivity(in);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        bookvehical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, availablevehicles.class);
                startActivity(intent);
            }
        });

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
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void Clickbook(View view){
        Intent intent = new Intent(MainActivity.this, availablevehicles.class);
        startActivity(intent);
    }

    public void ClickAddFaq(View view){
        Intent intent = new Intent(MainActivity.this, customerView.class);
        startActivity(intent);
    }

    public void adminlogin(View view){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}