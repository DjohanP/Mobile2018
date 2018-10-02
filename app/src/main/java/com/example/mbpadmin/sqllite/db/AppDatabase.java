package com.example.mbpadmin.sqllite.db;

import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

@Database(entities = {FotooEntity.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FotooDao fotooDao();
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(final Context context)
    {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE=Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"mobile2018").addCallback(
                        new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                AppDatabase database=AppDatabase.getInstance(context.getApplicationContext());
                                final List<FotooEntity> fotoos=DataGenerator.generateProducts();
                                database.runInTransaction(() -> {
                                    database.fotooDao().insertAll(fotoos);
                                });
                            }
                        }
                    ).build();
                }
            }
        }
        return INSTANCE;
    }

}
