package ch.xero88.goodmood.Mood;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xero88 on 14/06/2016.
 */
public class FirebaseRepository implements MoodRepository {

    private String TAG = "FirebaseRepository";
    private String MOOD_ENTITY_FIREBASE = "mood";
    private DatabaseReference mRef;

    public FirebaseRepository() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mRef = database.getReference(MOOD_ENTITY_FIREBASE);

    }

    @Override
    public void getMoods(@NonNull final LoadMoodsCallback callback) {

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Mood> moods = new ArrayList<>();
                for (DataSnapshot moodSnapshot: dataSnapshot.getChildren()) {
                    Mood mood = moodSnapshot.getValue(Mood.class);
                    moods.add(mood);
                }
                callback.onMoodLoaded(moods);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                callback.onMoodFailed();
            }
        };
        mRef.addValueEventListener(listener);

    }

    @Override
    public void getMood(@NonNull String noteId, @NonNull GetMoodCallback callback) {
        // TODO: 05/07/2016  
    }

    @Override
    public void saveMood(@NonNull Mood note) {
        // TODO: 05/07/2016  
    }
}
