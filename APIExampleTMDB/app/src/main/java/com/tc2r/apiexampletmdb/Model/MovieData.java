package com.tc2r.apiexampletmdb.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tc2r on 4/14/2017.
 * <p>
 * Description:
 */

public class MovieData implements Parcelable {

	String title;
	String year;
	String imdbID;
	int iD;
	String backDropURL;
	String runtime;
	String genres;

	String tagline;
	String status;
	String posterImage;
	String videoUrl;
	String overview;
	Boolean adult;
	Double voteAvg;
	int voteCount;

	public MovieData(String title, String year, String imdbID, int iD, String backDropURL, String runtime, String genres, String tagline, String status, String posterImage, String videoUrl, String overview, Boolean adult, Double voteAvg, int voteCount) {
		this.title = title;
		this.year = year;
		this.imdbID = imdbID;
		this.iD = iD;
		this.backDropURL = backDropURL;
		this.runtime = runtime;
		this.genres = genres;
		this.tagline = tagline;
		this.status = status;
		this.posterImage = posterImage;
		this.videoUrl = videoUrl;
		this.overview = overview;
		this.adult = adult;
		this.voteAvg = voteAvg;
		this.voteCount = voteCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public MovieData() {

	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public int getiD() {
		return iD;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
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

	public String getImdbID() {
		return imdbID;
	}

	public void setImdbID(String imdbID) {
		this.imdbID = imdbID;
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.title);
		dest.writeString(this.year);
		dest.writeString(this.imdbID);
		dest.writeInt(this.iD);
		dest.writeString(this.backDropURL);
		dest.writeString(this.runtime);
		dest.writeString(this.genres);
		dest.writeString(this.tagline);
		dest.writeString(this.status);
		dest.writeString(this.posterImage);
		dest.writeString(this.videoUrl);
		dest.writeString(this.overview);
		dest.writeValue(this.adult);
		dest.writeValue(this.voteAvg);
		dest.writeInt(this.voteCount);
	}

	protected MovieData(Parcel in) {
		this.title = in.readString();
		this.year = in.readString();
		this.imdbID = in.readString();
		this.iD = in.readInt();
		this.backDropURL = in.readString();
		this.runtime = in.readString();
		this.genres = in.readString();
		this.tagline = in.readString();
		this.status = in.readString();
		this.posterImage = in.readString();
		this.videoUrl = in.readString();
		this.overview = in.readString();
		this.adult = (Boolean) in.readValue(Boolean.class.getClassLoader());
		this.voteAvg = (Double) in.readValue(Double.class.getClassLoader());
		this.voteCount = in.readInt();
	}

	public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
		@Override
		public MovieData createFromParcel(Parcel source) {
			return new MovieData(source);
		}

		@Override
		public MovieData[] newArray(int size) {
			return new MovieData[size];
		}
	};
}
