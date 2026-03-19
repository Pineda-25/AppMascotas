package com.example.appmascotas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListarCustom extends AppCompatActivity implements  MascotaAdapter.OnAccionListener {

    RecyclerView recyclerMascotas;
    MascotaAdapter adapter;
    ArrayList<Mascota> listaMascotas;
    RequestQueue requestQueue;

    Button btnEditar, btnEliminar;

    private final String URL = "http://192.168.1.72:3000/mascotas";
    private void loadUI(){

        recyclerMascotas = findViewById(R.id.recyclerMascotas);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_custom);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadUI();

        //preparar lista y adapter antes de utlizar  WS
        listaMascotas = new ArrayList<>();
        adapter = new MascotaAdapter(this,listaMascotas, this);// impletar la definicon de clases...
        recyclerMascotas.setLayoutManager(new LinearLayoutManager(this));
        recyclerMascotas.setAdapter(adapter);

        //WS
        obtenerDatos();
    }

    private void obtenerDatos(){
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                jsonArray -> renderizarLista(jsonArray),
                error -> {
                    Log.e("ErrorWS", error.toString());
                    Toast.makeText(this, "No se obtuvieron los datos", Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
    private void renderizarLista(JSONArray jsonMascotas){
        //Con los datos obtenidos, cargamos la lista <Mascota> ya que esta
        //vinculada al MascotaAdapter > RecyclerView
        try {
            listaMascotas.clear();
            for(int i = 0; i<jsonMascotas.length();i++){
                //Tomamos un json a la vez utilizando su indice
                JSONObject json = jsonMascotas.getJSONObject(i);
                listaMascotas.add(new Mascota(
                        json.getInt("id"),
                        json.getString("tipo"),
                        json.getString("nombre"),
                        json.getString("color"),
                        json.getDouble("pesokg")

                ));
            } // fin for
            adapter.notifyDataSetChanged();
        } catch (Exception e){
            Log.e("ErrorJSON",e.toString());
        }
    }

    @Override
    public void onEditar(int position, Mascota mascota) {
        Toast.makeText(this, "Editando: " + mascota.getNombre(), Toast.LENGTH_SHORT).show();

        Intent envio = new Intent(this, Actualizar.class);

        // EMPACAMOS TODOS LOS DATOS
        envio.putExtra("idMascota", mascota.getId());
        envio.putExtra("tipo", mascota.getTipo());
        envio.putExtra("nombre", mascota.getNombre());
        envio.putExtra("color", mascota.getColor());
        envio.putExtra("peso", mascota.getPesokg());

        startActivity(envio);
    }

    @Override
    public void onEliminar(int position, Mascota mascota) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        obtenerDatos();
    }
}