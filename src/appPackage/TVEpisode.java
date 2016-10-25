package appPackage;

import org.json.JSONException;
import org.json.JSONObject;

public class TVEpisode {
	private String airDate;
	private String name;
	private String overview;
	private Double voteAverage;
	private Integer voteCount;
	private Integer episodeId;
	private Integer season;
	private Integer episodeNum;
	

	public TVEpisode(){}
	@Override
	public String toString(){
		String descriptionString = "";
		descriptionString+= "---------- TV SHOW EPISODE DESCRIPTION ----------\n";
		descriptionString+= this.name+" (id:"+this.episodeId+")\n";
		descriptionString+= "Season "+this.season+" episode "+this.episodeNum+" \n";
		descriptionString+= this.overview+"\n";
		descriptionString+= "User rating: "+this.voteAverage+"/10 ("+this.voteCount+" votes)\n";
		descriptionString+= "Air date: "+this.airDate+"\n";
		descriptionString+= "-----------------------------------------\n";
		return descriptionString;
	}
	public String getName() {
		return name;
	}
	public String getOverview() {
		return overview;
	}
	public Double getVoteAverage() {
		return voteAverage;
	}
	public Integer getVoteCount() {
		return voteCount;
	}
	public Integer getEpisodeId() {
		return episodeId;
	}
	public String getairDate() {
		return airDate;
	}
	public Integer getSeason() {
		return season;
	}
	public Integer getEpisodeNum() {
		return episodeNum;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public void setVoteAverage(Double voteAverage) {
		this.voteAverage = voteAverage;
	}
	public void setVoteCount(Integer voteCount) {
		this.voteCount = voteCount;
	}
	public void setEpisodeId(Integer episodeId) {
		this.episodeId = episodeId;
	}
	public void setairDate(String airDate) {
		this.airDate = airDate;
	}
	public void setSeason(Integer season) {
		this.season = season;
	}
	public void setEpisodeNum(Integer episodeNum) {
		this.episodeNum = episodeNum;
	}
	
}
