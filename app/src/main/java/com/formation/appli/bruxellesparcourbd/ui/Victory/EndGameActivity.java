package com.formation.appli.bruxellesparcourbd.ui.Victory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.formation.appli.bruxellesparcourbd.R;
import com.formation.appli.bruxellesparcourbd.ui.User.UserActivity;

public class EndGameActivity extends AppCompatActivity {

    private Button btn_retourAcceuil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initView();
    }

    private void initView(){
        btn_retourAcceuil = (Button) findViewById(R.id.btn_end_game_button);
        btn_retourAcceuil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retourAcceuil();
            }
        });
    }

    private void retourAcceuil(){
        Intent retourUserChoiceIntent = new Intent(this, UserActivity.class);
        startActivity(retourUserChoiceIntent);
    }
}
