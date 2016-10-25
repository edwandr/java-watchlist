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
	
		
		for(int i=0;i<json.getJSONArray("episodes").length();i++){
			TVEpisode newEpisode = new TVEpisode();
			newEpisode.setEpisodeNum(Integer.parseInt(json.getJSONArray("episodes").getJSONObject(i).optString("episode_number")));
			newEpisode.setairDate(json.getJSONArray("episodes").getJSONObject(i).optString("air_date"));
			newEpisode.setName(json.getJSONArray("episodes").getJSONObject(i).optString("name"));
			newEpisode.setOverview(json.getJSONArray("episodes").getJSONObject(i).optString("overview"));
			newEpisode.setVoteAverage(Double.parseDouble(json.getJSONArray("episodes").getJSONObject(i).optString("vote_average")));
			newEpisode.setVoteCount(Integer.parseInt(json.getJSONArray("episodes").getJSONObject(i).optString("vote_count")));
			newEpisode.setEpisodeId(Integer.parseInt(json.getJSONArray("episodes").getJSONObject(i).optString("id")));
			newEpisode.setSeason(seasonNum);
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
		return this.episodes;
	}
	
	
}
