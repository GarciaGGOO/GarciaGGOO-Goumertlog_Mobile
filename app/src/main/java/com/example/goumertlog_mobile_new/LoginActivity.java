package com.example.goumertlog_mobile_new;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    EditText textEmail, editTextPassword;
    Button buttonEntrar, buttonEsqueceuSenha, buttonNovaConta;
    private String userId;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textEmail = findViewById(R.id.textEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonEntrar = findViewById(R.id.buttonEntrar);
        buttonNovaConta = findViewById(R.id.buttonNovaConta);

        db = FirebaseFirestore.getInstance();

        buttonEntrar.setOnClickListener(v -> loginUser());
        buttonNovaConta.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, CadastroPessoaActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = textEmail.getText().toString();
        String senha = editTextPassword.getText().toString();

        db.collection("usuarios").whereEqualTo("email", email).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (!task.getResult().isEmpty()) {
                    DocumentSnapshot document = task.getResult().getDocuments().get(0);
                    String senhaArmazenada = document.getString("senha");
                    if (senha.equals(senhaArmazenada)) {
                        Toast.makeText(LoginActivity.this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();
                        String userId = document.getId();
                        Intent intent = new Intent(LoginActivity.this, MenuUsuarioActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Senha incorreta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Usuário não encontrado", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Erro ao buscar usuário", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
