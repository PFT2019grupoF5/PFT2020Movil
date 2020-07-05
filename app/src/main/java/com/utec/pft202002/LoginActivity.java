package com.utec.pft202002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.utec.pft202002.model.ResObj;
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
                String nomacceso = edtUsuarioNomAcceso.getText().toString();
                String contrasena = edtUsuarioContrasena.getText().toString();
                if(validateLogin(nomacceso, contrasena)){
                    doLogin(nomacceso, contrasena);
                }
            }
        });
    }

    private boolean validateLogin(String nomacceso, String contrasena){
        if(nomacceso == null || nomacceso.trim().length() == 0){
            Toast.makeText(this, "Por favor ingrese el nombre de aceeso del usuario", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(contrasena == null || contrasena.trim().length() == 0){
            Toast.makeText(this, "Por favor ingrese la contraseña del usuario", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doLogin(final String nomacceso,final String contrasena){
        Call call = usuarioService.login(nomacceso,contrasena);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    ResObj resObj = (ResObj) response.body();
                    if(resObj.getMessage().equals("true")){
                        //login start main activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("nomacceso", nomacceso);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}