package com.example.goumertlog_mobile_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ApresentacaoActivity extends AppCompatActivity {

    Button buttonCadastroRest, buttonLogin, buttonMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apresentacao);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonMenu = (Button) findViewById(R.id.buttonMenu);
        buttonCadastroRest = (Button) findViewById(R.id.buttonCadastroRest);

        buttonLogin.setOnClickListener(v -> {
            Intent intent = new
                    Intent(ApresentacaoActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        buttonMenu.setOnClickListener(v -> {
            Intent intent = new
                    Intent(ApresentacaoActivity.this, MenuDeRestaurantesActivity.class);
            startActivity(intent);
        });
    }
}