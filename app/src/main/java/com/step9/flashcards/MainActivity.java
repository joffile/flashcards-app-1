package com.step9.flashcards;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_CURRENT_INDEX = "current_index";
    private static final String STATE_SELECTED_LEVEL = "selected_level";
    private static final String STATE_IS_REVEALED = "is_revealed";

    private Spinner levelSpinner;
    private TextView tvEnglish, tvTranscription, tvRussian, tvExampleEn, tvExampleRu, tvProgress, tvEmptyState;
    private Button btnReveal, btnNext;
    private CardView wordCard;

    private List<Word> currentWordsList;
    private int currentIndex = 0;
    private boolean isRevealed = false;
    private String currentLevel = "Все уровни";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupSpinner();
        
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(STATE_CURRENT_INDEX, 0);
            currentLevel = savedInstanceState.getString(STATE_SELECTED_LEVEL, "Все уровни");
            isRevealed = savedInstanceState.getBoolean(STATE_IS_REVEALED, false);
            
            ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) levelSpinner.getAdapter();
            int position = adapter.getPosition(currentLevel);
            if (position >= 0) {
                levelSpinner.setSelection(position);
            }
        } else {
            loadWordsForLevel(currentLevel);
        }
    }

    private void initViews() {
        levelSpinner = findViewById(R.id.levelSpinner);
        tvEnglish = findViewById(R.id.tvEnglish);
        tvTranscription = findViewById(R.id.tvTranscription);
        tvRussian = findViewById(R.id.tvRussian);
        tvExampleEn = findViewById(R.id.tvExampleEn);
        tvExampleRu = findViewById(R.id.tvExampleRu);
        tvProgress = findViewById(R.id.tvProgress);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        btnReveal = findViewById(R.id.btnReveal);
        btnNext = findViewById(R.id.btnNext);
        wordCard = findViewById(R.id.wordCard);

        if (levelSpinner == null || tvEnglish == null || btnNext == null) {
            throw new IllegalStateException("Один или несколько View не найдены. Проверьте activity_main.xml");
        }

        btnReveal.setOnClickListener(v -> toggleReveal());
        btnNext.setOnClickListener(v -> showNextWord());
    }

    private void setupSpinner() {
        List<String> levels = WordDataManager.getAvailableLevels();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 
                android.R.layout.simple_spinner_item, levels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(adapter);

        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentLevel = parent.getItemAtPosition(position).toString();
                currentIndex = 0;
                isRevealed = false;
                loadWordsForLevel(currentLevel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void loadWordsForLevel(String level) {
        currentWordsList = WordDataManager.getWordsByLevel(level);
        updateUI();
    }

    private void toggleReveal() {
        isRevealed = !isRevealed;
        updateUI();
    }

    private void showNextWord() {
        if (currentWordsList == null || currentWordsList.isEmpty()) {
            return;
        }

        currentIndex++;
        if (currentIndex >= currentWordsList.size()) {
            currentIndex = 0;
            Toast.makeText(this, "Вы прошли все слова этого уровня. Начинаем сначала.", Toast.LENGTH_SHORT).show();
        }
        
        isRevealed = false;
        updateUI();
    }

    private void updateUI() {
        if (currentWordsList == null || currentWordsList.isEmpty()) {
            wordCard.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
            tvProgress.setVisibility(View.GONE);
            tvEmptyState.setVisibility(View.VISIBLE);
            return;
        }

        wordCard.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.VISIBLE);
        tvProgress.setVisibility(View.VISIBLE);
        tvEmptyState.setVisibility(View.GONE);

        if (currentIndex < 0 || currentIndex >= currentWordsList.size()) {
            currentIndex = 0;
        }

        Word currentWord = currentWordsList.get(currentIndex);

        tvEnglish.setText(currentWord.getEnglish());
        tvTranscription.setText(currentWord.getTranscription());
        tvProgress.setText((currentIndex + 1) + " / " + currentWordsList.size() + " (" + currentWord.getLevel() + ")");

        if (isRevealed) {
            tvRussian.setVisibility(View.VISIBLE);
            tvRussian.setText(currentWord.getRussian());
            
            tvExampleEn.setVisibility(View.VISIBLE);
            tvExampleEn.setText(currentWord.getExampleEn());
            
            tvExampleRu.setVisibility(View.VISIBLE);
            tvExampleRu.setText(currentWord.getExampleRu());
            
            btnReveal.setText("Скрыть перевод");
        } else {
            tvRussian.setVisibility(View.GONE);
            tvExampleEn.setVisibility(View.GONE);
            tvExampleRu.setVisibility(View.GONE);
            btnReveal.setText("Показать перевод");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_CURRENT_INDEX, currentIndex);
        outState.putString(STATE_SELECTED_LEVEL, currentLevel);
        outState.putBoolean(STATE_IS_REVEALED, isRevealed);
    }
}
