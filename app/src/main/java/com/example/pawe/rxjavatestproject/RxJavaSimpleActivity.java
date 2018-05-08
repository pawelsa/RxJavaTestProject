package com.example.pawe.rxjavatestproject;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxJavaSimpleActivity extends AppCompatActivity {
	
	CompositeDisposable disposable=new CompositeDisposable();
	public int value=0;
	
	final Observable<Integer> serverDownloadObservable=Observable.create(emitter->{
		SystemClock.sleep(10000);
		emitter.onNext(5);
		emitter.onComplete();
	});
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rx_java_simple);
		
		View view = findViewById(R.id.button);
		view.setOnClickListener(v -> {
			v.setEnabled(false);
			
			Disposable subscribe= serverDownloadObservable.
					observeOn(AndroidSchedulers.mainThread()).
					subscribeOn(Schedulers.io()).
					subscribe(integer -> {
						updateUserInterface(integer);
						v.setEnabled(true);
					});
			disposable.add(subscribe);
		});
	}
	
	private void updateUserInterface(int integer){
		TextView textView=findViewById(R.id.resultView);
		textView.setText(String.valueOf(integer));
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if (disposable!=null && !disposable.isDisposed()){
			disposable.dispose();
		}
	}
	
	public void onClick(View view){
		Toast.makeText(this, "Still active: "+value++, Toast.LENGTH_SHORT).show();
	}
}
