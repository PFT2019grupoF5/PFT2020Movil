package com.utec.pft202002;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.Enum.tipoPerfil;
import com.utec.pft202002.model.Perfil;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.PerfilService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {

    PerfilService perfilService;

    EditText edtPerfilId;
    EditText edtPerfilTipoPerfil;
    Button btnSave;
    Button btnDel;
    TextView txtPerfilId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        setTitle("Perfiles");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtPerfilId = (TextView) findViewById(R.id.txtPerfilId);
        edtPerfilId = (EditText) findViewById(R.id.edtPerfilId);
        edtPerfilTipoPerfil = (EditText) findViewById(R.id.edtPerfilTipoPerfil);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);

        perfilService = APIUtils.getPerfilService();

        Bundle extras = getIntent().getExtras();
        final String perfilId = extras.getString("perfil_id");
        String perfilTipoPerfil = extras.getString("perfil_tipoperfil");

        edtPerfilId.setText(perfilId);
        edtPerfilTipoPerfil.setText(perfilTipoPerfil);

        if(perfilId != null && perfilId.trim().length() > 0 ){
            edtPerfilId.setFocusable(false);
        } else {
            txtPerfilId.setVisibility(View.INVISIBLE);
            edtPerfilId.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Perfil u = new Perfil();
                u.setTipoPerfil(tipoPerfil.valueOf(edtPerfilTipoPerfil.getText().toString()));

                if(perfilId != null && perfilId.trim().length() > 0){
                    //update perfil
                    updatePerfil(Long.parseLong(perfilId), u);
                } else {
                    //add perfil
                    addPerfil(u);
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePerfil(Long.parseLong(perfilId));

                Intent intent = new Intent(PerfilActivity.this, PerfilMainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void addPerfil(Perfil u){
        Call<Perfil> call = perfilService.addPerfil(u);
        call.enqueue(new Callback<Perfil>() {
            @Override
            public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PerfilActivity.this, "Perfil creada ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Perfil> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }


    public void updatePerfil(Long id, Perfil u){
        Call<Perfil> call = perfilService.updatePerfil(id, u);
        call.enqueue(new Callback<Perfil>() {
            @Override
            public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PerfilActivity.this, "Perfil modificada ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Perfil> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void deletePerfil(Long id){
        Call<Perfil> call = perfilService.deletePerfil(id);
        call.enqueue(new Callback<Perfil>() {
            @Override
            public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PerfilActivity.this, "Perfil borrada ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Perfil> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
}
