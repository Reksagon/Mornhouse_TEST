package com.denys.korniienko.fragments;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.denys.korniienko.dagger.ApiService;
import com.denys.korniienko.room.History;
import com.denys.korniienko.room.HistoryDao;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MainFragmentViewModel extends AndroidViewModel {

    @Inject
    ApiService myApiService;
    @Inject
    HistoryDao historyrDao;
    private MutableLiveData<String> mutableLiveData;
    private MutableLiveData<List<History>> historyMutableLiveData;
    private CompositeDisposable compositeDisposable;

    public MainFragmentViewModel(@NonNull Application application) {
        super(application);
        mutableLiveData = new MutableLiveData<>();
        historyMutableLiveData = new MutableLiveData<>();
        compositeDisposable = new CompositeDisposable();
    }

    public MutableLiveData<List<History>> getHistoryMutableLiveData() {
        return historyMutableLiveData;
    }

    public MutableLiveData<String> getMutableLiveData() {
        return mutableLiveData;
    }

    public void getFact(int num)
    {
        myApiService.getNumber(num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResponseBody value) {
                        try {
                            mutableLiveData.setValue(value.string());
                        } catch (IOException e) {
                            mutableLiveData.setValue(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mutableLiveData.setValue(e.getMessage());
                        Log.e("ERROR", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
    public void clearData() {
        mutableLiveData.setValue(null);
        historyMutableLiveData.setValue(null);
    }


    public void getAllHistory() {
        compositeDisposable.add(historyrDao.getAllHistory()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError));
    }

    private void handleResults(List<History> results) {
        historyMutableLiveData.setValue(results);
    }

    private void handleError(Throwable error) {
        // обробка помилок
    }
    public void getRandomFact()
    {
        myApiService.getRandom()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResponseBody value) {
                        try {
                            mutableLiveData.setValue(value.string());
                        } catch (IOException e) {
                            mutableLiveData.setValue(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mutableLiveData.setValue(e.getMessage());
                        Log.e("ERROR", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
