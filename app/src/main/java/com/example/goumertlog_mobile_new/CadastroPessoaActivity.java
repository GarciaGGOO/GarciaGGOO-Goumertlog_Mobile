package com.example.goumertlog_mobile_new;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.goumertlog_mobile_new.models.Usuario;
import com.google.firebase.firestore.FirebaseFirestore;

public class CadastroPessoaActivity extends AppCompatActivity {

    private EditText textNome, textNumberCPF, textDateNascimento, textEmail, textConfirmeEmail, textPasswordSenha, textPasswordConfirmar;
    private Button buttonCadastrar;

    // URL da imagem padrão
    private static final String IMAGEM_PADRAO_URL = "https://w7.pngwing.com/pngs/1000/665/png-transparent-computer-icons-profile-s-free-angle-sphere-profile-cliparts-free.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);

        textNome = findViewById(R.id.textComentarios);
        textNumberCPF = findViewById(R.id.textNumberCPF);
        textDateNascimento = findViewById(R.id.textDateNascimento);
        textEmail = findViewById(R.id.textEmail);
        textConfirmeEmail = findViewById(R.id.textConfirmeEmail);
        textPasswordSenha = findViewById(R.id.textPasswordSenha);
        textPasswordConfirmar = findViewById(R.id.textPasswordConfirmar);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        String nome = textNome.getText().toString().trim();
        String cpf = textNumberCPF.getText().toString().trim();
        String dataNasc = textDateNascimento.getText().toString().trim();
        String email = textEmail.getText().toString().trim();
        String confirmarEmail = textConfirmeEmail.getText().toString().trim();
        String senha = textPasswordSenha.getText().toString().trim();
        String confirmarSenha = textPasswordConfirmar.getText().toString().trim();

        if (nome.isEmpty() || cpf.isEmpty() || dataNasc.isEmpty() || email.isEmpty() || confirmarEmail.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.equals(confirmarEmail)) {
            Toast.makeText(this, "Os e-mails não correspondem", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "As senhas não correspondem", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criação do objeto Usuario com a URL da imagem padrão
        Usuario usuario = new Usuario(cpf, dataNasc, email, nome, senha, IMAGEM_PADRAO_URL);

        FirebaseFirestore.getInstance().collection("usuarios")
                .add(usuario)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CadastroPessoaActivity.this, LoginActivity.class);
                    startActivity(intent);
                    // Aqui você pode adicionar qualquer ação após o sucesso, como redirecionar o usuário para outra tela
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erro ao cadastrar o usuário", Toast.LENGTH_SHORT).show();
                    // Aqui você pode tratar o erro, exibindo uma mensagem de erro para o usuário, por exemplo
                });
    }
}
