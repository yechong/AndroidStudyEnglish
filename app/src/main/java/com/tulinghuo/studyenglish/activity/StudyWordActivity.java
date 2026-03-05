package com.tulinghuo.studyenglish.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.adapter.WordSentenceAdapter;
import com.tulinghuo.studyenglish.fragment.component.WordSentenceFragment;
import com.tulinghuo.studyenglish.fragment.component.WordTranslateFragment;
import com.tulinghuo.studyenglish.util.CommonUtil;
import com.tulinghuo.studyenglish.util.HttpUtil;
import com.tulinghuo.studyenglish.util.MediaPlayerUtils;
import com.tulinghuo.studyenglish.vo.HttpResponseVO;
import com.tulinghuo.studyenglish.vo.WordVO;

public class StudyWordActivity extends AppCompatActivity {

    private int wordId;
    private WordVO wordVO;

    private MediaPlayerUtils mediaPlayerUtils;

    private TextView word_name;
    private LinearLayout usphone_ll;
    private LinearLayout ukphone_ll;
    private TextView usphone_tv;
    private TextView ukphone_tv;
    private LinearLayout translate_ll;
    private LinearLayout rem_method_ll;
    private TextView rem_method_tv;
    private ViewPager2 sentence_vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_study_word);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        loadWord();
    }

    private void initViews() {
        mediaPlayerUtils = new MediaPlayerUtils();

        ImageView back_iv = findViewById(R.id.back_iv);
        word_name = findViewById(R.id.word_name);
        usphone_ll = findViewById(R.id.usphone_ll);
        ukphone_ll = findViewById(R.id.ukphone_ll);
        usphone_tv = findViewById(R.id.usphone_tv);
        ukphone_tv = findViewById(R.id.ukphone_tv);
        translate_ll = findViewById(R.id.translate_ll);
        rem_method_ll = findViewById(R.id.rem_method_ll);
        rem_method_tv = findViewById(R.id.rem_method_tv);

        back_iv.setOnClickListener(v -> {
            finish();
        });

        usphone_ll.setOnClickListener(v -> {
            mediaPlayerUtils.play(wordVO.getUsspeech());
        });
        ukphone_ll.setOnClickListener(v -> {
            mediaPlayerUtils.play(wordVO.getUkspeech());
        });
    }

    private void loadWord() {
        wordId = 1;
        HttpUtil.getAsync("/api_word.php?method=detail&id=" + wordId, new HttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                HttpResponseVO responseVO = gson.fromJson(response, HttpResponseVO.class);
                if (!responseVO.isRequestSuccess()) {
                    runOnUiThread(() -> Toast.makeText(StudyWordActivity.this, responseVO.getMsg(), Toast.LENGTH_SHORT).show());
                }
                else {
                    JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
                    wordVO = gson.fromJson(jsonObject.getAsJsonObject("data"), WordVO.class);
                    Log.i("StudyWordActivity", "wordVO -> " + gson.toJson(wordVO));
                    updateWordView();
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    private void updateWordView() {
        runOnUiThread(() -> {
            word_name.setText(wordVO.getWord());
            if (CommonUtil.isBlank(wordVO.getUsphone())) {
                usphone_ll.setVisibility(View.GONE);
                usphone_tv.setVisibility(View.GONE);
            }
            else {
                usphone_ll.setVisibility(View.VISIBLE);
                usphone_tv.setVisibility(View.VISIBLE);
                usphone_tv.setText("/" + wordVO.getUsphone() + "/");
            }

            if (CommonUtil.isBlank(wordVO.getUkphone())) {
                ukphone_ll.setVisibility(View.GONE);
                ukphone_tv.setVisibility(View.GONE);
            }
            else {
                ukphone_ll.setVisibility(View.VISIBLE);
                ukphone_tv.setVisibility(View.VISIBLE);
                ukphone_tv.setText("/" + wordVO.getUkphone() + "/");
            }

            createTranslateViews();

            if (!CommonUtil.isBlank(wordVO.getRemMethod())) {
                rem_method_ll.setVisibility(View.VISIBLE);
                rem_method_tv.setText(wordVO.getRemMethod());
            }
            else {
                rem_method_ll.setVisibility(View.GONE);
            }

            createSentenceViews();
        });
    }

    private void createTranslateViews() {
        if (wordVO.getTranslationList() == null || wordVO.getTranslationList().isEmpty()) {
            translate_ll.setVisibility(View.GONE);
            return;
        }
        for (WordVO.TranslationVO vo : wordVO.getTranslationList()) {
            WordTranslateFragment fragment = WordTranslateFragment.newInstance(vo.getPos(), vo.getTranslation());
            getSupportFragmentManager().beginTransaction().add(translate_ll.getId(), fragment).commit();
        }
    }

    private void createSentenceViews() {
        sentence_vp = findViewById(R.id.sentence_vp);
        DotsIndicator dotsIndicator = findViewById(R.id.dotsIndicator);
        if (wordVO.getSentenceList() == null || wordVO.getSentenceList().isEmpty()) {
            sentence_vp.setVisibility(View.GONE);
            return;
        }
        WordSentenceAdapter adapter = new WordSentenceAdapter(this);
        for (WordVO.SentenceVO vo : wordVO.getSentenceList()) {
            adapter.addFragment(WordSentenceFragment.newInstance(vo.getContent(), vo.getTranslation()));
        }
        sentence_vp.setAdapter(adapter);
        dotsIndicator.attachTo(sentence_vp);
    }

}