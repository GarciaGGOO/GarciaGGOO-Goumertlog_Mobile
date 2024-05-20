package com.example.goumertlog_mobile_new;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.goumertlog_mobile_new.models.Restaurante;
import com.example.goumertlog_mobile_new.recyclers.RestauranteAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MenuDeRestaurantesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Restaurante> restaurantes;
    RestauranteAdapter restauranteAdapter;
    FirebaseFirestore db;
    Button buttonFiltro;
    ImageButton buttonBuscar;
    FloatingActionButton buttonMenuUsuario;
    TextView textBusca;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_de_restaurantes);

        db = FirebaseFirestore.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        buttonFiltro = (Button) findViewById(R.id.buttonFiltro);
        buttonBuscar = (ImageButton) findViewById(R.id.buttonBuscar);
        buttonMenuUsuario = (FloatingActionButton) findViewById(R.id.buttonMenuUsuario);
        textBusca = (TextView) findViewById(R.id.textBusca);

        restaurantes = new ArrayList<>();

        buttonMenuUsuario.setOnClickListener(v -> {
            Intent intent = new
                    Intent(MenuDeRestaurantesActivity.this, MenuUsuarioActivity.class);
            startActivity(intent);
        });

        buttonFiltro.setOnClickListener(v -> {
            Intent intent = new
                    Intent(MenuDeRestaurantesActivity.this,FiltroRestScrollActivity.class);
            startActivity(intent);
        });

        buttonBuscar.setOnClickListener(v -> {
            String nomeRestaurante = textBusca.getText().toString().trim();
            buscarRestaurantesPorNome(nomeRestaurante);
        });
    }

    public void buscarRestaurantesPorNome(String nomeRestaurante) {
        // Array final para armazenar o restaurante procurado
        final Restaurante[] restauranteProcurado = new Restaurante[1];

        // Consulta no Firebase Firestore para buscar restaurantes com base no nome
        db.collection("restaurantes")
                .whereEqualTo("nomeRest", nomeRestaurante)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Restaurante restaurante = document.toObject(Restaurante.class);
                                restaurante.setId(document.getId());

                                // Verifica se o restaurante corresponde ao nome procurado
                                if (restaurante.getNomeRest().equals(nomeRestaurante)) {
                                    // Armazena o restaurante procurado no array
                                    restauranteProcurado[0] = restaurante;
                                } else {
                                    // Adiciona outros restaurantes à lista de resultados
                                    restaurantes.add(restaurante);
                                }
                            }

                            // Se houver um restaurante procurado, move-o para o topo da lista
                            if (restauranteProcurado[0] != null) {
                                restaurantes.remove(restauranteProcurado[0]); // Remove o restaurante da posição atual na lista
                                restaurantes.add(0, restauranteProcurado[0]); // Adiciona o restaurante procurado no topo da lista
                            }

                            restauranteAdapter.notifyDataSetChanged(); // Notifica o adaptador de que os dados foram alterados
                        } else {
                            // Se ocorrer um erro ao executar a consulta
                            // Exiba uma mensagem de erro ou trate conforme necessário
                        }
                    }
                });
    }


    protected void onResume(){
        super.onResume();
        buscarRestaurantes();
    }
    public void buscarRestaurantes(){
        restaurantes = new ArrayList<>();
        db.collection("restaurantes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Restaurante usu = document.toObject(Restaurante.class);
                        usu.setId(document.getId());
                        restaurantes.add(usu);
                    }
                    iniciarRecybler();
                }
            }
        });
    }

    public void iniciarRecybler(){
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);


        recyclerView.setLayoutManager(layout);
        restauranteAdapter = new RestauranteAdapter(restaurantes);
        recyclerView.setAdapter(restauranteAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}