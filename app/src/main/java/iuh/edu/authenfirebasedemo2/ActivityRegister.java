package iuh.edu.authenfirebasedemo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class ActivityRegister extends AppCompatActivity {

    private EditText txtName;
    private EditText txtMail;
    private EditText txtPassword;
    private EditText txtRepassword;
    private ProgressBar progressBar;

    private Button btnRegister;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter);

        auth =  FirebaseAuth.getInstance();


        txtName = findViewById(R.id.editTextTextEmailAddress2);
        txtMail = findViewById(R.id.editTextTextEmailAddress3);
        txtPassword = findViewById(R.id.editTextTextPassword2);
        txtRepassword = findViewById(R.id.editTextTextPassword3);

        progressBar = findViewById(R.id.progressBar);

        btnRegister = findViewById(R.id.button4);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString().trim();
                String email = txtMail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String repassword = txtRepassword.getText().toString().trim();

                User user = new User(email,name);

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(),"Enter your name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(),"Enter your email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(),"Enter your password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(repassword)) {
                    Toast.makeText(getApplicationContext(),"Enter your password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(repassword)) {
                    Toast.makeText(getApplicationContext(),"Repassword is wrong!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(ActivityRegister.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(ActivityRegister.this,"createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        
                        if (!task.isSuccessful()) {
                            Toast.makeText(ActivityRegister.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ActivityRegister.this, "Register account complied !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ActivityRegister.this,MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}