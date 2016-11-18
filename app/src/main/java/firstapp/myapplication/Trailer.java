package firstapp.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Trailer extends Activity {

      WebView videoScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        //Bundle bundle = Trailer.getIntent().getExtras();
       // String trailer = bundle.getString("t");

        videoScreen = (WebView)findViewById(R.id.video);
        Bundle bundle = this.getIntent().getExtras();
        String video = bundle.getString("videoid");
        videoScreen.loadUrl(video);

    }

}
