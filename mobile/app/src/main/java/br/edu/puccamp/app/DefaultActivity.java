package br.edu.puccamp.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.util.ArrayList;
import java.util.List;

import br.edu.puccamp.app.posts.Question;
import br.edu.puccamp.app.posts.QuestionsAdapter;
import br.edu.puccamp.app.util.Strings;

public class DefaultActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private RecyclerView mRecyclerView;
    private QuestionsAdapter mAdapter;
    private AppCompatImageView mIcon;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(DefaultActivity.this, LinearLayoutManager.VERTICAL, false));
                    mRecyclerView.setAdapter(mAdapter = new QuestionsAdapter(DefaultActivity.this, getQuestions()));
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        mRecyclerView = (RecyclerView) findViewById(R.id.listPosts);
        mTextMessage = (TextView) findViewById(R.id.message);
        mIcon = (AppCompatImageView) findViewById(R.id.iconAlarm);
        mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(Strings.USUARIO, MODE_PRIVATE);
                prefs.edit().clear().apply();
                startActivity(new Intent(DefaultActivity.this, MainActivity.class));
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ImagePipelineConfig config = ImagePipelineConfig
                .newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);


    }

    private List<Question> getQuestions() {
        return new ArrayList<Question>() {{
            add(new Question("Paloma Silva", "Graphic Designer",
                    "http://kingofwallpapers.com/girl/girl-011.jpg", "Nov 20, 6:12 PM",
                    "What is the first step to transform an idea into an actual project?"));
            add(new Question("Paloma Silva", "Project Manager",
                    "http://weknowyourdreams.com/images/girl/girl-03.jpg", "Nov 20, 3:48 AM",
                    "What is your biggest frustration with taking your business/career (in a corporate) to the next level?"));
            add(new Question("Janaine Cristina", "iOS Developer",
                    "http://www.viraldoza.com/wp-content/uploads/2014/03/8876509-lily-pretty-girl.jpg", "Nov 20, 6:12 PM",
                    "What is the first step to transform an idea into an actual project?"));
            add(new Question("Janaine Cristina", "QA Engineer",
                    "http://kingofwallpapers.com/girl/girl-019.jpg", "Nov 20, 6:12 PM",
                    "What is the first step to transform an idea into an actual project?"));
            add(new Question("Mateus Govêa", "Android Developer",
                    "https://scontent.fcpq3-1.fna.fbcdn.net/v/t1.0-9/11918928_1012801065406820_5528279907234667073_n.jpg?oh=1afd1268531b58274fd34090bc90d46c&oe=598B0484", "Nov 20, 6:12 PM",
                    "What is the first step to transform an idea into an actual project?"));
        }};
    }

}
