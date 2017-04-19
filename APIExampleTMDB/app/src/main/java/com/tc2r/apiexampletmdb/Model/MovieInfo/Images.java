package com.tc2r.apiexampletmdb.Model.MovieInfo;

import java.util.List;

/**
 * Created by Tc2r on 4/18/2017.
 * <p>
 * Description:
 */

public class Images {
	private List<Backdrop> backdrops;
	private List<Poster> posters;

	public List<Backdrop> getBackdrops() {
		return backdrops;
	}

	public void setBackdrops(List<Backdrop> backdrops) {
		this.backdrops = backdrops;
	}

	public List<Poster> getPosters() {
		return posters;
	}

	public void setPosters(List<Poster> posters) {
		this.posters = posters;
	}
}
