package com.gmail.s0rInb.Utils;

public class FileStorageExceptions extends Exception {
	public FileStorageExceptions(Exception e) {
		super(e);
	}

	public FileStorageExceptions(String message) {
		super(message);
	}
}
