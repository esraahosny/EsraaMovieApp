package firstapp.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieFragment movieFragment = new MovieFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.activity, movieFragment).commit();


    }

}
