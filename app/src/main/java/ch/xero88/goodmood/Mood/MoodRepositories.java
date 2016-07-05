package ch.xero88.goodmood.Mood;

import android.support.annotation.NonNull;

/**
 * Created by Xero88 on 14/06/2016.
 */
public class MoodRepositories {

    private static MoodRepository repository = null;

    public synchronized static MoodRepository getFirebaseRepoInstance() {
        if (null == repository) {
            repository = new FirebaseRepository();
        }
        return repository;
    }
}
