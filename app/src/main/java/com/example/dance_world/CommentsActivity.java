package com.example.dance_world;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dance_world.database.DatabaseHelper;
import com.example.dance_world.database.entities.Comment;
import com.example.dance_world.database.entities.Favorites;
import com.example.dance_world.database.entities.Festival;
import com.example.dance_world.database.entities.User;

import java.time.LocalDateTime;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    EditText inputComment;
    ImageButton sendComment;
    Long FestivalId;
    ListView listComments;
    private DatabaseHelper helper;
    MyAdapter adapter;
    String userNames[];
    String dateTimes[];
    String userImages[];
    String comments[];
    Comment com;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        helper = DatabaseHelper.getInstance(this);

        inputComment = findViewById(R.id.inputComment);
        sendComment = findViewById(R.id.sendComment);
        listComments = findViewById(R.id.listComments);

        FestivalId = getIntent().getLongExtra("FestivalId", 0);

        List<Comment> AllComments = helper.CommentDAO().findCommentsForFestival(FestivalId);

        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(inputComment.getText().toString()!=""){
                        User user = helper.UserDao().getLoggedInUser(true);
                        LocalDateTime time = LocalDateTime.now();
                        String text = inputComment.getText().toString();
                        Comment comment = new Comment(time.toString(),text,user.id, FestivalId);
                        helper.CommentDAO().insertComment(comment);
                        onRestart();
                    }
            }
        });

        userNames = new String[AllComments.size()];
        dateTimes = new String[AllComments.size()];
        userImages = new String[AllComments.size()];
        comments = new String[AllComments.size()];

        if(AllComments!=null) {
            for (int i = 0; i < AllComments.size(); i++) {
                User user = helper.UserDao().getUserById(AllComments.get(i).id_user);
                userNames[i] = user.name;
                //int id = getResources().getIdentifier(user.getImage(), "drawable", getPackageName());
                Log.i("aaaaa", user.getImage());
                userImages[i] = user.getImage();
                dateTimes[i] = AllComments.get(i).datetime;
                comments[i]=AllComments.get(i).content;
            }
        }

        adapter = new MyAdapter(this, userNames, dateTimes, userImages, comments);
        listComments.setAdapter(adapter);

    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rNames[];
        String rDateTime[];
        String rImg[];
        String rComments[];

        MyAdapter(Context c, String title[],String description[], String img[], String comments[]) {
            super(c, R.layout.row_favorites,R.id.name, title);
            this.context = c;
            this.rNames = title;
            this.rDateTime = description;
            this.rImg = img;
            this.rComments = comments;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.all_comments_layout, parent, false);
            TextView myTitle = row.findViewById(R.id.commentUsername);
            ImageView images = row.findViewById(R.id.userImage);
            TextView myDateTime= row.findViewById(R.id.commentDateTime);
            TextView myComment= row.findViewById(R.id.comment_text);

            Uri imgUri=Uri.parse(rImg[position]);
            images.setImageURI(imgUri);
            myTitle.setText(rNames[position]);
            myDateTime.setText(rDateTime[position]);
            myComment.setText(rComments[position]);

            return row;
        }
    }

    @Override
    protected void onRestart() {
        inputComment.setText("");
        this.recreate();
        super.onRestart();
    }

}
