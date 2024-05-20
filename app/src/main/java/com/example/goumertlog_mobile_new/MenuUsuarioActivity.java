package com.example.goumertlog_mobile_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.net.URL;

public class MenuUsuarioActivity extends AppCompatActivity {

    ImageView imageViewPerfil;
    TextView textViewNome;
    Button buttonPerfil, buttonMenu, buttonCadastro, buttonMaisInfo, buttonLogin;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);

        db = FirebaseFirestore.getInstance();

        buttonPerfil = (Button) findViewById(R.id.buttonPerfil);
        buttonMenu = (Button) findViewById(R.id.buttonMenu);
        buttonCadastro = (Button) findViewById(R.id.buttonCadastro);
        buttonMaisInfo = (Button) findViewById(R.id.buttonMaisInfo);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        textViewNome = (TextView) findViewById(R.id.textViewNome);
        imageViewPerfil = (ImageView) findViewById(R.id.imageViewPerfil);

        // Obtém o ID do usuário do intent
        String userId = getIntent().getStringExtra("userId");

        // Verifica se o userId está disponível
        if (userId != null) {
            // Consulta o documento do usuário pelo ID
            db.collection("usuarios").document(userId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // Obtém o nome e a URL da imagem do documento
                            String nome = documentSnapshot.getString("nome");
                            String imagemUrl = documentSnapshot.getString("imagem");

                            // Define o nome do usuário no textViewNome
                            textViewNome.setText(nome);

                            // Aqui você pode usar sua lógica para carregar a imagem
                            // utilizando a URL da imagem (imagemUrl) e definir no imageViewPerfil
                            // Exemplo: carregarImagem(imagemUrl);
                        } else {
                            // Usuário não encontrado
                            Toast.makeText(MenuUsuarioActivity.this, "Usuário não encontrado", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Trata falhas na obtenção do documento
                        Toast.makeText(MenuUsuarioActivity.this, "Erro ao obter usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            // Se userId for nulo, o usuário não está logado
            // Aqui você pode definir o texto padrão no textViewNome
            // e a imagem padrão no imageViewPerfil
        }

        buttonMenu.setOnClickListener(v -> {
            Intent intent = new
                    Intent(MenuUsuarioActivity.this, MenuDeRestaurantesActivity.class);
            startActivity(intent);
        });

        buttonCadastro.setOnClickListener(v -> {
            Intent intent = new
                    Intent(MenuUsuarioActivity.this, CadastroPessoaActivity.class);
            startActivity(intent);
        });

        buttonLogin.setOnClickListener(v -> {
            Intent intent = new
                    Intent(MenuUsuarioActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        buttonMaisInfo.setOnClickListener(v -> {
            Intent intent = new
                    Intent(MenuUsuarioActivity.this, ApresentacaoActivity.class);
            startActivity(intent);
        });
    }
    private void carregarImagem(String urlImagem) {
        new Thread(() -> {
            try {
                URL url = new URL(urlImagem);
                final Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                runOnUiThread(() -> imageViewPerfil.setImageBitmap(bmp));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}