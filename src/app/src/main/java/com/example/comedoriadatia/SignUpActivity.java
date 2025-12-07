package com.example.comedoriadatia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private EditText signupNome, signupRA, signupTelefone, signupEmail, signupPassword, signupConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        signupNome = findViewById(R.id.editTextNome);
        signupRA = findViewById(R.id.editTextRA);
        signupTelefone = findViewById(R.id.editTextTelefone);
        signupEmail = findViewById(R.id.editTextEmail);
        signupPassword = findViewById(R.id.editTextPassword);
        signupConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button signupButton = findViewById(R.id.btnCadastro);
        TextView loginRedirectText = findViewById(R.id.loginRedirectText);

        signupButton.setOnClickListener(v -> {

            String nome = signupNome.getText().toString().trim();
            String RA = signupRA.getText().toString().trim();
            String telefone = signupTelefone.getText().toString().trim();
            String email = signupEmail.getText().toString().trim();
            String password = signupPassword.getText().toString().trim();
            String confirmPassword = signupConfirmPassword.getText().toString().trim();

            // Validação básica
            if (nome.isEmpty()) {
                signupNome.setError("O nome não pode estar vazio");
                return;
            }
            if (RA.isEmpty()) {
                signupRA.setError("O RA não pode estar vazio");
                return;
            }
            if (telefone.isEmpty()) {
                signupTelefone.setError("O telefone não pode estar vazio");
                return;
            }
            if (email.isEmpty()) {
                signupEmail.setError("O e-mail não pode estar vazio");
                return;
            }
            if (password.isEmpty()) {
                signupPassword.setError("A senha não pode estar vazia");
                return;
            }
            if (confirmPassword.isEmpty()) {
                signupConfirmPassword.setError("A confirmação não pode estar vazia");
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(SignUpActivity.this, "As senhas estão diferentes", Toast.LENGTH_SHORT).show();
                signupConfirmPassword.setError("As senhas estão diferentes");
                return;
            }

            // Criação do usuário no Firebase Auth
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            assert auth.getCurrentUser() != null;
                            String uid = auth.getCurrentUser().getUid();

                            // Mapa com os dados do usuário
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("nome", nome);
                            userData.put("ra", RA);
                            userData.put("telefone", telefone);
                            userData.put("email", email);
                            userData.put("tipo", "aluno");
                            userData.put("criadoEm", FieldValue.serverTimestamp());

                            // Salvando no Firestore
                            db.collection("users")
                                    .document(uid)
                                    .set(userData)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(SignUpActivity.this, "Cadastro realizado", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(SignUpActivity.this, "Falha ao salvar dados no banco", Toast.LENGTH_SHORT).show());

                        } else {
                            Toast.makeText(SignUpActivity.this, "Cadastro falhou", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        loginRedirectText.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });
    }
}
