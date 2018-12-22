package com.example.l.uadnews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    Spinner spinnerStatus;
    EditText editTextUsername, editTextNamaPanjang, editTextNomorHP, editTextEmail, editTextPassword, editTextConfirmPassword;
    Button buttonRegister;

    AwesomeValidation validation;

//    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = (EditText) findViewById(R.id.username);
        editTextNamaPanjang = (EditText) findViewById(R.id.nama_user);
        editTextNomorHP = (EditText) findViewById(R.id.noHp);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextConfirmPassword = (EditText) findViewById(R.id.confpassword);
        spinnerStatus = (Spinner) findViewById(R.id.spStatus);

        validation = new AwesomeValidation(ValidationStyle.BASIC);

        validation.addValidation(Register.this, R.id.username, "^(?=\\s*\\S).*$", R.string.username_error);
        validation.addValidation(Register.this, R.id.nama_user, "[a-zA-Z\\s]+", R.string.nama_error );
        validation.addValidation(Register.this, R.id.noHp, "^[+]?[0-9]{10,13}$", R.string.no_hp_error );
        validation.addValidation(Register.this, R.id.email, android.util.Patterns.EMAIL_ADDRESS, R.string.email_error);


/**        extras = getIntent().getExtras();
        String NoHP = (String) extras.get("NoHP");
        EditText etNoHp = (EditText) findViewById(R.id.noHp);
        etNoHp.setText(NoHP);

        Toast.makeText(getApplicationContext(), NoHP, Toast.LENGTH_LONG).show();
*/
        ((Button) findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String p = ((EditText) findViewById(R.id.password)).getText().toString();
                String p2 = ((EditText) findViewById(R.id.confpassword)).getText().toString();
                if (p.equals(p2))
                    register();
                else
                    Toast.makeText(Register.this, "periksa kembali data yang anda masukkan", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void register() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,HttpUrl.alamat + "api/register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("username", ((EditText) findViewById(R.id.username)).getText().toString());
                map.put("password", ((EditText) findViewById(R.id.password)).getText().toString());
                map.put("nama_user", ((EditText) findViewById(R.id.nama_user)).getText().toString());
                map.put("status", String.valueOf(spinnerStatus.getSelectedItem()) );
                map.put("nohp", ((EditText) findViewById(R.id.noHp)).getText().toString());
                map.put("email", ((EditText) findViewById(R.id.email)).getText().toString());

                return map;
            }
        };
        requestQueue.add(stringRequest);
    }


}
