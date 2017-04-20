package com.tc2r.apiexampletmdb.Model.MovieInfo;

/**
 * Created by Tc2r on 4/18/2017.
 * <p>
 * Description:
 */

public class Poster {

		private double aspect_ratio;
		private String file_path;
		private int height;
		private String iso_639_1;
		private double vote_average;
		private int vote_count;
		private int width;

		public double getAspect_ratio() {
			return aspect_ratio;
		}

		public void setAspect_ratio(double aspect_ratio) {
			this.aspect_ratio = aspect_ratio;
		}

		public String getFile_path() {
			return file_path;
		}

		public void setFile_path(String file_path) {
			this.file_path = file_path;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public String getIso_639_1() {
			return iso_639_1;
		}

		public void setIso_639_1(String iso_639_1) {
			this.iso_639_1 = iso_639_1;
		}

		public double getVote_average() {
			return vote_average;
		}

		public void setVote_average(double vote_average) {
			this.vote_average = vote_average;
		}

		public int getVote_count() {
			return vote_count;
		}

		public void setVote_count(int vote_count) {
			this.vote_count = vote_count;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

}
