package com.step9.flashcards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordDataManager {
    
    private static final List<Word> ALL_WORDS = new ArrayList<>();

    static {
        // Добавлены категории при инициализации слов
        ALL_WORDS.add(new Word("think", "думать", "[θɪŋk]", 
                "I think so.", "Я так думаю.", "A1", "Глаголы"));
        ALL_WORDS.add(new Word("water", "вода", "[ˈwɔːtər]", 
                "Can I have some water?", "Можно мне немного воды?", "A1", "Еда и напитки"));
        ALL_WORDS.add(new Word("travel", "путешествовать", "[ˈtrævl]", 
                "I love to travel in summer.", "Я люблю путешествовать летом.", "A2", "Путешествия"));
        ALL_WORDS.add(new Word("decide", "решать", "[dɪˈsaɪd]", 
                "It is hard to decide.", "Трудно принять решение.", "A2", "Глаголы"));
        ALL_WORDS.add(new Word("achieve", "достигать", "[əˈtʃiːv]", 
                "He achieved his goal.", "Он достиг своей цели.", "B1", "Глаголы"));
        ALL_WORDS.add(new Word("environment", "окружающая среда", "[ɪnˈvaɪrənmənt]", 
                "We must protect the environment.", "Мы должны защищать окружающую среду.", "B1", "Природа"));
        ALL_WORDS.add(new Word("significant", "значительный", "[sɪɡˈnɪfɪkənt]", 
                "There was a significant change.", "Произошли значительные изменения.", "B2", "Общее"));
        ALL_WORDS.add(new Word("establish", "устанавливать", "[ɪˈstæblɪʃ]", 
                "They established a new company.", "Они основали новую компанию.", "B2", "Работа"));
        ALL_WORDS.add(new Word("comprehensive", "всеобъемлющий", "[ˌkɒmprɪˈhensɪv]", 
                "A comprehensive report.", "Всеобъемлющий отчёт.", "C1", "Общее"));
        ALL_WORDS.add(new Word("inevitable", "неизбежный", "[ɪnˈevɪtəbl]", 
                "Change is inevitable.", "Перемены неизбежны.", "C1", "Общее"));
    }

    private WordDataManager() {}

    // --- Функция 1: Уровни CEFR ---
    public static List<String> getAvailableLevels() {
        return Arrays.asList("Все уровни", "A1", "A2", "B1", "B2", "C1");
    }

    public static List<Word> getWordsByLevel(String level) {
        List<Word> result = new ArrayList<>();
        if (level == null || level.isEmpty() || level.equals("Все уровни")) {
            result.addAll(ALL_WORDS);
            return result;
        }
        for (Word word : ALL_WORDS) {
            if (level.equals(word.getLevel())) {
                result.add(word);
            }
        }
        return result;
    }

    // --- Функция 2: Категории (Новая) ---
    public static List<String> getAvailableCategories() {
        return Arrays.asList("Все категории", "Глаголы", "Еда и напитки", "Путешествия", "Природа", "Работа", "Общее");
    }

    public static List<Word> getWordsByCategory(String category) {
        List<Word> result = new ArrayList<>();
        if (category == null || category.isEmpty() || category.equals("Все категории")) {
            result.addAll(ALL_WORDS);
            return result;
        }
        for (Word word : ALL_WORDS) {
            if (category.equals(word.getCategory())) {
                result.add(word);
            }
        }
        return result;
    }

    // --- Функция 3: Комбинированная фильтрация (Уровень + Категория) ---
    public static List<Word> getWordsByLevelAndCategory(String level, String category) {
        List<Word> result = new ArrayList<>();
        boolean filterByLevel = level != null && !level.isEmpty() && !level.equals("Все уровни");
        boolean filterByCategory = category != null && !category.isEmpty() && !category.equals("Все категории");

        for (Word word : ALL_WORDS) {
            boolean levelMatch = !filterByLevel || level.equals(word.getLevel());
            boolean categoryMatch = !filterByCategory || category.equals(word.getCategory());
            
            if (levelMatch && categoryMatch) {
                result.add(word);
            }
        }
        return result;
    }
}
