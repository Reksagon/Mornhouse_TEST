package com.denys.korniienko.dagger;

import android.app.Application;
import androidx.room.Room;
import com.denys.korniienko.room.HistoryDao;
import com.denys.korniienko.room.HistoryDatabase;
import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    private HistoryDatabase appDatabase;

    @Provides
    public HistoryDatabase provideDatabase(Application application) {
        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(
                    application.getApplicationContext(),
                    HistoryDatabase.class, "history-db"
            ).build();
        }
        return appDatabase;
    }

    @Provides
    public HistoryDao provideHistoryDao(HistoryDatabase appDatabase) {
        return appDatabase.historyDao();
    }

}

