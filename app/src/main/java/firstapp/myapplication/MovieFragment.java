package firstapp.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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
 * A placeholder fragment containing a simple view.
 */
public class MovieFragment extends Fragment {

    public firstapp.myapplication.MovieAdapter1 movieAdapter1;
    public List<firstapp.movie_app.Movie> arrayList = new ArrayList<>();
    public GridView gridView;

    public MovieFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragmentlayout, container, false);
        View v = inflater.inflate(R.layout.fragmentlayout, container, false);
        gridView = (GridView) v.findViewById(R.id.gridView);
        movieAdapter1 = new firstapp.movie_app.MovieAdapter1(getActivity(), arrayList);
        gridView.setAdapter(movieAdapter1);
        // updateMovie();
        //JsonTask movieTask = new JsonTask();
        //movieTask.execute();
        TopRated();
        MostPopular();
        //onOptionsItemSelected();

        //on clicking item of grid view
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                firstapp.movie_app.Movie movie = arrayList.get(position);
                String image = movie.getPosterPath();
                String overview = movie.getOverview();
                String release_date = movie.getRelease_date();
                String title = movie.getTitle();
                String vote_count = movie.getPopularity();
                String video = movie.getVideo();
                String ids = movie.getId();



                // listener.setSelectedPosterPath(image);
                Intent intent = new Intent(getActivity(), firstapp.movie_app.Detail.class);
                intent.putExtra("i", image);//i=Key , image = value)
                //i should put all images in arraylist
                // intent.putExtra("imageID", ImageArray[position]);
                intent.putExtra("o", overview);
                intent.putExtra("r", release_date);
                intent.putExtra("t", title);
                intent.putExtra("v", vote_count);
                intent.putExtra("video", video);
                intent.putExtra("id", ids);

                getActivity().startActivity(intent);
            }
        });
        return v;
    }

//    public void updateMovie() {
//        JsonTask movieTask = new JsonTask();
//        movieTask.execute("https://api.themoviedb.org/3/movie/popular?api_key=6be3beeecf3e73c7baf052936de346da");
//    }

    public void TopRated() {
        JsonTask movieTask = new JsonTask();
        movieTask.execute("https://api.themoviedb.org/3/movie/top_rated?api_key=6be3beeecf3e73c7baf052936de346da");
    }

    public void MostPopular() {
        JsonTask movieTask = new JsonTask();
        movieTask.execute("https://api.themoviedb.org/3/movie/popular?api_key=6be3beeecf3e73c7baf052936de346da");
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }

    public class JsonTask extends AsyncTask<String, Void, List<firstapp.movie_app.Movie>> {
        private final String LOG = JsonTask.class.getSimpleName();
        //variables & declarations
        private firstapp.movie_app.MovieAdapter1 movieAdapter1;
        private View view;


//        public JsonTask(MovieAdapter1 movieAdapter1, View view) {
//            this.movieAdapter1 = movieAdapter1;
//            this.view = view;
//        }

        //doInBackground part contains connection + url of api + exception handlers
        protected List<firstapp.movie_app.Movie> doInBackground(String... params) {

            //initialization of variables
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String finaljson = null;
            try {
                URL url = new URL(params[0]);
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

        private List<firstapp.movie_app.Movie> getData(String jsontoString) throws JSONException {

            JSONObject movieJson = new JSONObject(jsontoString);
            JSONArray movieArray = movieJson.getJSONArray("results");

            //here we write all the data to be string and showed in movie details
            final String posterPath = "poster_path";
            final String title = "title";
            final String vote_count = "vote_count";
            final String release_date = "release_date";
            final String overview = "overview";

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject finalObject = movieArray.getJSONObject(i);
                firstapp.movie_app.Movie movie = new firstapp.movie_app.Movie();


                movie.setPosterPath(finalObject.getString(posterPath));
                movie.setTitle(finalObject.getString(title));
                movie.setVote_count(finalObject.getString(vote_count));
                movie.setRelease_date(finalObject.getString(release_date));
                movie.setOverview(finalObject.getString(overview));

                arrayList.add(movie);
            }

//            ArrayList<String> Urls = new ArrayList<String>();
//            for (int i = 0; i < Urls; i++) {
//                JSONObject finalObject = movieArray.getJSONObject(i);
//                movie.setPosterPath(finalObject.getString("poster_path"));
//                ImageArray[i];
//            }

            return arrayList;
        }

//        @Override
//        protected void onPostExecute(List<Movie> movies) {
//            super.onPostExecute(arrayList);
//            movieAdapter1.notifyDataSetChanged();
//
//        }
    }
//

    @Override public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_top_rated:
                arrayList.clear();
                TopRated();
                break;
            case R.id.action_most_popular:
                MostPopular();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
