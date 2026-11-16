package com.step9.flashcards;

import androidx.annotation.NonNull;

public class Word {
    private final String english;
    private final String russian;
    private final String transcription;
    private final String exampleEn;
    private final String exampleRu;
    private final String level;
    private final String category; // Новое поле для функции "Категории"

    public Word(String english, String russian, String transcription, 
                String exampleEn, String exampleRu, String level, String category) {
        this.english = english != null ? english : "";
        this.russian = russian != null ? russian : "";
        this.transcription = transcription != null ? transcription : "";
        this.exampleEn = exampleEn != null ? exampleEn : "";
        this.exampleRu = exampleRu != null ? exampleRu : "";
        this.level = level != null ? level : "A1";
        this.category = category != null ? category : "Общее"; // Значение по умолчанию
    }

    // Перегруженный конструктор для обратной совместимости со старым кодом (без категории)
    public Word(String english, String russian, String transcription, 
                String exampleEn, String exampleRu, String level) {
        this(english, russian, transcription, exampleEn, exampleRu, level, "Общее");
    }

    public String getEnglish() { return english; }
    public String getRussian() { return russian; }
    public String getTranscription() { return transcription; }
    public String getExampleEn() { return exampleEn; }
    public String getExampleRu() { return exampleRu; }
    public String getLevel() { return level; }
    public String getCategory() { return category; } // Новый геттер

    @NonNull
    @Override
    public String toString() {
        return english;
    }
}
