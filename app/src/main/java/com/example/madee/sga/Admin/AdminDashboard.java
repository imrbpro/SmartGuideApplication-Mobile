package com.example.madee.sga.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.madee.sga.Admin.Shop.Shop;
import com.example.madee.sga.R;

public class AdminDashboard extends AppCompatActivity {
    public Button btnShop, btnBrand, btnProduct, btnPromotion, btnEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnShop = findViewById(R.id.btnShopActivity);
        btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GotoShop = new Intent(AdminDashboard.this, Shop.class);
                startActivity(GotoShop);
            }
        });
    }


}
