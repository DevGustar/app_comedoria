package com.example.comedoriadatia;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;


public class PasswordRecovery extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText emailRecuperacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);

        auth = FirebaseAuth.getInstance();
        emailRecuperacao = findViewById(R.id.emailRecuperacao);

        Button btnEnviarEmailRecuperacao = findViewById(R.id.btnRecuperarSenha);
        ImageView setaVoltar = findViewById(R.id.setaVoltar);

        btnEnviarEmailRecuperacao.setOnClickListener(v -> {
            String email = emailRecuperacao.getText().toString().trim();

            if (email.isEmpty()) {
                emailRecuperacao.setError("Digite seu e-mail para recuperar a senha");
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailRecuperacao.setError("Digite um e-mail válido");
                return;
            }

            auth.sendPasswordResetEmail(email).addOnSuccessListener(
                    unused -> {
                        Toast.makeText(PasswordRecovery.this, "E-mail enviado. Verifique sua caixa de spam.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(PasswordRecovery.this, LoginActivity.class));
                        finish();
                    }).addOnFailureListener(e ->
                        Toast.makeText(PasswordRecovery.this, "Falha ao enviar e-mail: " + e.getMessage(), Toast.LENGTH_LONG).show());
        });


        setaVoltar.setOnClickListener(v -> {
            startActivity(new Intent(PasswordRecovery.this, LoginActivity.class));
            finish();
        });

    }
}
