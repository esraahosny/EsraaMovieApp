package firstapp.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

/**
 * Created by User on 25/10/2016.
 */
public class DetailFragment extends Fragment {
    public MovieAdapter2 movieAdapter2;
    public List<TrailerData> listVideos = new ArrayList<>();
    public MovieAdapter3 movieAdapter3;
    public List<ReviewsData> listReviews = new ArrayList<>();
    //public List<Movie> list = new ArrayList<>();
    //DataBaseFavourites dbf ;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.detail_item, container, false);
      Intent intent=getActivity().getIntent();       //intent Bundle to get data
      String Movie_id= intent.getExtras().getString("id");
       // Log.i("id",Movie_id);
        // image of each movie poster
        ImageView posterpath = (ImageView) view.findViewById(R.id.posterpathtv);
        //text views of info from grid view
        TextView title = (TextView) view.findViewById(R.id.titletv);
        final TextView release_date = (TextView) view.findViewById(R.id.release_datetv);
        TextView overview = (TextView) view.findViewById(R.id.overviewtv);
        TextView voteAverage = (TextView) view.findViewById(R.id.vote_average);
        ListView lvTrailers = (ListView) view.findViewById(R.id.listViewVideo);
        final TextView tvReviewData = (TextView) view.findViewById(R.id.textViewReviews);
        TextView reviewText = (TextView) view.findViewById(R.id.reviewText);


        // star button
        final Button starbutton = (Button) view.findViewById(R.id.starbutton);
        starbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //save movie in favourite list
            }
        });


        movieAdapter2 = new MovieAdapter2(getActivity(), listVideos);
        lvTrailers.setAdapter(movieAdapter2);
        JsonTask2 jsonTask2 = new JsonTask2();
        jsonTask2.execute(Movie_id);
        lvTrailers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrailerData trailerData = listVideos.get(position);
                String SemiPartVideoUrl = trailerData.getKey();
                startActivity(new Intent( Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+SemiPartVideoUrl)));
            }
        });

//        movieAdapter3 = new MovieAdapter3(getActivity(), listReviews);
//        tvReview.setAdapter(movieAdapter3);
//        JsonTask3 jsonTask3 = new JsonTask3();
//        jsonTask3.execute(Movie_id);
//        tvReview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//      @Override
//      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        ReviewsData reviewsData = listReviews.get(position);
//          String content = reviewsData.getContent();
//          jsonTask3.execute("https://api.themoviedb.org/3/movie/" + content + "/reviews?api_key=6be3beeecf3e73c7baf052936de346da");
//      }
//    });

        reviewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewsData reviewsData = new ReviewsData();
                Intent intent=getActivity().getIntent();       //intent Bundle to get data
                String Movie_id= intent.getExtras().getString("id");
                movieAdapter3 = new MovieAdapter3(getActivity(), listReviews);
               // tvReview.setAdapter(movieAdapter3);
                JsonTask3 jsonTask3 = new JsonTask3();
                jsonTask3.execute("https://api.themoviedb.org/3/movie/" + Movie_id + "/reviews?api_key=6be3beeecf3e73c7baf052936de346da");
                String content = reviewsData.getContent();
                tvReviewData.setText(content);
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

    public class JsonTask2 extends AsyncTask<String, Void, List<TrailerData>> {
        private final String LOG = JsonTask2.class.getSimpleName();
        //variables & declarations

        //doInBackground part contains connection + url of api + exception handlers
        protected List<TrailerData> doInBackground(String... params) {

            //initialization of variables
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String finaljson = null;
            String id=params[0];
            try {
                URL url = new URL("https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=6be3beeecf3e73c7baf052936de346da");
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


        private List<TrailerData> getData(String jsontoString) throws JSONException {

            JSONObject movieJson = new JSONObject(jsontoString);
            JSONArray movieArray = movieJson.getJSONArray("results");

            //here we write all the data to be string and showed in movie details
            final String TrailerName = "name";
            final String TrailerID = "id";
            final String key = "key";
            for (int i = 0; i < movieArray.length(); i++) {

                JSONObject finalObject = movieArray.getJSONObject(i);
                TrailerData td = new TrailerData();
                td.setTrailer_name(finalObject.getString(TrailerName));
                td.setTrailer_id(finalObject.getString(TrailerID));
                td.setKey(finalObject.getString(key));
                listVideos.add(td);
                Log.v("name",TrailerName);
                Log.v("id",TrailerID);
                Log.v("key",key);
            }

            return listVideos;

        }

//        @Override
//        protected void onPostExecute(List<TrailerData> trailerDatas) {
//            super.onPostExecute(trailerDatas);
//            movieAdapter2.notifyDataSetChanged();
//           // movieAdapter2 = new MovieAdapter2(getActivity(), listVideos);
//        }
    }
    public class JsonTask3 extends AsyncTask<String, Void, List<ReviewsData>> {
        private final String LOG = JsonTask2.class.getSimpleName();
        //variables & declarations

        //doInBackground part contains connection + url of api + exception handlers
        protected List<ReviewsData> doInBackground(String... params) {

            //initialization of variables
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String finaljson = null;
            String id=params[0];
            try {
                URL url = new URL("https://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=6be3beeecf3e73c7baf052936de346da");
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

        private List<ReviewsData> getData(String jsontoString) throws JSONException {

            JSONObject movieJson = new JSONObject(jsontoString);
            JSONArray movieArray = movieJson.getJSONArray("results");

            //here we write all the data to be string and showed in movie details
            final String ReviewContent = "content";
            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject finalObject = movieArray.getJSONObject(i);
                ReviewsData r= new ReviewsData();
                r.setContent(finalObject.getString(ReviewContent));
                listReviews.add(r);

            }

            return listReviews;

        }

//        @Override
//        protected void onPostExecute(List<ReviewsData> reviewsDatas) {
//            super.onPostExecute(reviewsDatas);
//            movieAdapter3.notifyDataSetChanged();
//        }
    }
}

