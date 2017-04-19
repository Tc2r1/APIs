package com.tc2r.apiexampletmdb.Model;

/**
 * Created by Tc2r on 4/14/2017.
 * <p>
 * Description:
 */

public class MovieData {

	String title;
	String year;
	int imdbID;
	int iD;
	String backDropURL;
	String runtime;
	String genres;
	String type;
	String posterImage;
	String videoUrl;

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	String overview;
	Boolean adult;
	Double voteAvg;

	public MovieData(String title, String year, int imdbID, int iD, String backDropURL, String runtime, String genres, String type, String posterImage, String overview, Boolean adult, Double voteAvg) {
		this.title = title;
		this.year = year;
		this.imdbID = imdbID;
		this.iD = iD;
		this.backDropURL = backDropURL;
		this.runtime = runtime;
		this.genres = genres;
		this.type = type;
		this.posterImage = posterImage;
		this.overview = overview;
		this.adult = adult;
		this.voteAvg = voteAvg;
	}

	public int getiD() {
		return iD;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}

	public String getBackDropURL() {
		return backDropURL;
	}

	public void setBackDropURL(String backDropURL) {
		this.backDropURL = backDropURL;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getImdbID() {
		return imdbID;
	}

	public void setImdbID(int imdbID) {
		this.imdbID = imdbID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPosterImage() {
		return posterImage;
	}

	public void setPosterImage(String posterImage) {
		this.posterImage = posterImage;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public Boolean getAdult() {
		return adult;
	}

	public void setAdult(Boolean adult) {
		this.adult = adult;
	}

	public Double getVoteAvg() {
		return voteAvg;
	}

	public void setVoteAvg(Double voteAvg) {
		this.voteAvg = voteAvg;
	}
}
