package com.tc2r.apiexampleomdb.data;

/**
 * Created by Tc2r on 4/10/2017.
 * <p>
 * Description:
 */

public class MovieData {

	private String title;
	private String year;
	private String imdbID;
	private String type;
	private String posterImage;

	public MovieData(String title, String year, String imdbID, String type, String posterImage) {
		this.title = title;
		this.year = year;
		this.imdbID = imdbID;
		this.type = type;
		this.posterImage = posterImage;
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

	public String getImdbID() {
		return imdbID;
	}

	public void setImdbID(String imdbID) {
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
}
