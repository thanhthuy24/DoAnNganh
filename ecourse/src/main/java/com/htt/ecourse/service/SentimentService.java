package com.htt.ecourse.service;

import org.json.JSONException;

public interface SentimentService {
    String analyzeSentiment(String comment) throws JSONException;
    String analyzeSentimentRating(String comment) throws JSONException;
}
