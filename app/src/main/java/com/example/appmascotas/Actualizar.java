package com.example.appmascotas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

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

public class Actualizar extends AppCompatActivity {

    EditText edTipoA, edtNombreA, edtColoA, edtPesoA;
    Button btnActualizar;

    int idRecibido;
    RequestQueue requestQueue;

    private final String URL = "http://192.168.101.30:3000/mascotas";

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
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadUI();
        idRecibido = getIntent().getIntExtra("idMascota", -1 );
        if (idRecibido != -1) {
            buscarDatosMascota();
        }

        btnActualizar.setOnClickListener((v) -> {startActivity(new Intent(getApplicationContext(), Actualizar.class));});

    }

    private void buscarDatosMascota(){
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL + "/" + idRecibido,
                null,
                response -> {
                    try {
                        edTipoA.setText(response.getString("tipo"));
                        edtNombreA.setText(response.getString("nombre"));
                        edtColoA.setText(response.getString("color"));
                        edtPesoA.setText(String.valueOf(response.getDouble("pesokg")));
                    } catch (JSONException e) {
                        Log.e("ErrorGET", e.toString());
                    }
                },
                error -> Log.e("ErrorGET", error.toString())
        );
        requestQueue.add(request);
    }


}