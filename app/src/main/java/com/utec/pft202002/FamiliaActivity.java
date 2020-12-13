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

import com.utec.pft202002.model.Familia;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.FamiliaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamiliaActivity extends AppCompatActivity {

    FamiliaService familiaService;
    EditText edtFamiliaId;
    EditText edtFamiliaNombre;
    EditText edtFamiliaDescrip;
    EditText edtFamiliaIncompat;
    Button btnSave;
    Button btnDel;
    TextView txtFamiliaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familia);

        setTitle("Familias");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtFamiliaId = (TextView) findViewById(R.id.txtFamiliaId);
        edtFamiliaId = (EditText) findViewById(R.id.edtFamiliaId);
        edtFamiliaNombre = (EditText) findViewById(R.id.edtFamiliaNombre);
        edtFamiliaDescrip = (EditText) findViewById(R.id.edtFamiliaDescrip);
        edtFamiliaIncompat = (EditText) findViewById(R.id.edtFamiliaIncompat);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);

        familiaService = APIUtils.getFamiliaService();

        Bundle extras = getIntent().getExtras();
        final String familiaId = extras.getString("familia_id");
        String familiaNombre = extras.getString("familia_nombre");
        String familiaDescrip = extras.getString("familia_descrip");
        String familiaIncompat = extras.getString("familia_incompat");

        edtFamiliaId.setText(familiaId);
        edtFamiliaNombre.setText(familiaNombre);
        edtFamiliaDescrip.setText(familiaDescrip);
        edtFamiliaIncompat.setText(familiaIncompat);

        if(familiaId != null && familiaId.trim().length() > 0 ){
            edtFamiliaId.setFocusable(false);
        } else {
            txtFamiliaId.setVisibility(View.INVISIBLE);
            edtFamiliaId.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Familia u = new Familia();
                u.setNombre(edtFamiliaNombre.getText().toString());
                u.setDescrip(edtFamiliaDescrip.getText().toString());
                u.setIncompat(edtFamiliaIncompat.getText().toString());
                if(familiaId != null && familiaId.trim().length() > 0){
                    //update familia
                    updateFamilia(Long.parseLong(familiaId), u);
                } else {
                    //add familia
                    addFamilia(u);
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(FamiliaActivity.this);
                builder.setMessage("¿Por favor confirme que quiere borrar esta Familia? Gracias").
                        setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deleteFamilia(Long.parseLong(familiaId));
                                Intent intent = new Intent(FamiliaActivity.this, FamiliaMainActivity.class);
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

    public void addFamilia(Familia u){
        Call<Familia> call = familiaService.addFamilia(u);
        call.enqueue(new Callback<Familia>() {
            @Override
            public void onResponse(Call<Familia> call, Response<Familia> response) {
                if(response.isSuccessful()){
                    Toast.makeText(FamiliaActivity.this, "Familia creada ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Familia> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void updateFamilia(Long id, Familia u){
        Call<Familia> call = familiaService.updateFamilia(id, u);
        call.enqueue(new Callback<Familia>() {
            @Override
            public void onResponse(Call<Familia> call, Response<Familia> response) {
                if(response.isSuccessful()){
                    Toast.makeText(FamiliaActivity.this, "Familia modificada ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Familia> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void deleteFamilia(Long id){
        Call<Familia> call = familiaService.deleteFamilia(id);
        call.enqueue(new Callback<Familia>() {
            @Override
            public void onResponse(Call<Familia> call, Response<Familia> response) {
                if(response.isSuccessful()){
                    Toast.makeText(FamiliaActivity.this, "Familia borrada ok!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FamiliaActivity.this, "No fue posible borrar la Familia. Verifique si está en otro registro.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Familia> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void getByIdFamilia(Long id){
        Call<Familia> call = familiaService.getByIdFamilia(id);
        call.enqueue(new Callback<Familia>() {
            @Override
            public void onResponse(Call<Familia> call, Response<Familia> response) {
                if(response.isSuccessful()){
                    Toast.makeText(FamiliaActivity.this, "Familia encontrada ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Familia> call, Throwable t) {
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
