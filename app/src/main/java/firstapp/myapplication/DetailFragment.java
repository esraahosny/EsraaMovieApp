package firstapp.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import firstapp.myapplication.R;
import firstapp.myapplication.Trailer;

/**
 * Created by User on 25/10/2016.
 */
public class DetailFragment extends Fragment {
  public firstapp.myapplication.MovieAdapter2 movieAdapter2;
  public List<Movie> listVideos = new ArrayList<>();
  public List<Movie> listReviews = new ArrayList<>();
  public List<Movie> list = new ArrayList<>();
 DataBaseFavourites dbf ;

  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.detail_item, container, false);

    //intent Bundle to get data

    // image of each movie poster
    ImageView posterpath = (ImageView) view.findViewById(R.id.posterpathtv);
    //text views of info from grid view
    TextView title = (TextView) view.findViewById(R.id.titletv);
    final TextView release_date = (TextView) view.findViewById(R.id.release_datetv);
    TextView overview = (TextView) view.findViewById(R.id.overviewtv);
    TextView voteAverage = (TextView) view.findViewById(R.id.vote_average);
    final ImageView videos = (ImageView) view.findViewById(R.id.imageView2);
    final TextView reviews = (TextView) view.findViewById(R.id.reviewstv);



    // rating bar
    final RatingBar ratingBar = (RatingBar)view.findViewById(R.id.ratingBar);
    ratingBar.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {


                                   }
                                 }
    videos.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //https://api.themoviedb.org/3/movie/329865/videos?api_key=6be3beeecf3e73c7baf052936de346da
        final View view = inflater.inflate(R.layout.videos_item, container, false);
        movieAdapter2 = new firstapp.myapplication.MovieAdapter2(getActivity(), listVideos);
        videos.setAdapter(movieAdapter2);
        JsonTask2 jsonTask2 = new JsonTask2();
        Intent intent = new Intent(getActivity(), Trailer.class);
        Bundle bundle = getActivity().getIntent().getExtras();
        String ids = bundle.getString("id");
        //  imageView.setImageResource(video);
        jsonTask2.execute("https://api.themoviedb.org/3/movie/" + ids + "/videos?api_key=6be3beeecf3e73c7baf052936de346da");
        getActivity().startActivity(intent);
      }
    });


    reviews.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final View view = inflater.inflate(R.layout.reviews_item, container, false);
        reviews = (TextView) view.findViewById(R.id.reviewstv);
        Bundle bundle = getActivity().getIntent().getExtras();
        String ids = bundle.getString("id");
        movieAdapter2 = new firstapp.myapplication.MovieAdapter2(getActivity(), listReviews);
        reviews.setAdapter(movieAdapter2);
        JsonTask2 jsonTask2 = new JsonTask2();
        jsonTask2.execute("https://api.themoviedb.org/3/movie/" + ids + "/reviews?api_key=6be3beeecf3e73c7baf052936de346da");
      }
    });


    // Bundle sentBundle = getArguments();
    // String name = sentBundle.getString("i");


    Bundle bundle = getActivity().getIntent().getExtras();

    String image1 = "http://image.tmdb.org/t/p/w185/" + bundle.getString("i");
    Picasso.with(getContext()).load(image1).into(posterpath);
    //overview
    String overview1 = bundle.getString("o");
    overview.setText(overview1);

    //release date
    String release_date1 = bundle.getString("r");
    release_date.setText(release_date1);

    //title
    String title1 = bundle.getString("t");
    title.setText(title1);

    //vote_count
    String average = bundle.getString("a");
    voteAverage.setText(average);

    return view;
  }

  public class JsonTask2 extends AsyncTask<String, Void, List<Movie>> {
    private final String LOG = JsonTask2.class.getSimpleName();
    //variables & declarations
    private firstapp.myapplication.MovieAdapter1 movieAdapter1;
    private View view;


    //doInBackground part contains connection + url of api + exception handlers
    protected List<Movie> doInBackground(String... params) {

      //initialization of variables
      HttpURLConnection connection = null;
      BufferedReader reader = null;
      String finaljson = null;
      try {
        URL url = new URL(params[0]);
        //"https://api.themoviedb.org/3/movie/popular?api_key=6be3beeecf3e73c7baf052936de346da"
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        InputStream stream = connection.getInputStream();
        reader = new BufferedReader(new InputStreamReader(stream));

        if (stream == null) {
          // Nothing to do.
          return null;
        }
        StringBuffer buffer = new StringBuffer();


        String line;
        while ((line = reader.readLine()) != null) {
          buffer.append(line);
        }
        if (buffer.length() == 0) {
          // Stream was empty.  No point in parsing.
          return null;
        }
        finaljson = buffer.toString();

      } catch (IOException e1) {
        Log.e(LOG, "error", e1);
        return null;

      } finally {
        if (connection != null) {
          connection.disconnect();
        }

        if (reader != null) {
          try {
            reader.close();
          } catch (IOException ioe) {
            //log statement or any message
            Log.e(LOG, "error", ioe);
          }
        }

        try {
          return getData(finaljson);
        } catch (JSONException e) {
          //e.printStackTrace();
          Log.e(LOG, "error", e);
        }
      }
      return null;
    }

    private List<Movie> getData(String jsontoString) throws JSONException {

      JSONObject movieJson = new JSONObject(jsontoString);
      JSONArray movieArray = movieJson.getJSONArray("results");

      //here we write all the data to be string and showed in movie details
      final String ids = "id";
      for (int i = 0; i < movieArray.length(); i++) {
        JSONObject finalObject = movieArray.getJSONObject(i);
        Movie movie = new Movie();
        movie.setPosterPath(finalObject.getString(ids));
        list.add(movie);
      }

      return list;
    }

  }
}

