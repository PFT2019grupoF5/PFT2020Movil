package com.utec.pft202002;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.utec.pft202002.model.Ciudad;
import com.utec.pft202002.remote.CiudadService;
import com.utec.pft202002.model.EntidadLoc;
import com.utec.pft202002.remote.EntidadLocService;
import com.utec.pft202002.Enum.tipoLoc;
import com.utec.pft202002.remote.APIUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    TextView txtEntidadLocId;
    Spinner  spinnerTipoLoc;
    Spinner  spinnerCiudad;
    Button   btnSave;
    Button   btnDel;
    ArrayList<String> listaCiudades;
    HashMap<String,Long> hashCiudades;


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
        spinnerTipoLoc = (Spinner) findViewById(R.id.spinnerTipoLoc);
        spinnerCiudad = (Spinner) findViewById(R.id.spinnerCiudad);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);

        entidadLocService = APIUtils.getEntidadLocService();
        ciudadService = APIUtils.getCiudadService();

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

        obtenerListasParaSpinnerCiudades();

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
                if (spinnerTipoLoc.getSelectedItem().toString().equals("---Por favor seleccione Tipo de Local---")) {
                    Toast.makeText(getBaseContext(), "Por favor seleccione un Tipo de Local. Gracias", Toast.LENGTH_LONG).show();
                } else {

                    edtEntidadLocTipoLoc.setText((spinnerTipoLoc.getSelectedItem().toString()));
                    edtEntidadLocCiudadId.setText(Long.toString(hashCiudades.get(spinnerCiudad.getSelectedItem().toString())));

                    EntidadLoc u = new EntidadLoc();
                    u.setCodigo(Integer.parseInt(edtEntidadLocCodigo.getText().toString()));
                    u.setNombre(edtEntidadLocNombre.getText().toString());
                    u.setDireccion(edtEntidadLocDireccion.getText().toString());
                    u.setTipoLoc(tipoLoc.valueOf(edtEntidadLocTipoLoc.getText().toString()));

                    Long ciudadId = Long.parseLong(edtEntidadLocCiudadId.getText().toString());
                    try {
                        u.setCiudad(ciudadService.getByIdCiudad(ciudadId).execute().body());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (entidadLocId != null && entidadLocId.trim().length() > 0) {
                        //update entidadLoc
                        updateEntidadLoc(Long.parseLong(entidadLocId), u);
                    } else {
                        //add entidadLoc
                        addEntidadLoc(u);
                    }
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(EntidadLocActivity.this);
                builder.setMessage("¿Por favor confirme que quiere borrar este Local? Gracias").
                        setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deleteEntidadLoc(Long.parseLong(entidadLocId));
                                Intent intent = new Intent(EntidadLocActivity.this, EntidadLocMainActivity.class);
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


    public void obtenerListasParaSpinnerCiudades(){

        Call<List<Ciudad>> call = ciudadService.getCiudades();
        call.enqueue(new Callback<List<Ciudad>>() {
            @Override
            public void onResponse(Call<List<Ciudad>> call, Response<List<Ciudad>> response) {
                if(response.isSuccessful()){
                    List<Ciudad> ciudadesList = response.body();

                    listaCiudades = new ArrayList<>();
                    hashCiudades = new HashMap<String,Long>();
                    listaCiudades.add("---Por favor seleccione Ciudad---");
                    for (int i=0;i<ciudadesList.size();i++){
                        hashCiudades.put(ciudadesList.get(i).getNombre(),ciudadesList.get(i).getId());
                        listaCiudades.add(ciudadesList.get(i).getNombre());
                    }
                    ArrayAdapter<String> adapterSpinnerCiudades = new ArrayAdapter<String>(EntidadLocActivity.this, android.R.layout.simple_spinner_item, listaCiudades);
                    spinnerCiudad.setAdapter(adapterSpinnerCiudades);
                }
            }
            @Override
            public void onFailure(Call<List<Ciudad>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
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
                } else {
                    Toast.makeText(EntidadLocActivity.this, "No fue posible borrar el Local. Verifique si está en otro registro.", Toast.LENGTH_SHORT).show();
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
