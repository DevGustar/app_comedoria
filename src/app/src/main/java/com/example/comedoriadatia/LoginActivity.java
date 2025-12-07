package com.example.comedoriadatia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText loginEmail, loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.editTextEmail);
        loginPassword = findViewById(R.id.editTextPassword);
        Button loginButton = findViewById(R.id.btnLogin);
        TextView signupRedirectText = findViewById(R.id.signupRedirectText);
        TextView recuperarSenha = findViewById(R.id.tvRecuperacaoSenha);

        loginButton.setOnClickListener(v -> {
            String email = loginEmail.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();

            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                if (!password.isEmpty())
                {
                    auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                        Toast.makeText(LoginActivity.this, "Login realizado", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }).addOnFailureListener(e -> Toast.makeText(LoginActivity.this, "Login falhou", Toast.LENGTH_SHORT).show());
                }
                else
                {
                    loginPassword.setError("A senha não pode estar vazia");
                }
            }
            else if (email.isEmpty())
            {
                loginEmail.setError("O e-mail não pode estar vazio");
            }
            else
            {
                loginEmail.setError("Por favor, insira um e-mail válido");
            }
        });

        signupRedirectText.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        });

        recuperarSenha.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, PasswordRecovery.class));
            finish();
        });
    }
}
