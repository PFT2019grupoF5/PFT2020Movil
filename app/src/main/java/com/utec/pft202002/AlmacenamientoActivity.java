package com.utec.pft202002;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.model.Almacenamiento;
import com.utec.pft202002.model.EntidadLoc;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.AlmacenamientoService;
import com.utec.pft202002.remote.EntidadLocService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    TextView txtAlmacenamientoId;
    Spinner  spinnerEntidadLoc;
    Button   btnSave;
    Button   btnDel;
    Button   btnVolverAlmac;
    ArrayList<String> listaEntidadesLoc;
    HashMap<String,Long> hashEntidadesLoc;

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
        spinnerEntidadLoc = (Spinner) findViewById(R.id.spinnerEntidadLoc);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);
        btnVolverAlmac = (Button) findViewById(R.id.btnVolverAlmac);

        almacenamientoService = APIUtils.getAlmacenamientoService();
        entidadLocService = APIUtils.getEntidadLocService();

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

        obtenerListasParaSpinnerEntidadesLoc();

        if(almacenamientoId != null && almacenamientoId.trim().length() > 0 ){
            edtAlmacenamientoId.setFocusable(false);
        } else {
            txtAlmacenamientoId.setVisibility(View.INVISIBLE);
            edtAlmacenamientoId.setVisibility(View.INVISIBLE);
            edtAlmacenamientoEntidadLocId.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtAlmacenamientoNombre.getText().toString().trim().equals("")) {
                    edtAlmacenamientoNombre.requestFocus();
                    edtAlmacenamientoNombre.setError("Es necesario ingresar todo los datos requeridos");
                } else if (edtAlmacenamientoNombre.getText().toString().length() > 250) {
                    edtAlmacenamientoNombre.requestFocus();
                    edtAlmacenamientoNombre.setError("Los datos ingresados superan el largo permitido. Por favor revise sus datos.");
                } else if (edtAlmacenamientoCostoOp.getText().toString().trim().equals("")) {
                    edtAlmacenamientoCostoOp.requestFocus();
                    edtAlmacenamientoCostoOp.setError("Es necesario ingresar todo los datos requeridos");
                } else if (edtAlmacenamientoCapEstiba.getText().toString().trim().equals("")) {
                    edtAlmacenamientoCapEstiba.requestFocus();
                    edtAlmacenamientoCapEstiba.setError("Es necesario ingresar todo los datos requeridos");
                } else if (edtAlmacenamientoCapPeso.getText().toString().trim().equals("")) {
                    edtAlmacenamientoCapPeso.requestFocus();
                    edtAlmacenamientoCapPeso.setError("Es necesario ingresar todo los datos requeridos");
                } else if (edtAlmacenamientoVolumen.getText().toString().trim().equals("")) {
                    edtAlmacenamientoVolumen.requestFocus();
                    edtAlmacenamientoVolumen.setError("Es necesario ingresar todo los datos requeridos");
                } else if (spinnerEntidadLoc.getSelectedItem().toString().equals("---Por favor seleccione EntidadLoc---")) {
                    Toast.makeText(getBaseContext(), "Por favor seleccione el Local. Gracias", Toast.LENGTH_LONG).show();
                } else {

                    Almacenamiento u = new Almacenamiento();
                    u.setNombre(edtAlmacenamientoNombre.getText().toString());
                    u.setCostoop(Double.parseDouble(edtAlmacenamientoCostoOp.getText().toString()));
                    u.setCapestiba(Double.parseDouble(edtAlmacenamientoCapEstiba.getText().toString()));
                    u.setCappeso(Double.parseDouble(edtAlmacenamientoCapPeso.getText().toString()));
                    u.setVolumen(Integer.parseInt(edtAlmacenamientoVolumen.getText().toString()));

                    edtAlmacenamientoEntidadLocId.setText(Long.toString(hashEntidadesLoc.get(spinnerEntidadLoc.getSelectedItem().toString())));
                    Long entidadLocId = Long.parseLong(edtAlmacenamientoEntidadLocId.getText().toString());
                    try {
                        u.setEntidadLoc(entidadLocService.getByIdEntidadLoc(entidadLocId).execute().body());
                    } catch (IOException e) {
                        Toast.makeText(AlmacenamientoActivity.this, "*** No se pudo obtener Local por Id", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    if(almacenamientoId != null && almacenamientoId.trim().length() > 0){
                        //update almacenamiento
                        updateAlmacenamiento(Long.parseLong(almacenamientoId), u);
                        finish();
                    } else {
                        //add almacenamiento
                        addAlmacenamiento(u);
                        finish();
                    }
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
                                finish();

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

        btnVolverAlmac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void obtenerListasParaSpinnerEntidadesLoc(){

        Call<List<EntidadLoc>> call = entidadLocService.getEntidadesLoc();
        call.enqueue(new Callback<List<EntidadLoc>>() {
            @Override
            public void onResponse(Call<List<EntidadLoc>> call, Response<List<EntidadLoc>> response) {
                if(response.isSuccessful()){
                    List<EntidadLoc> entidadesLocList = response.body();

                    listaEntidadesLoc = new ArrayList<>();
                    hashEntidadesLoc = new HashMap<String,Long>();
                    listaEntidadesLoc.add("---Por favor seleccione EntidadLoc---");
                    for (int i=0;i<entidadesLocList.size();i++){
                        hashEntidadesLoc.put(entidadesLocList.get(i).getId() + " " +entidadesLocList.get(i).getNombre(),entidadesLocList.get(i).getId());
                        listaEntidadesLoc.add(entidadesLocList.get(i).getId() + " " +entidadesLocList.get(i).getNombre());
                    }
                    ArrayAdapter<String> adapterSpinnerEntidadesLoc = new ArrayAdapter<String>(AlmacenamientoActivity.this, android.R.layout.simple_spinner_item, listaEntidadesLoc);
                    spinnerEntidadLoc.setAdapter(adapterSpinnerEntidadesLoc);
                } else  {
                    Toast.makeText(AlmacenamientoActivity.this, "getEntidadesLoc: Servicio no disponible. Por favor comuniquese con su Administrador.", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<List<EntidadLoc>> call, Throwable t) {
                Toast.makeText(AlmacenamientoActivity.this, "*** No se pudo obtener Lista de Locales", Toast.LENGTH_SHORT).show();
                Log.e("ERROR: ", t.getMessage());
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
                } else  {
                    Toast.makeText(AlmacenamientoActivity.this, "No fue posible agregar el Almacenamiento. Verifique los datos ingresados.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Almacenamiento> call, Throwable t) {
                Toast.makeText(AlmacenamientoActivity.this, "*** Error al crear Almacenamiento", Toast.LENGTH_SHORT).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void updateAlmacenamiento(Long id, Almacenamiento almacenamiento){
        Call<Almacenamiento> call = almacenamientoService.updateAlmacenamiento(id, almacenamiento);
        call.enqueue(new Callback<Almacenamiento>() {
            @Override
            public void onResponse(Call<Almacenamiento> call, Response<Almacenamiento> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AlmacenamientoActivity.this, "Almacenamiento modificado ok!", Toast.LENGTH_SHORT).show();
                } else  {
                    Toast.makeText(AlmacenamientoActivity.this, "No fue posible modificar el Almacenamiento. Verifique los datos ingresados.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Almacenamiento> call, Throwable t) {
                Toast.makeText(AlmacenamientoActivity.this, "*** Error al modificar Almacenamiento", Toast.LENGTH_SHORT).show();
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
                } else {
                    Toast.makeText(AlmacenamientoActivity.this, "No fue posible borrar el Almacenamiento. Verifique si está en otro registro.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Almacenamiento> call, Throwable t) {
                Toast.makeText(AlmacenamientoActivity.this, "*** Error al borrar Almacenamiento", Toast.LENGTH_SHORT).show();
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
                } else {
                    Toast.makeText(AlmacenamientoActivity.this, "No fue posible obtener el Almacenamiento por Id.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Almacenamiento> call, Throwable t) {
                Toast.makeText(AlmacenamientoActivity.this, "*** No se pudo encontrar Almacenamiento por ID", Toast.LENGTH_SHORT).show();
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
