package appPackage;

import org.json.JSONException;
import org.json.JSONObject;

public class TVEpisode {
	private String name;
	private String overview;
	private Double voteAverage;
	private Integer voteCount;
	private Integer episodeId;
	private String airDate;
	private Integer season;
	private Integer episodeNum;
	

	public TVEpisode(Integer id, Integer seasonNumber, Integer episodeNumber){
		
		String url = "https://api.themoviedb.org/3/tv/"+id.toString()+"/season/"+seasonNumber.toString()+"/episode/"+episodeNumber.toString()+"?api_key="+Main.apiKey+"&language=en-US";
		JSONObject json;
		try{
			json = Main.getJSONAtURL(url);
		}catch(JSONException e){return;}
		
		String defaultString = "N/A";
		this.name = json.optString("name", defaultString);
		this.overview = json.optString("overview",defaultString);
		this.voteCount = json.optInt("vote_count");
		this.voteAverage = json.optDouble("vote_average");
		this.episodeId = json.optInt("id");
		this.airDate = json.optString("air_date",defaultString);
		this.season = seasonNumber;
		this.episodeNum = episodeNumber;
	}
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
	
}
