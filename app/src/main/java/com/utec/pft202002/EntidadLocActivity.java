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

import com.utec.pft202002.remote.CiudadService;
import com.utec.pft202002.model.EntidadLoc;
import com.utec.pft202002.remote.EntidadLocService;
import com.utec.pft202002.Enum.tipoLoc;
import com.utec.pft202002.remote.APIUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntidadLocActivity extends AppCompatActivity {

    CiudadService ciudadService;
    EntidadLocService entidadLocService;

    EditText edtEntidadLocId;
    EditText edtEntidadLocCodigo;
    EditText edtEntidadLocNombre;
    EditText edtEntidadLocDireccion;
    EditText edtEntidadLocTipoLoc;
    EditText edtEntidadLocCiudadId;
    Button btnSave;
    Button btnDel;
    TextView txtEntidadLocId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entidadloc);

        setTitle("EntidadesLoc");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtEntidadLocId = (TextView) findViewById(R.id.txtEntidadLocId);
        edtEntidadLocId = (EditText) findViewById(R.id.edtEntidadLocId);
        edtEntidadLocCodigo = (EditText) findViewById(R.id.edtEntidadLocCodigo);
        edtEntidadLocNombre = (EditText) findViewById(R.id.edtEntidadLocNombre);
        edtEntidadLocDireccion = (EditText) findViewById(R.id.edtEntidadLocDireccion);
        edtEntidadLocTipoLoc = (EditText) findViewById(R.id.edtEntidadLocTipoLoc);
        edtEntidadLocCiudadId = (EditText) findViewById(R.id.edtEntidadLocCiudadId);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);

        entidadLocService = APIUtils.getEntidadLocService();

        Bundle extras = getIntent().getExtras();
        final String entidadLocId = extras.getString("entidadloc_id");
        String entidadLocCodigo = extras.getString("entidadloc_codigo");
        String entidadLocNombre = extras.getString("entidadloc_nombre");
        String entidadLocDireccion = extras.getString("entidadloc_direccion");
        String entidadLocTipoLoc = extras.getString("entidadloc_tipoloc");
        String entidadLocCiudadId = extras.getString("entidadloc_ciudadid");

        edtEntidadLocId.setText(entidadLocId);
        edtEntidadLocCodigo.setText(entidadLocCodigo);
        edtEntidadLocNombre.setText(entidadLocNombre);
        edtEntidadLocDireccion.setText(entidadLocDireccion);
        edtEntidadLocTipoLoc.setText(entidadLocTipoLoc);
        edtEntidadLocCiudadId.setText(entidadLocCiudadId);

        if(entidadLocId != null && entidadLocId.trim().length() > 0 ){
            edtEntidadLocId.setFocusable(false);
        } else {
            txtEntidadLocId.setVisibility(View.INVISIBLE);
            edtEntidadLocId.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntidadLoc u = new EntidadLoc();
                u.setCodigo(Integer.parseInt(edtEntidadLocCodigo.getText().toString()));
                u.setNombre(edtEntidadLocNombre.getText().toString());
                u.setDireccion(edtEntidadLocDireccion.getText().toString());
                u.setTipoLoc(tipoLoc.valueOf(edtEntidadLocTipoLoc.getText().toString()));


                Long ciudadId = Long.parseLong(edtEntidadLocCiudadId.getText().toString());
                ciudadService = APIUtils.getCiudadService();
                try {
                    u.setCiudad(ciudadService.getByIdCiudad(ciudadId).execute().body());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(entidadLocId != null && entidadLocId.trim().length() > 0){
                    //update entidadLoc
                    updateEntidadLoc(Long.parseLong(entidadLocId), u);
                } else {
                    //add entidadLoc
                    addEntidadLoc(u);
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEntidadLoc(Long.parseLong(entidadLocId));

                Intent intent = new Intent(EntidadLocActivity.this, EntidadLocMainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void addEntidadLoc(EntidadLoc u){
        Call<EntidadLoc> call = entidadLocService.addEntidadLoc(u);
        call.enqueue(new Callback<EntidadLoc>() {
            @Override
            public void onResponse(Call<EntidadLoc> call, Response<EntidadLoc> response) {
                if(response.isSuccessful()){
                    Toast.makeText(EntidadLocActivity.this, "EntidadLoc creada ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EntidadLoc> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }


    public void updateEntidadLoc(Long id, EntidadLoc u){
        Call<EntidadLoc> call = entidadLocService.updateEntidadLoc(id, u);
        call.enqueue(new Callback<EntidadLoc>() {
            @Override
            public void onResponse(Call<EntidadLoc> call, Response<EntidadLoc> response) {
                if(response.isSuccessful()){
                    Toast.makeText(EntidadLocActivity.this, "EntidadLoc modificada ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EntidadLoc> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void deleteEntidadLoc(Long id){
        Call<EntidadLoc> call = entidadLocService.deleteEntidadLoc(id);
        call.enqueue(new Callback<EntidadLoc>() {
            @Override
            public void onResponse(Call<EntidadLoc> call, Response<EntidadLoc> response) {
                if(response.isSuccessful()){
                    Toast.makeText(EntidadLocActivity.this, "EntidadLoc borrada ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EntidadLoc> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void getByIdEntidadLoc(Long id){
        Call<EntidadLoc> call = entidadLocService.getByIdEntidadLoc(id);
        call.enqueue(new Callback<EntidadLoc>() {
            @Override
            public void onResponse(Call<EntidadLoc> call, Response<EntidadLoc> response) {
                if(response.isSuccessful()){
                    Toast.makeText(EntidadLocActivity.this, "EntidadLoc encontrada ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EntidadLoc> call, Throwable t) {
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
