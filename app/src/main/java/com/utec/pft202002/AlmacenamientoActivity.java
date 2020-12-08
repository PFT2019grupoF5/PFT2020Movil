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

import com.utec.pft202002.model.Almacenamiento;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.AlmacenamientoService;
import com.utec.pft202002.remote.EntidadLocService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlmacenamientoActivity extends AppCompatActivity {

    AlmacenamientoService almacenamientoService;
    EntidadLocService entidadLocService;

    EditText edtAlmacenamientoId;
    EditText edtAlmacenamientoNombre;
    EditText edtAlmacenamientoCostoOp;
    EditText edtAlmacenamientoCapEstiba;
    EditText edtAlmacenamientoCapPeso;
    EditText edtAlmacenamientoVolumen;
    EditText edtAlmacenamientoEntidadLocId;
    Button btnSave;
    Button btnDel;
    TextView txtAlmacenamientoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacenamiento);

        setTitle("Almacenamientos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtAlmacenamientoId = (TextView) findViewById(R.id.txtAlmacenamientoId);
        edtAlmacenamientoId = (EditText) findViewById(R.id.edtAlmacenamientoId);
        edtAlmacenamientoNombre = (EditText) findViewById(R.id.edtAlmacenamientoNombre);
        edtAlmacenamientoCostoOp = (EditText) findViewById(R.id.edtAlmacenamientoCostoOp);
        edtAlmacenamientoCapEstiba = (EditText) findViewById(R.id.edtAlmacenamientoCapEstiba);
        edtAlmacenamientoCapPeso = (EditText) findViewById(R.id.edtAlmacenamientoCapPeso);
        edtAlmacenamientoVolumen = (EditText) findViewById(R.id.edtAlmacenamientoVolumen);
        edtAlmacenamientoEntidadLocId = (EditText) findViewById(R.id.edtAlmacenamientoEntidadLocId);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);

        almacenamientoService = APIUtils.getAlmacenamientoService();

        Bundle extras = getIntent().getExtras();
        final String almacenamientoId = extras.getString("almacenamiento_id");
        String almacenamientoNombre = extras.getString("almacenamiento_nombre");
        String almacenamientoCostoOp = extras.getString("almacenamiento_costoop");
        String almacenamientoCapEstiba = extras.getString("almacenamiento_capestiba");
        String almacenamientoCapPeso = extras.getString("almacenamiento_cappeso");
        String almacenamientoVolumen = extras.getString("almacenamiento_volumen");
        String almacenamientoEntidadLocId = extras.getString("almacenamiento_entidadlocid");

        edtAlmacenamientoId.setText(almacenamientoId);
        edtAlmacenamientoNombre.setText(almacenamientoNombre);
        edtAlmacenamientoCostoOp.setText(almacenamientoCostoOp);
        edtAlmacenamientoCapEstiba.setText(almacenamientoCapEstiba);
        edtAlmacenamientoCapPeso.setText(almacenamientoCapPeso);
        edtAlmacenamientoVolumen.setText(almacenamientoVolumen);
        edtAlmacenamientoEntidadLocId.setText(almacenamientoEntidadLocId);

        if(almacenamientoId != null && almacenamientoId.trim().length() > 0 ){
            edtAlmacenamientoId.setFocusable(false);
        } else {
            txtAlmacenamientoId.setVisibility(View.INVISIBLE);
            edtAlmacenamientoId.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Almacenamiento u = new Almacenamiento();
                u.setNombre(edtAlmacenamientoNombre.getText().toString());
                u.setCostoop(Double.parseDouble(edtAlmacenamientoCostoOp.getText().toString()));
                u.setCapestiba(Double.parseDouble(edtAlmacenamientoCapEstiba.getText().toString()));
                u.setCappeso(Double.parseDouble(edtAlmacenamientoCapPeso.getText().toString()));
                u.setVolumen(Integer.parseInt(edtAlmacenamientoVolumen.getText().toString()));

                Long entidadLocId = Long.parseLong(edtAlmacenamientoEntidadLocId.getText().toString());
                entidadLocService = APIUtils.getEntidadLocService();
                try {
                    u.setEntidadLoc(entidadLocService.getByIdEntidadLoc(entidadLocId).execute().body());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(almacenamientoId != null && almacenamientoId.trim().length() > 0){
                    //update almacenamiento
                    updateAlmacenamiento(Long.parseLong(almacenamientoId), u);
                } else {
                    //add almacenamiento
                    addAlmacenamiento(u);
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(AlmacenamientoActivity.this);
                builder.setMessage("¿Por favor confirme que quiere borrar este Almacenamiento? Gracias").
                        setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deleteAlmacenamiento(Long.parseLong(almacenamientoId));
                                Intent intent = new Intent(AlmacenamientoActivity.this, AlmacenamientoMainActivity.class);
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

    public void addAlmacenamiento(Almacenamiento u){
        Call<Almacenamiento> call = almacenamientoService.addAlmacenamiento(u);
        call.enqueue(new Callback<Almacenamiento>() {
            @Override
            public void onResponse(Call<Almacenamiento> call, Response<Almacenamiento> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AlmacenamientoActivity.this, "Almacenamiento creado ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Almacenamiento> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void updateAlmacenamiento(Long id, Almacenamiento u){
        Call<Almacenamiento> call = almacenamientoService.updateAlmacenamiento(id, u);
        call.enqueue(new Callback<Almacenamiento>() {
            @Override
            public void onResponse(Call<Almacenamiento> call, Response<Almacenamiento> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AlmacenamientoActivity.this, "Almacenamiento modificado ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Almacenamiento> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void deleteAlmacenamiento(Long id){
        Call<Almacenamiento> call = almacenamientoService.deleteAlmacenamiento(id);
        call.enqueue(new Callback<Almacenamiento>() {
            @Override
            public void onResponse(Call<Almacenamiento> call, Response<Almacenamiento> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AlmacenamientoActivity.this, "Almacenamiento borrado ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Almacenamiento> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void getByIdAlmacenamiento(Long id){
        Call<Almacenamiento> call = almacenamientoService.getByIdAlmacenamiento(id);
        call.enqueue(new Callback<Almacenamiento>() {
            @Override
            public void onResponse(Call<Almacenamiento> call, Response<Almacenamiento> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AlmacenamientoActivity.this, "Almacenamiento encontrado ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Almacenamiento> call, Throwable t) {
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
