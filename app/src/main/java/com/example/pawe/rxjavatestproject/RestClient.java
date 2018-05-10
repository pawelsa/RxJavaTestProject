package com.example.pawe.rxjavatestproject;

import android.content.Context;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paweł on 08.05.2018.
 */

public class RestClient {
	
	private Context context;
	
	public RestClient(Context context) {
		this.context = context;
	}
	
	public List<String> getFavouriteBooks() {
		SystemClock.sleep(8000);
		return createBooks();
	}
	
	public List<String> getFavouriteBooksWithException() {
		SystemClock.sleep(8000);
		throw new RuntimeException("Failed to load");
	}
	
	private List<String> createBooks() {
		List<String> books = new ArrayList<>();
		books.add("Lord of the Rings");
		books.add("The dark elf");
		books.add("Eclipse Introduction");
		books.add("History book");
		books.add("Der kleine Prinz");
		books.add("7 habits of highly effective people");
		books.add("Other book 1");
		books.add("Other book 2");
		books.add("Other book 3");
		books.add("Other book 4");
		books.add("Other book 5");
		books.add("Other book 6");
		return books;
	}
}
