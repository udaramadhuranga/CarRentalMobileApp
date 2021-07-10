package com.cupcakes.kandycupcakes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cupcakes.kandycupcakes.IT19152110.kas;
import com.cupcakes.kandycupcakes.IT19207100.ImagesActivity;
import com.cupcakes.kandycupcakes.IT19210520.allpayment;

public class Admin extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    DrawerLayout drawerLayout;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin2);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();


        Button add = findViewById(R.id.addvehi);
        Button faq = findViewById(R.id.addfq);
        Button pay = findViewById(R.id.paymentbtn);
       LinearLayout toolbar = findViewById(R.id.toolbar);

        drawerLayout = findViewById(R.id.navdraw1);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(Admin.this, ImagesActivity.class);
                startActivity(in);
            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(Admin.this, kas.class);
                startActivity(in);
            }
        });


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(Admin.this, allpayment.class);
                startActivity(in);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        if(id== R.id.logout){

            Toast.makeText(getApplicationContext(),"You Logged Out",Toast.LENGTH_SHORT).show();

            Intent in = new Intent(Admin.this, LoginActivity.class);
            startActivity(in);

        }

        return super.onOptionsItemSelected(item);
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
        recreate();
    }

    public void Clickvehical(View view){
        Intent intent = new Intent(Admin.this, ImagesActivity.class);
        startActivity(intent);
    }

    public void ClickFaq(View view){
        Intent intent = new Intent(Admin.this, kas.class);
        startActivity(intent);
    }

    public void clickpayment(View view){
        Intent intent = new Intent(Admin.this, allpayment.class);
        startActivity(intent);
    }

    public void clicklogout(View view){
        Intent intent = new Intent(Admin.this, LoginActivity.class);
        startActivity(intent);
    }
    public void showpopup(View v){
        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id=menuItem.getItemId();

        if(id== R.id.logout){

            Toast.makeText(getApplicationContext(),"You Logged Out",Toast.LENGTH_SHORT).show();

            Intent in = new Intent(Admin.this, LoginActivity.class);
            startActivity(in);

        }

        return super.onOptionsItemSelected(menuItem);
    }
}