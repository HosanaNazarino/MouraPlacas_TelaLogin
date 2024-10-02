package com.example.mouraplacaslg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.mViewHolder.editLogin = findViewById(R.id.edit_login);
        this.mViewHolder.editPassword = findViewById(R.id.edit_password);
        this.mViewHolder.textLogin = findViewById(R.id.text_login);
        this.mViewHolder.textPassword = findViewById(R.id.text_password);
        this.mViewHolder.buttonEnter = findViewById(R.id.button_enter);

        this.mViewHolder.buttonEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
         if (view.getId() == R.id.button_enter) {
            String login = this.mViewHolder.editLogin.getText().toString();
            if("".equals(login)){
                Toast.makeText(this,  this.getString(R.string.informe_o_login_e_senha), Toast.LENGTH_LONG).show();
             } else {
                
            }
         }
    }

    private static class ViewHolder{
        EditText editLogin;
        EditText editPassword;
        TextView textLogin;
        TextView textPassword;
        Button buttonEnter;

    }
}