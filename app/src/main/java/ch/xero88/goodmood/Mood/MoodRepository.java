package ch.xero88.goodmood.Mood;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Xero88 on 14/06/2016.
 */
public interface MoodRepository {

    interface LoadMoodsCallback {

        void onMoodLoaded(List<Mood> moods);
        void onMoodFailed();
    }

    interface GetMoodCallback {

        void onMoodLoaded(Mood mood);
    }

    void getMoods(@NonNull LoadMoodsCallback callback);
    void getMood(@NonNull String noteId, @NonNull GetMoodCallback callback);
    void saveMood(@NonNull Mood note);
}
