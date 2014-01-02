package com.yoe21c.file2object.model;

import java.io.File;


public class Files implements Param {

	private File file;

	public Files(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	
}
