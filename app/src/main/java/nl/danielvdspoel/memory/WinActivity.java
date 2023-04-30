package nl.danielvdspoel.memory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WinActivity extends AppCompatActivity {

    TextView descriptionText;
    TextView beatenRecordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        descriptionText = findViewById(R.id.descriptionText);
        beatenRecordText = findViewById(R.id.beatenRecordText);

        //get score from bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int timePassed = bundle.getInt("timePassed");
            descriptionText.setText("You have finished the game! You took " + DateUtils.formatElapsedTime(timePassed) + " to match all cards!");


            SharedPreferences file = getSharedPreferences("statistics", Context.MODE_PRIVATE);
            int time = file.getInt("highScore", -1);

            if (time == -1 || time > timePassed) {

                beatenRecordText.setText("You beat the last record of " + DateUtils.formatElapsedTime(time) + "!");

                SharedPreferences.Editor editor = file.edit();
                editor.putInt("highScore", timePassed);
                editor.apply();
            }
        }




    }
}