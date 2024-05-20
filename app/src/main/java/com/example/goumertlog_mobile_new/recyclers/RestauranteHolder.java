package com.example.goumertlog_mobile_new.recyclers;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.goumertlog_mobile_new.R;

public class RestauranteHolder extends RecyclerView.ViewHolder {

    protected TextView textNomeRest, textDescricao;
    protected Button buttonSaibaMais;
    ImageView imageViewFoto;

    public RestauranteHolder(View itemView) {
        super(itemView);
        textNomeRest = (TextView) itemView.findViewById(R.id.textComentarios);
        textDescricao = (TextView) itemView.findViewById(R.id.textDescricao);
        buttonSaibaMais = (Button) itemView.findViewById(R.id.buttonSaibaMais);
        imageViewFoto = (ImageView) itemView.findViewById(R.id.imageViewFoto);
    }
}
