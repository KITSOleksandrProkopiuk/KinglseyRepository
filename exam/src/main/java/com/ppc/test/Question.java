package com.ppc.test;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String question;
    private List<String> variants;
    private String answer;

    public Question() {
        variants = new ArrayList<String>();
    }

    public void addVariant(final String variant) {
        variants.add(variant);
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(final String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(final String question) {
        this.question = question;
    }

    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(final List<String> variants) {
        this.variants = variants;
    }

}
