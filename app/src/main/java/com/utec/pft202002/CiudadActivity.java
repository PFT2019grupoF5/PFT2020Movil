package com.utec.pft202002;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.utec.pft202002.model.Ciudad;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.CiudadService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CiudadActivity extends AppCompatActivity {

    CiudadService ciudadService;
    EditText edtCiudadId;
    EditText edtCiudadNombre;
    Button btnSave;
    Button btnDel;
    TextView txtCiudadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciudad);

        setTitle("Ciudades");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtCiudadId = (TextView) findViewById(R.id.txtCiudadId);
        edtCiudadId = (EditText) findViewById(R.id.edtCiudadId);
        edtCiudadNombre = (EditText) findViewById(R.id.edtCiudadNombre);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);

        ciudadService = APIUtils.getCiudadService();

        Bundle extras = getIntent().getExtras();
        final String ciudadId = extras.getString("ciudad_id");
        String ciudadNombre = extras.getString("ciudad_nombre");

        edtCiudadId.setText(ciudadId);
        edtCiudadNombre.setText(ciudadNombre);

        if(ciudadId != null && ciudadId.trim().length() > 0 ){
            edtCiudadId.setFocusable(false);
        } else {
            txtCiudadId.setVisibility(View.INVISIBLE);
            edtCiudadId.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ciudad u = new Ciudad();
                u.setNombre(edtCiudadNombre.getText().toString());
                if(ciudadId != null && ciudadId.trim().length() > 0){
                    //update ciudad
                    updateCiudad(Long.parseLong(ciudadId), u);
                } else {
                    //add ciudad
                    addCiudad(u);
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(CiudadActivity.this);
                builder.setMessage("¿Por favor confirme que quiere borrar esta Ciudad? Gracias").
                        setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deleteCiudad(Long.parseLong(ciudadId));
                                Intent intent = new Intent(CiudadActivity.this, CiudadMainActivity.class);
                                startActivity(intent);

                            }
                        }).
                        setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });

    }

    public void addCiudad(Ciudad u){
        Call<Ciudad> call = ciudadService.addCiudad(u);
        call.enqueue(new Callback<Ciudad>() {
            @Override
            public void onResponse(Call<Ciudad> call, Response<Ciudad> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CiudadActivity.this, "Ciudad creada ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ciudad> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void updateCiudad(Long id, Ciudad u){
        Call<Ciudad> call = ciudadService.updateCiudad(id, u);
        call.enqueue(new Callback<Ciudad>() {
            @Override
            public void onResponse(Call<Ciudad> call, Response<Ciudad> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CiudadActivity.this, "Ciudad modificada ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ciudad> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void deleteCiudad(Long id){
        Call<Ciudad> call = ciudadService.deleteCiudad(id);
        call.enqueue(new Callback<Ciudad>() {
            @Override
            public void onResponse(Call<Ciudad> call, Response<Ciudad> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CiudadActivity.this, "Ciudad borrada ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ciudad> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void getByIdCiudad(Long id){
        Call<Ciudad> call = ciudadService.getByIdCiudad(id);
        call.enqueue(new Callback<Ciudad>() {
            @Override
            public void onResponse(Call<Ciudad> call, Response<Ciudad> response) {
                if(response.isSuccessful()){
                    Toast.makeText(CiudadActivity.this, "Ciudad encontrada ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ciudad> call, Throwable t) {
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
