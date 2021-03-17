package com.example.retrofitclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.retrofitclient.model.Comment;
import com.example.retrofitclient.model.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    MyWebService myWebService;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.message);
        Button run = findViewById(R.id.run);
        Button clear = findViewById(R.id.clear);

        myWebService = MyWebService.retrofit.create(MyWebService.class);

        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // getposts();
//                getComments();
                //createPost();
                // updatePost();
                deletePost();
            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText("");
            }
        });
    }

    private void deletePost() {
        Call<Void> deleteCall = myWebService.deletePost(5);

        deleteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    text.setText(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void updatePost() {
        Post post = new Post(15, "Title", null);

        /*
        Difference between PUT & POST , in PUT whole new object will be created for the given id (for an object
            if body is null it will remain null in the updated object) and in PATCH it will change only the passed
            parameters (if body is null, it will keep the old body for the updated object as well).
         */

        //Put
        //Call<Post> postCall = myWebService.putPost(5,post);

        //Patch
        Call<Post> postCall = myWebService.patch(5,post);

        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    text.setText(String.valueOf(response.code()));
                    showPost(response.body());
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    private void createPost() {

        //Post Through Body
//        Post post = new Post(1,"Post Title", "This is post body");
//        Call<Post> postCall = myWebService.createPost(post);

        //Post through URL
        //Call<Post> postCall = myWebService.createPost(3,"Post Title 2", "Post body 2");

        // Post Through Map
        Map<String, String> postMap = new HashMap<>();
        postMap.put("userId","5");
        postMap.put("title","Map title");
        postMap.put("body","This is body");
        Call<Post> postCall = myWebService.createPost(postMap);

        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    text.setText(String.valueOf(response.code()));
                    showPost(response.body());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    private void getComments() {
        // Method 1
        //Call<ArrayList<Comment>> call = myWebService.getComments("https://jsonplaceholder.typicode.com/posts/13/comments");

        // Method 2
        //Call<ArrayList<Comment>> call = myWebService.getComments(3, null,null );

        // Method 3
//        Call<ArrayList<Comment>> call = myWebService.getComments(3, "id","desc" );

         //Method 5
 //       Call<ArrayList<Comment>> call = myWebService.getComments(new Integer[]{1,3,2}, "id","desc" );

        // Method 4
        Map<String, String> parameters = new HashMap<>();
        parameters.put("postId","5");
        parameters.put("_sort","id");
        parameters.put("_order","desc");

        Call<ArrayList<Comment>> call = myWebService.getComments(parameters);

        call.enqueue(new Callback<ArrayList<Comment>>() {
            @Override
            public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {
                if(response.isSuccessful()){
                    showComments(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {

            }
        });
    }

    private void showComments(ArrayList<Comment> body) {

        for(Comment comment: body) {
            text.append("Id: " + comment.getId() + "\n");
            text.append("postId: " + comment.getPostId() + "\n");
            text.append("user: " + comment.getName() + "\n");
            text.append("body: " + comment.getBody() + "\n\n");
        }
    }

    private void getposts() {
        Call<ArrayList<Post>> call = myWebService.getPosts();

        call.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                if(response.isSuccessful()) {
                    for (Post post : response.body()){
                        showPost(post);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {

            }
        });
    }

    private void showPost(Post post) {
        text.append("\nuserId: " + post.getUserId() + "\n");
        text.append("id: " + post.getId() + "\n");
        text.append("title: " + post.getTitle() + "\n");
        text.append("body: " + post.getBody() + "\n\n");
    }
}