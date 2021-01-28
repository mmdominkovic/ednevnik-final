package ba.sum.fpmoz.mim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String loggedRole;
    private FirebaseAuth mAuth;
    private TextView messageTxt;
    private EditText emailInp;
    private EditText passwordInp;
    private Button loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        this.messageTxt = findViewById(R.id.messageTxt);
        this.emailInp = findViewById(R.id.emailInp);
        this.passwordInp = findViewById(R.id.passwordInp);
        this.loginBtn = findViewById(R.id.loginBtn);

        this.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailInp.getText().toString();
                String password = passwordInp.getText().toString();

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            messageTxt.setText("Uspješno ste se prijavili na sustav!");
                            Intent A = new Intent(getApplicationContext(), HomeNavigationActivity.class);
                            Intent S  = new Intent(getApplicationContext(), HomeNavigationStudentActivity.class);
                            Intent P=new Intent(getApplicationContext(),HomeNavigationProfesorActivity.class);
                            String[] dio=email.split("@");
                            String email1=dio[1];
                            if (email.equals("marija.dominkovic@fpmoz.sum.ba"))
                            { startActivity(A);} else if(email.equals("fpmoz.sum.ba")){
                                startActivity(P);
                            }else{
                                startActivity(S);
                            }
                        }
                        else {
                            System.out.println(task.getException());
                            messageTxt.setText("Nastao je problem sa prijavom");
                        }
                    }
                });

            }
        });
    }
}