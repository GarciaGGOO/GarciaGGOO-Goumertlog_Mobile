package com.example.goumertlog_mobile_new;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goumertlog_mobile_new.models.Restaurante;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;

public class ReservaActivity extends AppCompatActivity {
    String id;
    TextView textNome, textViewDescricao, editTextDate, editTextTime, textQtdConvidados, textComentarios;
    ImageView imageView;
    FirebaseFirestore db;
    Button buttonFotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        textNome = findViewById(R.id.textViewNome);
        imageView = findViewById(R.id.imageView);
        textQtdConvidados = findViewById(R.id.textQtdConvidados);
        textViewDescricao = findViewById(R.id.textViewDescricao);
        textComentarios = findViewById(R.id.textComentarios);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime); // Adicionado a inicialização de editTextTime
        buttonFotos = findViewById(R.id.buttonFotos);

        buttonFotos.setOnClickListener(v -> {
            Intent intent = new
                    Intent(ReservaActivity.this,FotosActivity.class);
            startActivity(intent);
        });

        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        db = FirebaseFirestore.getInstance();

        if (getIntent().getExtras() != null) {
            id = getIntent().getStringExtra("id");
            //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

            db.collection("restaurantes").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Restaurante restaurante = document.toObject(Restaurante.class);
                            if (restaurante != null) {
                                textNome.setText(restaurante.getNomeRest());
                                textViewDescricao.setText(restaurante.getDescricao());
                                carregarImagem(restaurante.getImagem());
                                // Configure outros elementos de layout com os detalhes do restaurante
                            }
                        } else {
                            Toast.makeText(ReservaActivity.this, "Restaurante não encontrado", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ReservaActivity.this, "Erro ao recuperar detalhes do restaurante", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        editTextDate.setOnClickListener(v -> showDatePickerDialog());
        editTextTime.setOnClickListener(v -> showCustomTimePickerDialog()); // Altere aqui para usar o método customizado

        Button buttonReservar = findViewById(R.id.buttonReservar);
        buttonReservar.setOnClickListener(v -> fazerReserva());
    }

    private void fazerReserva() {
        String nome = textNome.getText().toString().trim();
        String data = editTextDate.getText().toString().trim();
        String hora = editTextTime.getText().toString().trim();
        String qtdConvidados = textQtdConvidados.getText().toString().trim();
        String comentarios = textComentarios.getText().toString().trim();

        if (nome.isEmpty() || data.isEmpty() || hora.isEmpty() || qtdConvidados.isEmpty()) {
            Toast.makeText(this, "Todos os campos obrigatórios devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Se todos os campos obrigatórios estiverem preenchidos, faça a reserva
        // Aqui você pode adicionar a lógica para fazer a reserva no seu sistema

        // Exibir um Toast informando que a reserva está aguardando a confirmação do restaurante
        Toast.makeText(this, "Reserva feita com sucesso. Aguardando confirmação do restaurante.", Toast.LENGTH_SHORT).show();
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    editTextDate.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showCustomTimePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create an AlertDialog to hold the NumberPickers
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecionar Hora");

        // Create a LinearLayout to hold the NumberPickers
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create the NumberPicker for hours
        final NumberPicker hourPicker = new NumberPicker(this);
        hourPicker.setMinValue(11); // Start at 11
        hourPicker.setMaxValue(20); // End at 20
        hourPicker.setValue(hour);

        // Create the NumberPicker for minutes
        final NumberPicker minutePicker = new NumberPicker(this);
        minutePicker.setMinValue(0); // Start at 0
        minutePicker.setMaxValue(11); // End at 55 (11 * 5)
        String[] displayedValues = new String[12];
        for (int i = 0; i < 12; i++) {
            displayedValues[i] = String.format(Locale.getDefault(), "%02d", i * 5);
        }
        minutePicker.setDisplayedValues(displayedValues);
        minutePicker.setValue(minute / 5);

        // Add the NumberPickers to the layout
        layout.addView(hourPicker);
        layout.addView(minutePicker);

        // Set the layout as the dialog view
        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            int selectedHour = hourPicker.getValue();
            int selectedMinute = minutePicker.getValue() * 5;

            String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
            editTextTime.setText(selectedTime);
        });
        builder.setNegativeButton("Cancel", null);

        builder.show();
    }

    private void carregarImagem(String urlImagem) {
        new Thread(() -> {
            try {
                URL url = new URL(urlImagem);
                final Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                new Handler(Looper.getMainLooper()).post(() -> imageView.setImageBitmap(bmp));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
