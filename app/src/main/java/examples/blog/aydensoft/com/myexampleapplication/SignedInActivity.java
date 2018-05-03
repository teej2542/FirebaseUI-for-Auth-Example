package examples.blog.aydensoft.com.myexampleapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static com.firebase.ui.auth.util.ExtraConstants.IDP_RESPONSE;

public class SignedInActivity extends AppCompatActivity {

    TextView userEmail;
    Button signout;

    public static Intent createIntent(
            Context context,
            IdpResponse idpResponse) {

        Intent startIntent = new Intent();
        if (idpResponse != null) {
            startIntent.putExtra(IDP_RESPONSE, idpResponse);
        }

        return startIntent.setClass(context, SignedInActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in);

        userEmail = findViewById(R.id.userEmail);
        signout = findViewById(R.id.signout);

                FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            userEmail.setText(auth.getCurrentUser().getEmail());
            Log.d("CURRENT_USER: ", auth.getCurrentUser().getEmail());
        }

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance()
                        .signOut(getApplicationContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                startActivity(new Intent(SignedInActivity.this, MainActivity.class));
                                finish();
                            }
                        });
            }
        });
    }
}
