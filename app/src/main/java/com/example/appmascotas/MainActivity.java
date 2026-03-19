package com.example.appmascotas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnActivityRegistrar, btnActivityListar, btnListarRecycler;

    private void loadUI(){
        btnActivityRegistrar = findViewById(R.id.btnActivityRegistrar);
        btnActivityListar = findViewById(R.id.btnActivityListar);
        btnListarRecycler = findViewById(R.id.btnListarRecycler);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadUI();
        btnActivityRegistrar.setOnClickListener((v) -> {startActivity(new Intent(getApplicationContext(), Registrar.class));});
        btnActivityListar.setOnClickListener((v -> {startActivity(new Intent(getApplicationContext(), Listar.class));}));
        btnListarRecycler.setOnClickListener((v) -> {startActivity(new Intent(getApplicationContext(), ListarCustom.class));});
    }//on crate
}