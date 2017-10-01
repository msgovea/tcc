package com.mgovea.urmusic.posts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.util.ArrayList;
import java.util.List;

import com.mgovea.urmusic.R;;

public class ExampleActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private QuestionsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        ImagePipelineConfig config = ImagePipelineConfig
                .newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);

        //the text to show when there's no selected items

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //mRecyclerView.setAdapter(mAdapter = new QuestionsAdapter(this, getQuestions()));
    }

    private void calculateDiff(final List<Question> oldList, final List<Question> newList) {
        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldList.size();
            }

            @Override
            public int getNewListSize() {
                return newList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
            }
        }).dispatchUpdatesTo(mAdapter);
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
