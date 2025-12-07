package com.example.comedoriadatia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signupNome, signupRA, signupTelefone, signupEmail, signupPassword, signupConfirmPassword;
    private Button signupButton;
    private TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Firebase Login e Cadastro
        auth = FirebaseAuth.getInstance();
        signupNome = findViewById(R.id.editTextNome);
        signupRA = findViewById(R.id.editTextRA);
        signupTelefone = findViewById(R.id.editTextTelefone);
        signupEmail = findViewById(R.id.editTextEmail);
        signupPassword = findViewById(R.id.editTextPassword);
        signupConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        signupButton = findViewById(R.id.btnCadastro);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = signupNome.getText().toString().trim();
                String RA = signupRA.getText().toString().trim();
                String telefone = signupTelefone.getText().toString();
                String email = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                String confirmPassword = signupConfirmPassword.getText().toString().trim();

                if (nome.isEmpty())
                {
                    signupNome.setError("O nome não pode estar vazio");
                }
                if (RA.isEmpty())
                {
                    signupRA.setError("O RA não pode estar vazio");
                }
                if (telefone.isEmpty())
                {
                    signupTelefone.setError("O telefone não pode estar vazio");
                }
                if (email.isEmpty())
                {
                    signupEmail.setError("O e-mail não pode estar vazio");
                }
                if (password.isEmpty())
                {
                    signupPassword.setError("A senha não pode estar vazia");
                }
                if (confirmPassword.isEmpty())
                {
                    signupConfirmPassword.setError("A confirmação não pode estar vazia");
                }

                if (password.equals(confirmPassword))
                {
                    if (!email.isEmpty() && !password.isEmpty())
                    {
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(SignUpActivity.this, "Cadastro realizado", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(SignUpActivity.this, "Cadastro falhou", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "As senhas estão diferentes", Toast.LENGTH_SHORT).show();
                    signupConfirmPassword.setError("As senhas estão diferentes");
                }
            }
        });

        loginRedirectText.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });
    }
}
