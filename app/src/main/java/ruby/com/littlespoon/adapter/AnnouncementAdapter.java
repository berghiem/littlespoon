package ruby.com.littlespoon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ruby.com.littlespoon.R;

/**
 * Created by Ruby on 2/15/2018.
 */

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {

    private ImageView[] imageViews;
    public List<String> imageUris;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public ViewHolder(View view) {
            super(view);
            image = (ImageView) view;
        }
    }

    public AnnouncementAdapter(Context context){
        this.context = context;
    }

    @Override
    public AnnouncementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.announcement,parent,false);
        ViewHolder viewHolder = new ViewHolder((ImageView) v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String uri = imageUris.get(position);
        /*
        Picasso.with(context)
                .load(uri)
                .placeholder(R.drawable.giveaway)
                .error(R.drawable.giveaway) //todo
                .into(holder.image);*/

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUris.size();
    }


}
