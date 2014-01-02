package com.yoe21c.file2object.model;

import java.net.URL;


public class Urls implements Param {

	private URL url;

	public Urls(URL url) {
		this.url = url;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
	
}
