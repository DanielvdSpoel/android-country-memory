package nl.danielvdspoel.memory;

import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import android.os.Handler;

public class UpdateTimerEvent implements Runnable {

    Integer time = 0;
    TextView textView;
    Handler handler;

    public UpdateTimerEvent(TextView text, Handler handler) {
        this.textView = text;
        this.handler = handler;
    }

    @Override
    public void run() {
        time++;
        textView.setText("Time passed: " + DateUtils.formatElapsedTime(time));
        handler.postDelayed(this, 1000);
    }
}
