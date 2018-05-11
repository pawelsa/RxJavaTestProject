package com.example.pawe.rxjavatestproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onClick(View view) {
		int ID = view.getId();
		Intent intent = null;
		switch (ID) {
			case R.id.first: {
				intent = new Intent(this, RxJavaSimpleActivity.class);
				break;
			}
			case R.id.second: {
				intent = new Intent(this, BooksActivity.class);
				break;
			}
			case R.id.third: {
				intent = new Intent(this, ColorsActivity.class);
				break;
			}
			case R.id.fourth: {
				intent = new Intent(this, SchedulerActivity.class);
				break;
			}
		}
		if (intent != null)
			startActivity(intent);
	}
}
