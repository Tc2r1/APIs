package com.tc2r.apiexampletmdb.Model.MovieInfo;

import android.util.Log;

import java.util.List;

/**
 * Created by Tc2r on 4/18/2017.
 * <p>
 * Description:
 */

public class MovieModel {
	private boolean adult;
	private String backdrop_path;
	private List<Genre> genres = null;
	private String homepage;
	private int id;

	private String imdb_id;
	private String overview;
	private String poster_path;
	private String release_date;
	private int runtime;
	private String tagline;
	private String title;
	private double vote_average;
	private int vote_count;
	private Videos videos;
	private Images images;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public String getBackdrop_path() {
		return backdrop_path;
	}

	public void setBackdrop_path(String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public int getVote_count() {
		return vote_count;
	}

	public void setVote_count(int vote_count) {
		this.vote_count = vote_count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImdb_Id() {
		return imdb_id;
	}

	public void setImdb_id(String imdb_id) {
		this.imdb_id = imdb_id;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public void setPoster_path(String poster_path) {
		this.poster_path = poster_path;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getVote_average() {
		return vote_average;
	}

	public void setVote_average(double vote_average) {
		this.vote_average = vote_average;
	}

	public Videos getVideos() {
		return videos;
	}

	public void setVideos(Videos videos) {
		this.videos = videos;
	}

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}
}
