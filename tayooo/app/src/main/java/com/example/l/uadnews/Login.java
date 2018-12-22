package com.example.l.uadnews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
//    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((Button) findViewById(R.id.login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });
        ((Button) findViewById(R.id.register)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class)); //
//                Verifikasi();
            }
        });


    }

    public void checkLogin() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl.alamat + "api/login",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("CheckResponse", response); //?
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("sukses")) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                new Prefferences(Login.this).setLogin(data.getString("id_user"));
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            } else

                                Toast.makeText(Login.this, "Username dan Password salah", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> map = new HashMap<>();
                            map.put("username", ((EditText) findViewById(R.id.username)).getText().toString());
                            map.put("password", ((EditText) findViewById(R.id.password)).getText().toString());

                            return map;
                            }
        };
        requestQueue.add(stringRequest);
    }
}

/**
 * public void Verifikasi() {
 * FirebaseAuth auth = FirebaseAuth.getInstance();
 * startActivityForResult(
 * AuthUI.getInstance()
 * .createSignInIntentBuilder()
 * .setAvailableProviders(
 * Arrays.asList(
 * new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()
 * ))
 * .build(),
 * RC_SIGN_IN);
 * }
 *
 * @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 * super.onActivityResult(requestCode, resultCode, data);
 * <p>
 * // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
 * if (requestCode == RC_SIGN_IN) {
 * IdpResponse response = IdpResponse.fromResultIntent(data);
 * <p>
 * // Successfully signed in
 * if (resultCode == RESULT_OK) {
 * //lanjutkan proses simpan data/register
 * //String NoHP = response.getPhoneNumber();
 * //startActivity(new Intent(Login.this,Register.class));
 * <p>
 * Intent intent = new Intent(Login.this, Register.class);
 * intent.putExtra("NoHP",response.getPhoneNumber());
 * startActivity(intent);
 * <p>
 * return;
 * } else {
 * // Sign in failed
 * if (response == null) {
 * // User pressed back button
 * finish();
 * return;
 * }
 * <p>
 * if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
 * Toast.makeText(getApplicationContext(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
 * return;
 * }
 * <p>
 * if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
 * Toast.makeText(getApplicationContext(), "Terjadi Error", Toast.LENGTH_SHORT).show();
 * return;
 * }
 * }
 * }
 * }
 * }
 */
