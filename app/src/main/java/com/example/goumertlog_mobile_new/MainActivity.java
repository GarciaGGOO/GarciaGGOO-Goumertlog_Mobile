package com.example.goumertlog_mobile_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    Button buttonTelaLogin, buttonTelaCadastro, buttonTelaMenuRest;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        buttonTelaLogin = (Button) findViewById(R.id.buttonTelaLogin);
        buttonTelaCadastro = (Button) findViewById(R.id.buttonTelaCadastro);
        buttonTelaMenuRest = (Button) findViewById(R.id.buttonTelaMenuRest);

        buttonTelaLogin.setOnClickListener(v -> {
            Intent intent = new
                    Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        });

        buttonTelaCadastro.setOnClickListener(v -> {
            Intent intent = new
                    Intent(MainActivity.this,CadastroPessoaActivity.class);
            startActivity(intent);
        });

        buttonTelaMenuRest.setOnClickListener(v -> {
            Intent intent = new
                    Intent(MainActivity.this,MenuDeRestaurantesActivity.class);
            startActivity(intent);
        });
    }
}