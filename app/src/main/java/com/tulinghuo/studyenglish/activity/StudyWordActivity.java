package com.tulinghuo.studyenglish.activity;

import android.content.Intent;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tulinghuo.studyenglish.R;
import com.tulinghuo.studyenglish.adapter.WordSentenceAdapter;
import com.tulinghuo.studyenglish.fragment.component.WordPhraseFragment;
import com.tulinghuo.studyenglish.fragment.component.WordRelWordFragment;
import com.tulinghuo.studyenglish.fragment.component.WordSentenceFragment;
import com.tulinghuo.studyenglish.fragment.component.WordSynoFragment;
import com.tulinghuo.studyenglish.fragment.component.WordTranslateFragment;
import com.tulinghuo.studyenglish.util.CommonUtil;
import com.tulinghuo.studyenglish.util.HttpUtil;
import com.tulinghuo.studyenglish.util.MediaPlayerUtils;
import com.tulinghuo.studyenglish.vo.HttpResponseVO;
import com.tulinghuo.studyenglish.vo.WordVO;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyWordActivity extends AppCompatActivity {

    private int index = 0;
    private int taskId;
    private List<Integer> wordList = new ArrayList<>();
    private WordVO wordVO;
    private boolean isLoadingNext = false;

    private LoadingDialog loadingDialog;
    private MediaPlayerUtils mediaPlayerUtils;

    private TextView word_name;
    private LinearLayout usphone_ll;
    private LinearLayout ukphone_ll;
    private TextView usphone_tv;
    private TextView ukphone_tv;
    private LinearLayout translate_ll;
    private LinearLayout rem_method_ll;
    private TextView rem_method_tv;
    private LinearLayout phrase_nav_ll;
    private LinearLayout rel_word_nav_ll;
    private LinearLayout syno_nav_ll;
    private LinearLayout multiply_content_ll;
    private TextView next_tv;

    private WordPhraseFragment wordPhraseFragment;
    private WordRelWordFragment wordRelWordFragment;
    private WordSynoFragment wordSynoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        taskId = intent.getIntExtra("taskId", 0);

        createActivityViews();
        initViews();
        loadWordList();
    }

    private void createActivityViews() {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_study_word);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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
        phrase_nav_ll = findViewById(R.id.phrase_nav_ll);
        rel_word_nav_ll = findViewById(R.id.rel_word_nav_ll);
        syno_nav_ll = findViewById(R.id.syno_nav_ll);
        multiply_content_ll = findViewById(R.id.multiply_content_ll);
        next_tv = findViewById(R.id.next_tv);

        back_iv.setOnClickListener(v -> {
            finish();
        });

        usphone_ll.setOnClickListener(v -> {
            mediaPlayerUtils.play(wordVO.getUsspeech());
        });
        ukphone_ll.setOnClickListener(v -> {
            mediaPlayerUtils.play(wordVO.getUkspeech());
        });

        phrase_nav_ll.setSelected(true);
        rel_word_nav_ll.setSelected(false);
        syno_nav_ll.setSelected(false);

        phrase_nav_ll.setOnClickListener(v -> {
            phrase_nav_ll.setSelected(true);
            rel_word_nav_ll.setSelected(false);
            syno_nav_ll.setSelected(false);
            getSupportFragmentManager().beginTransaction().replace(multiply_content_ll.getId(), wordPhraseFragment).commit();
        });
        rel_word_nav_ll.setOnClickListener(v -> {
            phrase_nav_ll.setSelected(false);
            rel_word_nav_ll.setSelected(true);
            syno_nav_ll.setSelected(false);
            getSupportFragmentManager().beginTransaction().replace(multiply_content_ll.getId(), wordRelWordFragment).commit();
        });
        syno_nav_ll.setOnClickListener(v -> {
            phrase_nav_ll.setSelected(false);
            rel_word_nav_ll.setSelected(false);
            syno_nav_ll.setSelected(true);
            getSupportFragmentManager().beginTransaction().replace(multiply_content_ll.getId(), wordSynoFragment).commit();
        });

        next_tv.setOnClickListener(v -> {
            showNext();
        });
    }

    private void loadWordList() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setLoadingText("加载数据中，请稍后...");
        loadingDialog.show();

        wordList.clear();
        Map<String, String> params = new HashMap<>();
        params.put("taskid", String.valueOf(taskId));
        HttpUtil.postFormDataAsync("/api_task.php?method=startNewWord", params, new HttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String response) {
                Log.i("StudyWordActivity", "loadWordList -> " + response);
                JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
                JsonArray jsonArray = jsonObject.getAsJsonArray("data");
                for (int i = 0; i < jsonArray.size(); i++) {
                    wordList.add(jsonArray.get(i).getAsInt());
                }
                loadWord(wordList.get(index));
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(loadingDialog::close);
            }
        });
    }

    private void loadWord(int wordId) {
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
                isLoadingNext = false;
            }

            @Override
            public void onFailure(Exception e) {
                runOnUiThread(loadingDialog::close);
                isLoadingNext = false;
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

            wordPhraseFragment = WordPhraseFragment.newInstance(wordVO);
            wordRelWordFragment = WordRelWordFragment.newInstance(wordVO);
            wordSynoFragment = WordSynoFragment.newInstance(wordVO);
            getSupportFragmentManager().beginTransaction().add(multiply_content_ll.getId(), wordPhraseFragment).commit();

            loadingDialog.close();
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
        ViewPager2 sentence_vp = findViewById(R.id.sentence_vp);
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

    private void showNext() {
        if (isLoadingNext) return;
        isLoadingNext = true;
        index++;
        if (index >= wordList.size()) {
            index = 0;
        }
//        loadingDialog.show();

        createActivityViews();
        wordPhraseFragment = null;
        wordRelWordFragment = null;
        wordSynoFragment = null;
        initViews();
        loadWord(wordList.get(index));
    }
}