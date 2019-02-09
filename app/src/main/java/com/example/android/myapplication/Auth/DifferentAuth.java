package com.example.android.myapplication.Auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.myapplication.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;

public class DifferentAuth extends AppCompatActivity implements View.OnClickListener {

    private Button  btnAnonymous,btnFB,btnPhoneAuth,btnEmailLink;
    private SignInButton signInGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_different_auth);

        signInGoogle = findViewById(R.id.signInGoogle);
        btnAnonymous = findViewById(R.id.btnAnonymous);
        btnFB = findViewById(R.id.btnFB);
        btnEmailLink = findViewById(R.id.btnEmailLink);
        btnPhoneAuth = findViewById(R.id.btnPhoneAuth);

        signInGoogle.setOnClickListener(this);
        btnPhoneAuth.setOnClickListener(this);
        btnFB.setOnClickListener(this);
        btnEmailLink.setOnClickListener(this);
        btnAnonymous.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signInGoogle : {
                startActivity(new Intent(getBaseContext(),GoogleSignInActivity.class));
                break;
            }

            case R.id.btnFB : {

                break;
            }

            case R.id.btnEmailLink : {
                startActivity(new Intent(getBaseContext(),PasswordlessActivity.class));
                break;
            }

            case R.id.btnAnonymous : {
                startActivity(new Intent(getBaseContext(),AnonymousActivity.class));
                break;
            }

            case R.id.btnPhoneAuth : {
                startActivity(new Intent(getBaseContext(),PhoneAuthActivity.class));
                break;
            }
        }
    }
}
