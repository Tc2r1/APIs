package com.tc2r.apiexampletmdb.Model.MovieInfo;

/**
 * Created by Tc2r on 4/18/2017.
 * <p>
 * Description:
 */

public class Backdrop {

	private double aspect_ratio;
	private String file_path;
	private int height;
	private int width;

	public double getAspectRatio() {
		return aspect_ratio;
	}

	public void setAspectRatio(double aspect_ratio) {
		this.aspect_ratio = aspect_ratio;
	}

	public String getFilePath() {
		return file_path;
	}

	public void setFilePath(String file_path) {
		this.file_path = file_path;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
