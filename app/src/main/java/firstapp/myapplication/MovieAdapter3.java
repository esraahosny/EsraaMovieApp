package firstapp.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 18/11/2016.
 */
public class MovieAdapter3  extends BaseAdapter {
    private Context context;
    private List<Movie> listReviews;

    public MovieAdapter3(Context context, List<Movie> listReviews) {
        this.context = context;
        this.listReviews = listReviews;
    }

    @Override
    public int getCount() {
        return listReviews.size();
    }

    @Override
    public Object getItem(int position) {
        return listReviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.videos_item, parent, false);
        //view = LayoutInflater.from(context).inflate(R.layout.grid_item, null);
        TextView textView= (TextView) convertView.findViewById(R.id.textView);
        textView.setText(Integer.parseInt("https://api.themoviedb.org/3/movie/"+ listReviews.get(position).getId() + "/reviews?api_key=6be3beeecf3e73c7baf052936de346da"));
        return convertView;
    }
    }

