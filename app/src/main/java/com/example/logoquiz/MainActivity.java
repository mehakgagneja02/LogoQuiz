package com.example.logoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.logoquiz.Common.cs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public List<String> suggestSource= new ArrayList<>();
    public GridViewAnswerAdapter answerAdapter;
    public GridViewSuggestAdapter suggestAdapter;

    public Button btnSubmit;

    public GridView gridViewAnswer, gridViewSuggest;

    public ImageView imgViewQuestion;
    int[] image_list={
            R.drawable.android,
            R.drawable.gmail,
            R.drawable.linkedin,
            R.drawable.nike,
            R.drawable.pinterest,
            R.drawable.twitter
    };
    public char[] answer;
    String correct_answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         initView();
    }

    private void initView() {
        gridViewAnswer=findViewById(R.id.gridViewAnswer);
        gridViewSuggest=findViewById(R.id.gridViewSuggest);

        imgViewQuestion=findViewById(R.id.imgLogo);

        setupList();

        btnSubmit=findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result="";
                for (int i=0;i< cs.user_submit_answer.length;i++)
                    result+=String.valueOf(cs.user_submit_answer[i]);
                if(result.equals(correct_answer))
                {
                    Toast.makeText(getApplicationContext(),"Finish ! This is "+result,Toast.LENGTH_SHORT).show();
                   //Reset
                    cs.count=0;
                    cs.user_submit_answer= new char[correct_answer.length()];
                    //Set adapter
                    GridViewAnswerAdapter answerAdapter= new GridViewAnswerAdapter(setupNullList(),getApplicationContext());
                    gridViewAnswer.setAdapter(answerAdapter);
                    answerAdapter.notifyDataSetChanged();

                    GridViewSuggestAdapter suggestAdapter= new GridViewSuggestAdapter(suggestSource,getApplicationContext(),MainActivity.this);
                    gridViewSuggest.setAdapter(suggestAdapter);
                    suggestAdapter.notifyDataSetChanged();

                    setupList();
                }
                else{
                    Toast.makeText(MainActivity.this,"Incorrect !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupList() {
        //Random logo
        Random random = new Random();
        int imageSelected = image_list[random.nextInt(image_list.length)];
        imgViewQuestion.setImageResource(imageSelected);

        correct_answer = getResources().getResourceName(imageSelected);
        correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/") + 1);
        answer = correct_answer.toCharArray();

        cs.user_submit_answer = new char[answer.length];

        suggestSource.clear();
        for (char item : answer) {
            suggestSource.add(String.valueOf(item));
        }
        for (int i = answer.length; i < answer.length * 2; i++) {
            suggestSource.add(cs.alphabet_character[random.nextInt(cs.alphabet_character.length)]);

            Collections.shuffle(suggestSource);

            answerAdapter = new GridViewAnswerAdapter(setupNullList(), this);
            suggestAdapter = new GridViewSuggestAdapter(suggestSource, this, this);

            answerAdapter.notifyDataSetChanged();
            suggestAdapter.notifyDataSetChanged();

            gridViewSuggest.setAdapter(suggestAdapter);
            gridViewAnswer.setAdapter(answerAdapter);
        }
    }
        private char[] setupNullList () {
            char result[] = new char[answer.length];
            for (int i = 0; i < answer.length; i++)
                result[i] = ' ';
            return result;
        }
    }





