package nl.danielvdspoel.memory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import nl.danielvdspoel.memory.objects.Card;
import nl.danielvdspoel.memory.objects.Country;

public class MainActivity extends AppCompatActivity {

    Card selectedCard = null;
    ArrayList<Country> countries = new ArrayList<>();
    ArrayList<Country> pickedCountries = new ArrayList<>();
    ArrayList<Card> cards = new ArrayList<>();

    GridLayout gridLayout;
    TextView timePassedView;
    ConstraintLayout startLayout;
    Button startButton;
    Handler handler;
    UpdateTimerEvent updateTimerEvent;

    TextView highScoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.grid);
        startLayout = findViewById(R.id.startLayout);
        startButton = findViewById(R.id.playButton);
        timePassedView = findViewById(R.id.timePassedText);
        highScoreText = findViewById(R.id.highScoreText);

        setupCountries();

        //Pick 6 random countries
        for (int i = 0; i < 6; i++) {
            int randomIndex = (int) (Math.random() * this.countries.size());
            Country country = this.countries.get(randomIndex);
            this.pickedCountries.add(country);
            this.pickedCountries.add(country);
            this.countries.remove(randomIndex);
        }
        Collections.shuffle(this.pickedCountries);

        //Setup cards array
        for (int x = 1; x < 5; x++) {
            for (int y = 1; y < 4; y++) {
                String viewName = "cardR" + x + "C" + y;
                int resId = getResources().getIdentifier(viewName, "id", getPackageName());
                ImageView imageView = findViewById(resId);

                //Pick a random country from selected countries
                int randomIndex = (int) (Math.random() * this.pickedCountries.size());
                Country country = this.pickedCountries.get(randomIndex);
                this.pickedCountries.remove(randomIndex);

                Card card = new Card(imageView, country, x, y);
                imageView.setOnClickListener(v -> {
                    this.cardClicked(card);
                });

                cards.add(card);
            }
        }

        SharedPreferences file = getSharedPreferences("statistics", Context.MODE_PRIVATE);
        int time = file.getInt("highScore", -1);

        if (time == -1) {
            highScoreText.setVisibility(TextView.GONE);
        } else {
            highScoreText.setText("The lowest time ever is: " + DateUtils.formatElapsedTime(time) + "!");
        }

        startButton.setOnClickListener(v -> {
            startLayout.setVisibility(ConstraintLayout.GONE);
            gridLayout.setVisibility(GridLayout.VISIBLE);
            startCounter();
        });
    }

    public void setupCountries() {
        this.countries.add(new Country("Netherlands", "Amsterdam", "dutch", R.drawable.netherlands));
        this.countries.add(new Country("Germany", "Berlin", "german", R.drawable.germany));
        this.countries.add(new Country("France", "Paris", "french", R.drawable.france));
        this.countries.add(new Country("Spain", "Madrid", "spanish", R.drawable.spain));
        this.countries.add(new Country("Italy", "Rome", "italian", R.drawable.italy));
        this.countries.add(new Country("United Kingdom", "London", "british english", R.drawable.united_kingdom));
        this.countries.add(new Country("United States", "Washington D.C.", "american english", R.drawable.united_states));
        this.countries.add(new Country("Russia", "Moscow", "russian", R.drawable.russia));
        this.countries.add(new Country("China", "Beijing", "chinese", R.drawable.china));
        this.countries.add(new Country("Japan", "Tokyo", "japanese", R.drawable.japan));
        this.countries.add(new Country("South Korea", "Seoul", "korean", R.drawable.south_korea));
        this.countries.add(new Country("Brazil", "Brasilia", "portuguese", R.drawable.brazil));
        this.countries.add(new Country("Australia", "Canberra", "australian english", R.drawable.australia));
    }

    public void startCounter() {
        handler = new Handler();
        updateTimerEvent = new UpdateTimerEvent(timePassedView, handler);
        handler.postDelayed(updateTimerEvent, 1000);
    }
    public void cardClicked(Card card) {
        System.out.println("Clicked card at row " + card.x + " and column " + card.y + "!");
        System.out.println("Cart value: " + card.country.name);

        boolean isMatch = false;

        if (selectedCard != null) {
            if (Objects.equals(selectedCard.country.name, card.country.name)) {
                System.out.println("Cards match!");
                isMatch = true;
                selectedCard.imageView.setVisibility(ImageView.INVISIBLE);
                card.imageView.setVisibility(ImageView.INVISIBLE);

                //Remove cards from array
                cards.remove(card);
                cards.remove(selectedCard);

                selectedCard = null;
            } else {
                System.out.println("Cards don't match!");
                selectedCard.imageView.setImageResource(R.drawable.red_back);
                selectedCard = null;
                isMatch = false;
            }
        } else {
            selectedCard = card;
            selectedCard.imageView.setImageResource(R.drawable.blue_back);
        }

        if (cards.size() == 0) {
            Intent i = new Intent(getApplicationContext(), WinActivity.class);
            i.putExtra("timePassed", updateTimerEvent.time);
            handler.removeCallbacks(updateTimerEvent);
            startActivity(i);
        }

        Intent i = new Intent(getApplicationContext(), CountryActivity.class);
        i.putExtra("countryName", card.country.name);
        i.putExtra("countryCapital", card.country.capital);
        i.putExtra("countryLanguage", card.country.language);
        i.putExtra("countryFlag", card.country.flag);
        i.putExtra("isMatch", isMatch);
        startActivity(i);

    }
}