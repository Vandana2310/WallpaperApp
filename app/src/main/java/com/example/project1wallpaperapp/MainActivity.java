package com.example.project1wallpaperapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.project1wallpaperapp.Controller.PostAdapter;
import com.example.project1wallpaperapp.Model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
    public class MainActivity extends AppCompatActivity {

        private RecyclerView recyclerView;
        private RequestQueue requestQueue;
        private List<Item> mList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            recyclerView = findViewById(R.id.rv);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

            mList = new ArrayList<>();
            fetchData();
        }

        private void fetchData() {

            String url = "https://pixabay.com/api/?key=26185015-bd1bb1367af4d10bcdfb3a017&q=animals&image_type=photo&pretty=true";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("hits");

                        for(int i = 0 ; i<jsonArray.length() ; i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String imageUrl = jsonObject.getString("webformatURL");
                            int likes = jsonObject.getInt("likes");
                            String tags = jsonObject.getString("tags");

                            Item post = new Item(tags,imageUrl  , likes,false);
                            mList.add(post);

                        }

                        PostAdapter adapter = new PostAdapter(MainActivity.this , mList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(jsonObjectRequest);
        }

    }