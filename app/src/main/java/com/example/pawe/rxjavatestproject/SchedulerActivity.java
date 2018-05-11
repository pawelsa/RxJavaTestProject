package com.example.pawe.rxjavatestproject;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SchedulerActivity extends AppCompatActivity {

    private Disposable subscription;
    private ProgressBar progressBar;
    private TextView messageArea;
    private View button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureLayout();
        createObservable();
    }

    private void configureLayout() {
        setContentView(R.layout.activity_scheduler);
        progressBar = findViewById(R.id.progressBar);
        messageArea = findViewById(R.id.messagearea);
        button = findViewById(R.id.scheduleLongRunningOperation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Observable.fromCallable(callable)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> {
                            progressBar.setVisibility(View.VISIBLE);
                            button.setEnabled(false);
                            messageArea.setText(messageArea.getText().toString() + "\nProgressBar set visible");
                        })
                        .subscribe(getDisposableObserver());
            }
        });
    }

    Callable<String> callable = new Callable<String>() {
        @Override
        public String call() throws Exception {
            return doSomethingLong();
        }
    };

    public String doSomethingLong() {
        SystemClock.sleep(1000);
        return "Hello";
    }

    private DisposableObserver<String> getDisposableObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                messageArea.setText(messageArea.getText().toString() + "\nonNext " + s);
            }

            @Override
            public void onError(Throwable e) {
                messageArea.setText(messageArea.getText().toString() + "\nonError");
                progressBar.setVisibility(View.INVISIBLE);
                button.setEnabled(true);
                messageArea.setText(messageArea.getText().toString() + "\nHiding progressBar");
            }

            @Override
            public void onComplete() {
                messageArea.setText(messageArea.getText().toString() + "\nonComplete");
                progressBar.setVisibility(View.INVISIBLE);
                button.setEnabled(true);
                messageArea.setText(messageArea.getText().toString() + "\n"+"Hiding progressBar");
            }
        };
    }

    private void createObservable() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isDisposed())
            subscription.dispose();
    }


}
