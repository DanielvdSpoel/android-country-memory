package nl.danielvdspoel.memory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryActivity extends AppCompatActivity {

    TextView countryName;
    TextView countryCapital;
    TextView countryLanguage;
    TextView countryLanguageLabel;
    ImageView countryFlag;

    TextView matchText;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        countryName = findViewById(R.id.countryName);
        countryCapital = findViewById(R.id.countryCapital);
        countryLanguage = findViewById(R.id.countryLanguage);
        countryLanguageLabel = findViewById(R.id.languageLabel);
        countryFlag = findViewById(R.id.countryFlag);

        matchText = findViewById(R.id.matchText);
        backButton = findViewById(R.id.backButton);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            countryName.setText(bundle.getString("countryName"));
            countryCapital.setText(bundle.getString("countryCapital"));
            countryLanguage.setText(bundle.getString("countryLanguage"));
            countryLanguageLabel.setText("People in " + bundle.getString("countryName") + " speak:");
            countryFlag.setImageResource(bundle.getInt("countryFlag"));

            if (bundle.getBoolean("isMatch")) {
                matchText.setText("You found a match!");
                matchText.setTextColor(Color.parseColor("#00FF00"));
            } else {
                matchText.setText("You didn't find a match.");
                matchText.setTextColor(Color.parseColor("#FF0000"));
            }
        }

        backButton.setOnClickListener(v -> finish());
    }
}