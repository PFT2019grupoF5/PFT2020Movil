package com.utec.pft202002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.utec.pft202002.model.Usuario;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.UsuarioService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    UsuarioService usuarioService;

    EditText edtUsuarioNomAcceso;
    EditText edtUsuarioContrasena;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsuarioNomAcceso = (EditText) findViewById(R.id.edtUsuarioNomAcceso);
        edtUsuarioContrasena = (EditText) findViewById(R.id.edtUsuarioContrasena);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        usuarioService = APIUtils.getUsuarioService();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomAcceso = edtUsuarioNomAcceso.getText().toString();
                String contrasena = edtUsuarioContrasena.getText().toString();
                if (validateLogin(nomAcceso, contrasena)) {
                    doLogin(nomAcceso, contrasena);
                }
            }
        });
    }

    private boolean validateLogin(String nomAcceso, String contrasena) {
        if (nomAcceso == null || nomAcceso.trim().length() == 0) {
            Toast.makeText(this, "Por favor ingrese el nombre de acceso del usuario", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (nomAcceso.length() > 20) {
            Toast.makeText(this, "El nombre de usuario no debe superar los 20 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (contrasena == null || contrasena.trim().length() == 0) {
            Toast.makeText(this, "Por favor ingrese la contraseña del usuario", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (contrasena.length() < 8 || contrasena.length() > 16) {
            Toast.makeText(this, "La contraeña debe ser minimo 8 y maximo 16 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doLogin(final String nomAcceso, final String contrasena) {
        Call<Usuario> call = usuarioService.login(nomAcceso, contrasena);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                System.out.println("response.isSuccessful() " + response.isSuccessful());
                if (response.isSuccessful()) {
                    if (response.body().getContrasena().equals("+Login-Ok!")) {
                        Toast.makeText(LoginActivity.this, "Login Correcto", Toast.LENGTH_LONG).show();
                        //login start main activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("usuario_nombre", response.body().getNombre());
                        intent.putExtra("usuario_apellido", response.body().getApellido());
                        intent.putExtra("usuario_nomacceso", response.body().getNomAcceso());
                        intent.putExtra("usuario_tipoperfil", String.valueOf(response.body().getTipoPerfil()));
                        startActivity(intent);

                    } else if (response.body().getContrasena().equals("+Error!")) {
                        Toast.makeText(LoginActivity.this, "Contraseña no Válidas, por favor revise los datos ingresados.", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(LoginActivity.this, "Usuario o Contraseña no Válidas, por favor revise los datos ingresados.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}