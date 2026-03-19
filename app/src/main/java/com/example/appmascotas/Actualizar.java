package com.example.appmascotas;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Actualizar extends AppCompatActivity {

    String tipo , nombre, color;
    double peso;

    EditText edTipoA, edtNombreA, edtColoA, edtPesoA;
    Button btnActualizar;

    int idRecibido;
    RequestQueue requestQueue;

    private final String URL = "http://192.168.1.72:3000/mascotas";

    private  void loadUI(){
        edTipoA = findViewById(R.id.edTipoA);
        edtNombreA = findViewById(R.id.edtNombreA);
        edtColoA = findViewById(R.id.edtColoA);
        edtPesoA = findViewById(R.id.edtPesoA);
        btnActualizar = findViewById(R.id.btnActualizar);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            return insets;
        });
        loadUI();

        idRecibido = getIntent().getIntExtra("idMascota", -1);

        if (idRecibido != -1) {
            edTipoA.setText(getIntent().getStringExtra("tipo"));
            edtNombreA.setText(getIntent().getStringExtra("nombre"));
            edtColoA.setText(getIntent().getStringExtra("color"));
            double pesoRecibido = getIntent().getDoubleExtra("peso", 0.0);
            edtPesoA.setText(String.valueOf(pesoRecibido));
        }

        btnActualizar.setOnClickListener(v -> {
            validarActualizacion();
        });
    }

    private void validarActualizacion() {
        if (edTipoA.getText().toString().isEmpty()) {
            edTipoA.setError("Complete con Perro , Gato");
            edTipoA.requestFocus();
            return;
        }

        if (edtNombreA.getText().toString().isEmpty()) {
            edtNombreA.setError("Escriba el nombre");
            edtNombreA.requestFocus();
            return;
        }

        if (edtColoA.getText().toString().isEmpty()) {
            edtColoA.setError("Este campo es obligatorio");
            edtColoA.requestFocus();
            return;
        }

        if (edtPesoA.getText().toString().isEmpty()) {
            edtPesoA.setError("Ingrese un valor");
            edtPesoA.requestFocus();
            return;
        }

        tipo = edTipoA.getText().toString().trim();
        nombre = edtNombreA.getText().toString().trim();
        color = edtColoA.getText().toString().trim();
        peso = Double.parseDouble(edtPesoA.getText().toString());

        if (!tipo.equals("Perro") && !tipo.equals("Gato")) {
            edTipoA.setError("Solo se permite: Perro , Gato");
            return;
        }

        if (peso < 0) {
            edtPesoA.setError("Solo se permiten valores positivos");
            edtPesoA.requestFocus();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mascotas");
        builder.setMessage("¿Seguro de actualizar los datos?");
        builder.setPositiveButton("Si", (dialog, which) -> {
            ejecutarPut();
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    // ENVIAR AL SERVIDOR
    private void ejecutarPut() {
        requestQueue = Volley.newRequestQueue(this);
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tipo", tipo);
            jsonObject.put("nombre", nombre);
            jsonObject.put("color", color);
            jsonObject.put("pesokg", peso);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.PUT,
                    URL + "/" + idRecibido,
                    jsonObject,
                    response -> {
                        Toast.makeText(this, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    },
                    error -> {
                        Log.e("ErrorWS", error.toString());
                        Toast.makeText(this, "No se pudo actualizar", Toast.LENGTH_SHORT).show();
                    }
            );
            requestQueue.add(jsonObjectRequest);

        } catch (Exception e) {
            Log.e("ErrorJSON", e.toString());
        }
    }
}