package nl.danielvdspoel.memory.objects;

import android.widget.ImageView;

public class Card {

    public ImageView imageView;
    public Country country;
    public int x;
    public int y;

    public Card(ImageView imageView, Country country, int x, int y) {
        this.imageView = imageView;
        this.country = country;
        this.x = x;
        this.y = y;
    }
}
