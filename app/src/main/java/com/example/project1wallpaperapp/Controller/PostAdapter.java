package com.example.project1wallpaperapp.Controller;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project1wallpaperapp.ImageFullScreen;
import com.example.project1wallpaperapp.Model.Item;
import com.example.project1wallpaperapp.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    Context context;
    List<Item> postList;

    private Item mItem;

    private final SharedPreferences preferences;
    SharedPreferences.Editor mEditor;

    private  boolean clicked = true;

    public PostAdapter(Context context , List<Item> postList){
        this.context = context;
        this.postList = postList;
       // mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences = context.getSharedPreferences("sp",MODE_PRIVATE);
        mEditor = preferences.edit();
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(context).inflate(R.layout.eachpost , parent , false);
        return new PostHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, @SuppressLint("RecyclerView") int position) {

        Item item = postList.get(position);
        holder.setImageView(item.getImageUrl());
        holder.setmTags(item.getTags());
        holder.setmLikes(item.getLikes());
        holder.setFavImg(item.isClicked());


        if(preferences.contains(item.getImageUrl())){
            holder.likeButton.setImageResource(R.drawable.filled_heart);
        }else {
            holder.likeButton.setImageResource(R.drawable.heart);
        }

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(clicked){
                    holder.likeButton.setImageResource(R.drawable.filled_heart);
                    clicked = false;
                    mEditor.putString(item.getImageUrl(),"");
                }else{
                    holder.likeButton.setImageResource(R.drawable.heart);
                    clicked = true;
                    mEditor.remove(item.getImageUrl());
                }
                mEditor.apply();
            }

        });


        // code for sending images to next screen
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ImageFullScreen.class);
                intent.putExtra("image",item.getImageUrl());
                intent.putExtra("name",item.getTags());

                // setFlag is used because we are in inner class
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView mLikes, mTags;
        View view;
        ImageButton likeButton;

        SwitchCompat swtch;
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;


        }

        public void setImageView(String url){
            imageView = view.findViewById(R.id.imageview);
            Glide.with(context).load(url).into(imageView);
        }
        public void setmLikes(int likes){
            mLikes = view.findViewById(R.id.likes);
            mLikes.setText(likes + " Likes");
        }
        public void setmTags(String tag){
            mTags = view.findViewById(R.id.tags);
            mTags.setText(tag);
        }
        public void setFavImg(boolean isClicked){
            likeButton = view.findViewById(R.id.likeButton);
        }






    }
}