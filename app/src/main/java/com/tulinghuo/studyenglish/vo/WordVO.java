package com.tulinghuo.studyenglish.vo;

import java.io.Serializable;
import java.util.List;

public class WordVO implements Serializable {
    private static final long serialVersionUID = -9015480168498204243L;

    private int id;
    private int bookid;
    private String word;
    private String usphone;
    private String usspeech;
    private String ukphone;
    private String ukspeech;
    private String remMethod;
    private String picture;
    private List<SentenceVO> sentenceList;
    private List<TranslationVO> translationList;
    private List<PhraseVO> phraseList;
    private List<RelWordVO> relWordList;
    private List<SynoVO> synoList;

    public static class SentenceVO implements Serializable {

        private static final long serialVersionUID = -5731342481624971065L;

        private String content;
        private String translation;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTranslation() {
            return translation;
        }

        public void setTranslation(String translation) {
            this.translation = translation;
        }
    }

    public static class TranslationVO implements Serializable {

        private static final long serialVersionUID = -802842920449491200L;

        private String pos;
        private String translation;

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public String getTranslation() {
            return translation;
        }

        public void setTranslation(String translation) {
            this.translation = translation;
        }
    }

    public static class PhraseVO implements Serializable {

        private static final long serialVersionUID = 3310071835357268177L;

        private String content;
        private String translation;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTranslation() {
            return translation;
        }

        public void setTranslation(String translation) {
            this.translation = translation;
        }
    }

    public static class RelWordVO implements Serializable {

        private static final long serialVersionUID = -5320130940614846835L;

        private String pos;
        private List<RelWordSubVO> words;

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public List<RelWordSubVO> getWords() {
            return words;
        }

        public void setWords(List<RelWordSubVO> words) {
            this.words = words;
        }
    }

    public static class RelWordSubVO implements Serializable {

        private static final long serialVersionUID = -6038403748295675234L;

        private String hwd;
        private String tran;

        public String getHwd() {
            return hwd;
        }

        public void setHwd(String hwd) {
            this.hwd = hwd;
        }

        public String getTran() {
            return tran;
        }

        public void setTran(String tran) {
            this.tran = tran;
        }
    }

    public static class SynoVO implements Serializable {

        private static final long serialVersionUID = 7369325886599294729L;

        private String pos;
        private String translation;
        private List<String> words;

        public String getPos() {
            return pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public String getTranslation() {
            return translation;
        }

        public void setTranslation(String translation) {
            this.translation = translation;
        }

        public List<String> getWords() {
            return words;
        }

        public void setWords(List<String> words) {
            this.words = words;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getUsphone() {
        return usphone;
    }

    public void setUsphone(String usphone) {
        this.usphone = usphone;
    }

    public String getUsspeech() {
        return usspeech;
    }

    public void setUsspeech(String usspeech) {
        this.usspeech = usspeech;
    }

    public String getUkphone() {
        return ukphone;
    }

    public void setUkphone(String ukphone) {
        this.ukphone = ukphone;
    }

    public String getUkspeech() {
        return ukspeech;
    }

    public void setUkspeech(String ukspeech) {
        this.ukspeech = ukspeech;
    }

    public String getRemMethod() {
        return remMethod;
    }

    public void setRemMethod(String remMethod) {
        this.remMethod = remMethod;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<SentenceVO> getSentenceList() {
        return sentenceList;
    }

    public void setSentenceList(List<SentenceVO> sentenceList) {
        this.sentenceList = sentenceList;
    }

    public List<TranslationVO> getTranslationList() {
        return translationList;
    }

    public void setTranslationList(List<TranslationVO> translationList) {
        this.translationList = translationList;
    }

    public List<PhraseVO> getPhraseList() {
        return phraseList;
    }

    public void setPhraseList(List<PhraseVO> phraseList) {
        this.phraseList = phraseList;
    }

    public List<RelWordVO> getRelWordList() {
        return relWordList;
    }

    public void setRelWordList(List<RelWordVO> relWordList) {
        this.relWordList = relWordList;
    }

    public List<SynoVO> getSynoList() {
        return synoList;
    }

    public void setSynoList(List<SynoVO> synoList) {
        this.synoList = synoList;
    }
}
