package com.example.ihsan.authentication.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.ihsan.authentication.MainActivity;
import com.example.ihsan.authentication.R;
import com.example.ihsan.authentication.helper.MyFuction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends MyFuction {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.logingoogle)
    Button logingoogle;
    @BindView(R.id.loginanonymous)
    Button loginanonymous;
    @BindView(R.id.loginphone)
    Button loginphone;
    @BindView(R.id.btn_reset_password)
    Button btnResetPassword;
    @BindView(R.id.btn_signup)
    Button btnSignup;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener listener;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        auth= FirebaseAuth.getInstance();
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user =firebaseAuth.getCurrentUser();
                if (user!=null){
                    if (user.isEmailVerified()){
                        myToast("selamat anda berhasil login");
                        myIntent(MainActivity.class);
                        finish();
                    }else{
                        myToast("gagal login \n periksa email untuk verifikasi");
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                }
            }
        };
    }

    @OnClick({R.id.btn_login, R.id.logingoogle, R.id.loginanonymous, R.id.loginphone, R.id.btn_reset_password, R.id.btn_signup})
    public void onViewClicked(View view) {
        String em = email.getText().toString();
        String pw =password.getText().toString();
        switch (view.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(em)) {
                    email.setError("email harus diisi");
                    email.requestFocus();
                    myanimation(email);
                } else if (TextUtils.isEmpty(pw)) {
                    password.setError("passowrd harus diisi");
                    password.requestFocus();
                    myanimation(password);
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(em,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                myIntent(MainActivity.class);
                                finish();
                            }else{
                                myToast("gagal login "+task.getException());
                            }
                        }
                    });
                }
                break;
            case R.id.logingoogle:
                break;
            case R.id.loginanonymous:
                break;
            case R.id.loginphone:
                break;
            case R.id.btn_reset_password:
                break;
            case R.id.btn_signup:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        keluar();
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (listener!=null){
            auth.removeAuthStateListener(listener);
        }
    }

}