package appPackage;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class TVSeason {
	private String name;
	private String overview;
	private String airDate;
	private Integer seasonNum;
	private Integer seasonId;
	private Integer episodeNum;

	private ArrayList<TVEpisode> episodes = new ArrayList<TVEpisode>();
	
	public TVSeason(Integer id , Integer seasonNumber){
		
		String url = "https://api.themoviedb.org/3/tv/"+id.toString()+"/season/"+seasonNumber.toString()+"?api_key="+Main.apiKey+"&language=en-US";
		JSONObject json;
		try{
			json = Main.getJSONAtURL(url);
		}catch(JSONException e){return;}
		
		String defaultString = "N/A";
		this.name = json.optString("name", defaultString);
		this.overview = json.optString("overview",defaultString);
		this.seasonId = json.optInt("id");
		this.airDate = json.optString("air_date",defaultString);
		this.seasonNum = seasonNumber;
		this.episodeNum = 0;
		for(Integer i=0;i<json.getJSONArray("episodes").length();i++){
			this.episodeNum+= 1;
			TVEpisode newEpisode = new TVEpisode(id,seasonNum,i+1);
			episodes.add(newEpisode);
		}
		
	}
	
	@Override
	public String toString(){
		String descriptionString = "";
		descriptionString+= "---------- TV SHOW SEASON DESCRIPTION ----------\n";
		descriptionString+= this.name+" (id:"+this.seasonId+")\n";
		descriptionString+= "Season "+this.seasonNum+" \n";
		descriptionString+= this.overview+"\n";
		descriptionString+= "Number of episodes : "+this.episodeNum+" \n";
		descriptionString+= "Air date: "+this.airDate+"\n";
		descriptionString+= "---------- EPISODES IN SEASON "+this.seasonNum+" DESCRIPTION ----------\n";
		Iterator<TVEpisode> it = episodes.iterator();
        while (it.hasNext()) {
            descriptionString += it.next().toString();              
            }
		descriptionString+= "-----------------------------------------\n";
		return descriptionString;
	}
	public String getName() {
		return name;
	}
	public String getOverview() {
		return overview;
	}
	public Integer getSeasonId() {
		return seasonId;
	}
	public String getairDate() {
		return airDate;
	}
	public Integer getSeasonNum() {
		return seasonNum;
	}
	public Integer getEpisodeNum() {
		return episodeNum;
	}
	public ArrayList<TVEpisode> getEpisodes(){
		return episodes;
	}
	
}
