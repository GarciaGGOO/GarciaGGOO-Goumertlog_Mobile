package com.example.goumertlog_mobile_new.recyclers;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goumertlog_mobile_new.MenuDeRestaurantesActivity;
import com.example.goumertlog_mobile_new.R;
import com.example.goumertlog_mobile_new.ReservaActivity;
import com.example.goumertlog_mobile_new.models.Restaurante;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class RestauranteAdapter extends RecyclerView.Adapter<RestauranteHolder> {

    FirebaseFirestore db;
    public static ArrayList<Restaurante> restaurantes;

    public RestauranteAdapter(ArrayList<Restaurante> restaurantes) {this.restaurantes = restaurantes;}

    @NonNull
    @Override
    public RestauranteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestauranteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_rest, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RestauranteHolder holder, int position) {
        Restaurante restaurante = restaurantes.get(position);
        holder.textNomeRest.setText(restaurante.getNomeRest());
        holder.textDescricao.setText(restaurante.getDescricao());

        new Thread(() -> {
            URL url = null;
            try {
                url = new URL(restaurante.getImagem());
                final Bitmap bmp;
                bmp = BitmapFactory
                        .decodeStream(url.openConnection()
                                .getInputStream());
                new Handler(Looper.getMainLooper()).post(() ->
                { holder.imageViewFoto.setImageBitmap(bmp);
                });
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        holder.buttonSaibaMais.setOnClickListener(v -> {
            Intent intent = new
                    Intent(v.getContext(), ReservaActivity.class);
            intent.putExtra("id", restaurante.getId());
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return restaurantes != null ? restaurantes.size() : 0;
    }
}
