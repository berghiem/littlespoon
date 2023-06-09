package ruby.com.littlespoon.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ruby.com.littlespoon.R;
import ruby.com.littlespoon.model.Recipe;
import ruby.com.littlespoon.model.RecipeComments;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {


    private List<RecipeComments> items;


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public TextView commentContent;
        public ImageView delete;
        public TextView reply;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            commentContent = (TextView) itemView.findViewById(R.id.commentcontent);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            reply = (TextView) itemView.findViewById(R.id.reply);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.comment,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
