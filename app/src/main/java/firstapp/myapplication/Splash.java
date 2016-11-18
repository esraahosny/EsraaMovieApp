package firstapp.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Splash extends AppCompatActivity {

    Button button;
    ImageView imageView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
            }
        });

         //imageView1.setImageResource(R.drawable.splash);


        //to make image pale
        //imageView1.setAlpha(130);


        // these 2 picassos' are for images from json api & any image from website as a test

        Picasso.with(Splash.this).load("http://www.dogsgossip.com/image-files/dogs-in-movies.gif").into(imageView1);
        // Picasso.with(Splash.this).load("http://image.tmdb.org/t/p/w185//jLRllZsubY8UWpeMyDLVXdRyEWi.jpg").into(imageView1);


    }}


       //    /IfB9hy4JH1eH6HEfIgIGORXi5h.jpg
        //   /nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
        //   /5N20rQURev5CNDcMjHVUZhpoCNC.jpg
        //   /mLrQMqyZgLeP8FrT5LCobKAiqmK.jpg
        //   /jjBgi2r5cRt36xF6iNUEhzscEcb.jpg
        //   /z09QAf8WbZncbitewNk6lKYMZsh.jpg
        //   /e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg
        //   /zSouWWrySXshPCT4t3UKCQGayyo.jpg
        //   /oFOG2yIRcluKfTtYbzz71Vj9bgz.jpg
        //   /gj282Pniaa78ZJfbaixyLXnXEDI.jpg
        //   /6FxOPJ9Ysilpq0IgkrMJ7PubFhq.jpg

