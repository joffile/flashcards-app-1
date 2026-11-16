package com.step9.flashcards;

import androidx.annotation.NonNull;

public class Word {
    private final String english;
    private final String russian;
    private final String transcription;
    private final String exampleEn;
    private final String exampleRu;
    private final String level;

    public Word(String english, String russian, String transcription, 
                String exampleEn, String exampleRu, String level) {
        this.english = english != null ? english : "";
        this.russian = russian != null ? russian : "";
        this.transcription = transcription != null ? transcription : "";
        this.exampleEn = exampleEn != null ? exampleEn : "";
        this.exampleRu = exampleRu != null ? exampleRu : "";
        this.level = level != null ? level : "A1";
    }

    public String getEnglish() { return english; }
    public String getRussian() { return russian; }
    public String getTranscription() { return transcription; }
    public String getExampleEn() { return exampleEn; }
    public String getExampleRu() { return exampleRu; }
    public String getLevel() { return level; }

    @NonNull
    @Override
    public String toString() {
        return english;
    }
}
