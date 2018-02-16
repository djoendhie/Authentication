package com.example.ihsan.authentication.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

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
public class RegisterActivity extends MyFuction {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.sign_up_button)
    Button signUpButton;
    @BindView(R.id.btn_reset_password)
    Button btnResetPassword;
    @BindView(R.id.sign_in_button)
    Button signInButton;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        auth =FirebaseAuth.getInstance();
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =firebaseAuth.getCurrentUser();
                if (user!=null){
                    myToast("silahkan cek email untuk verifikasi");
                    verifikasiemail();
                }

            }
        };

    }

    private void verifikasiemail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseAuth.getInstance().signOut();
                    myIntent(LoginActivity.class);
                    finish();
                } else {
                    //restart activity
                    overridePendingTransition(0, 0);
                    finish();
                    overridePendingTransition(0, 0);
                }
            }
        });

    }

    @OnClick({R.id.sign_up_button, R.id.btn_reset_password, R.id.sign_in_button})
    public void onViewClicked(View view) {
        String em = email.getText().toString();
        String pw =password.getText().toString();
        switch (view.getId()) {
            case R.id.sign_up_button:
                if (TextUtils.isEmpty(em)) {
                    email.setError("email harus diisi");
                    email.requestFocus();
                    myanimation(email);
                } else if (TextUtils.isEmpty(pw)) {
                    password.setError("passowrd harus diisi");
                    password.requestFocus();
                    myanimation(password);
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(em,pw).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        myToast("selamat anda berhasil register");
                                    }else{
                                        myToast("gagal register"+task.getException());
                                    }
                                }
                            }
                    );
                }



                break;
            case R.id.btn_reset_password:
                break;
            case R.id.sign_in_button:
                myIntent(LoginActivity.class);
                break;
        }
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