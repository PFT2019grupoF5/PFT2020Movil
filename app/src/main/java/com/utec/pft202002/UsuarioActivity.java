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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.Enum.tipoPerfil;
import com.utec.pft202002.model.Usuario;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.UsuarioService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioActivity extends AppCompatActivity {

    UsuarioService usuarioService;

    EditText edtUsuarioId;
    EditText edtUsuarioNombre;
    EditText edtUsuarioApellido;
    EditText edtUsuarioNomAcceso;
    EditText edtUsuarioContrasena;
    EditText edtUsuarioCorreo;
    EditText edtUsuarioTipoPerfil;
    TextView txtUsuarioId;
    Spinner  spinnerTipoPerfil;
    Button   btnSave;
    Button   btnDel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        setTitle("Usuarios");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtUsuarioId = (TextView) findViewById(R.id.txtUsuarioId);
        edtUsuarioId = (EditText) findViewById(R.id.edtUsuarioId);
        edtUsuarioNombre = (EditText) findViewById(R.id.edtUsuarioNombre);
        edtUsuarioApellido = (EditText) findViewById(R.id.edtUsuarioApellido);
        edtUsuarioNomAcceso = (EditText) findViewById(R.id.edtUsuarioNomAcceso);
        edtUsuarioContrasena = (EditText) findViewById(R.id.edtUsuarioContrasena);
        edtUsuarioCorreo = (EditText) findViewById(R.id.edtUsuarioCorreo);
        edtUsuarioTipoPerfil = (EditText) findViewById(R.id.edtUsuarioTipoPerfil);

        spinnerTipoPerfil = (Spinner) findViewById(R.id.spinnerTipoPerfil);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);

        usuarioService = APIUtils.getUsuarioService();

        Bundle extras = getIntent().getExtras();
        final String usuarioId = extras.getString("usuario_id");
        String usuarioNombre = extras.getString("usuario_nombre");
        String usuarioApellido = extras.getString("usuario_apellido");
        String usuarioNomAcceso = extras.getString("usuario_nomacceso");
        String usuarioConrtasena = extras.getString("usuario_contrasena");
        String usuarioCorreo = extras.getString("usuario_correo");
        String usuarioTipoPerfil = extras.getString("usuario_tipoperfil");

        edtUsuarioId.setText(usuarioId);
        edtUsuarioNombre.setText(usuarioNombre);
        edtUsuarioApellido.setText(usuarioApellido);
        edtUsuarioNomAcceso.setText(usuarioNomAcceso);
        edtUsuarioContrasena.setText(usuarioConrtasena);
        edtUsuarioCorreo.setText(usuarioCorreo);
        edtUsuarioTipoPerfil.setText(usuarioTipoPerfil);

        if(usuarioId != null && usuarioId.trim().length() > 0 ){
            edtUsuarioId.setFocusable(false);
        } else {
            txtUsuarioId.setVisibility(View.INVISIBLE);
            edtUsuarioId.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spinnerTipoPerfil.getSelectedItem().toString().equals("---Por favor seleccione Tipo de Perfil---")){
                    Toast.makeText(getBaseContext(),"Por favor seleccione un Tipo de Perfil. Gracias",Toast.LENGTH_LONG).show();
                } else {

                    edtUsuarioTipoPerfil.setText((spinnerTipoPerfil.getSelectedItem().toString()));

                    Usuario u = new Usuario();
                    u.setNombre(edtUsuarioNombre.getText().toString());
                    u.setApellido(edtUsuarioApellido.getText().toString());
                    u.setNomAcceso(edtUsuarioNomAcceso.getText().toString());
                    u.setContrasena(edtUsuarioContrasena.getText().toString());
                    u.setCorreo(edtUsuarioCorreo.getText().toString());
                    u.setTipoPerfil(tipoPerfil.valueOf(edtUsuarioTipoPerfil.getText().toString()));

                    if(usuarioId != null && usuarioId.trim().length() > 0){
                        //update usuario
                        updateUsuario(Long.parseLong(usuarioId), u);
                    } else {
                        //add usuario
                        addUsuario(u);
                    }

                }

            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(UsuarioActivity.this);
                builder.setMessage("¿Por favor confirme que quiere borrar este Usuario? Gracias").
                        setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deleteUsuario(Long.parseLong(usuarioId));
                                Intent intent = new Intent(UsuarioActivity.this, UsuarioMainActivity.class);
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



    public void addUsuario(Usuario u){
        Call<Usuario> call = usuarioService.addUsuario(u);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UsuarioActivity.this, "Usuario creada ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }


    public void updateUsuario(Long id, Usuario u){
        Call<Usuario> call = usuarioService.updateUsuario(id, u);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UsuarioActivity.this, "Usuario modificada ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void deleteUsuario(Long id){
        Call<Usuario> call = usuarioService.deleteUsuario(id);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UsuarioActivity.this, "Usuario borrada ok!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UsuarioActivity.this, "No fue posible borrar el Usuario. Verifique si está en otro registro.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }


    public void getByIdUsuario(Long id){
        Call<Usuario> call = usuarioService.getByIdUsuario(id);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UsuarioActivity.this, "Usuario encontrado ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
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
