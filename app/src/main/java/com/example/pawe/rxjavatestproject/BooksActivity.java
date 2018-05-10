package com.example.pawe.rxjavatestproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BooksActivity extends AppCompatActivity {
	
	private Disposable bookSubscription;
	private RecyclerView booksRecyclerView;
	private ProgressBar progressBar;
	private SimpleStringAdapter adapter;
	private RestClient restClient;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_books);
		
		restClient = new RestClient(this);
		progressBar = findViewById(R.id.loader);
		booksRecyclerView = findViewById(R.id.books_list);
		booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new SimpleStringAdapter(this);
		booksRecyclerView.setAdapter(adapter);
		
		createObservable();
	}
	
	private void createObservable() {
		Observable<List<String>> booksObservable = Observable.fromCallable(() -> restClient.getFavouriteBooks());
		bookSubscription = booksObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(strings -> displayBooks(strings));
	}
	
	private void displayBooks(List<String> books) {
		adapter.setStringList(books);
		progressBar.setVisibility(View.GONE);
		booksRecyclerView.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (bookSubscription != null && !bookSubscription.isDisposed()) {
			bookSubscription.dispose();
		}
	}
}
