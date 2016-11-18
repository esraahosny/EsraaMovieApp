package firstapp.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by User on 18/11/2016.
 */
public class MovieAdapter2 extends BaseAdapter {
    private Context context;
    private List<Movie> listVideos;

    public MovieAdapter2(Context context , List<Movie> listVideos) {
        super();
        this.context = context;
        this.listVideos = listVideos;

    }

    @Override
    public Object getItem(int position) {
        return listVideos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getCount() {
        return listVideos.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.videos_item, parent, false);
        //view = LayoutInflater.from(context).inflate(R.layout.grid_item, null);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        image.setImageResource(Integer.parseInt("https://api.themoviedb.org/3/movie/"+ listVideos.get(position).getId() + "/videos?api_key=6be3beeecf3e73c7baf052936de346da"));
        Intent i = new Intent(context,Trailer.class);
        i.putExtra("videoid", (Parcelable)image);
        return convertView;
    }

}
