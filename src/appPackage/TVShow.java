package appPackage;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TVShow {
	private String name;
	private String creator;
	private String genre;
	private Integer id;
	private boolean inProduction;
	private Integer nbEpisodes;
	private Integer nbSeasons;
	private String countryOfOrigin;
	private String overview;
	private String posterPath;
	private BufferedImage poster;
	private Double popularity;
	private Double voteAverage;
	private Integer voteCount;
	private ArrayList<TVSeason> seasons = new ArrayList<TVSeason>();

	private LocalDate nextAiringTime;
	private Boolean nextEpisodeisSoon;

	public TVShow(Integer id){

		String url = "https://api.themoviedb.org/3/tv/"+id.toString()+"?api_key="+Main.apiKey;
		JSONObject json;
		try{
			json = Main.getJSONAtURL(url);
		}catch(JSONException e){return;}

		String defaultString = "N/A";
		this.name = json.optString("name", defaultString);
		this.nbEpisodes = json.optInt("number_of_episodes");
		this.overview = json.optString("overview", defaultString);
		this.popularity = json.optDouble("popularity");
		this.id = json.optInt("id");
		this.nbSeasons = json.optInt("number_of_seasons");
		this.voteCount = json.optInt("vote_count");
		this.voteAverage = json.optDouble("vote_average");
		this.inProduction = json.optBoolean("in_production");


		//Create a string representing all creators
		this.creator="";
		for(int i=0;i<json.getJSONArray("created_by").length();i++){
			this.creator+=json.getJSONArray("created_by").getJSONObject(i).optString("name");
			//Add a comma if it is not the last name of the array
			if(i<json.getJSONArray("created_by").length()-1) this.creator+=", ";
		}

		//Create a string representing all genres
		this.genre="";
		for(int i=0;i<json.getJSONArray("genres").length();i++){
			this.genre+=json.getJSONArray("genres").getJSONObject(i).optString("name");
			//Add a comma if it is not the last genre of the array
			if(i<json.getJSONArray("genres").length()-1) this.genre+=", ";
		}

		//Create a string representing all countries of origin
		this.countryOfOrigin="";
		for(int i=0;i<json.getJSONArray("origin_country").length();i++){
			this.countryOfOrigin+=json.getJSONArray("origin_country").optString(i, defaultString);
			//Add a comma if it is not the last name of the array
			if(i<json.getJSONArray("origin_country").length()-1) this.countryOfOrigin+=", ";
		}

		//Retrieve poster image
		try {
			//available size options include "w92", "w154", "w185", "w342", "w500", "w780" and "original"
			String sizeOption = "w185";
			this.posterPath = json.optString("poster_path");
			this.poster = ImageIO.read(new URL("http://image.tmdb.org/t/p/"+sizeOption+posterPath));
		} catch (Exception e) {}



		//If the show is still in production, get the next airing time
		//Is it exactly the same as the number of seasons ? Let's calculate it just to be sure
		Integer lastSeason = json.getJSONArray("seasons").length()-1;
		if(lastSeason>=0){
			url = "https://api.themoviedb.org/3/tv/"+this.id.toString()
					+"/season/"+lastSeason.toString()+"?api_key="+Main.apiKey;
			try{
				json = Main.getJSONAtURL(url);
			}catch(JSONException e){
				return;
			}
			JSONArray episodes = json.getJSONArray("episodes");

			//If the season is empty, we'll look at the preceding one
			if(episodes.length()==0){
				lastSeason -=1;
				if (lastSeason<0) return;
				url = "https://api.themoviedb.org/3/tv/"+this.id.toString()
				+"/season/"+lastSeason.toString()+"?api_key="+Main.apiKey;
				try{
					json = Main.getJSONAtURL(url);
				}catch(JSONException e){
					return;
				}
				episodes = json.getJSONArray("episodes");
			}

			if(episodes.length()==0) return;
			String upcomingEpisode = episodes.getJSONObject(episodes.length()-1).optString("air_date", "1970-01-01");
			this.nextAiringTime = LocalDate.parse(upcomingEpisode);

			for (int i=episodes.length()-2;i>=0;i--){
				upcomingEpisode = episodes.getJSONObject(i).optString("air_date", "1970-01-01");
				//Check the other episodes. We look for the nearest upcoming episode that is still in the future.
				if(LocalDate.parse(upcomingEpisode).isBefore(this.nextAiringTime)&&LocalDate.parse(upcomingEpisode).isAfter(LocalDate.now().minusDays(1)))
						this.nextAiringTime=LocalDate.parse(upcomingEpisode);
			}

			this.nextEpisodeisSoon = Boolean.FALSE;
			if (LocalDate.now().isAfter(this.nextAiringTime.minusDays(8)) && this.nextAiringTime.isAfter(LocalDate.now())) {
				this.nextEpisodeisSoon = Boolean.TRUE;
			}
		}
		//Make an array with all the seasons
		for(Integer i=0;i<json.getJSONArray("seasons").length();i++){
			TVSeason newSeason = new TVSeason(id,nbSeasons);
			seasons.add(newSeason);
		}
	}


	public static ArrayList<TVShow> getPopularTVShows(){
		ArrayList<TVShow> list = new ArrayList<TVShow>();

		String url = "https://api.themoviedb.org/3/tv/popular?api_key="+Main.apiKey;
		JSONObject json;
		try{
			json = Main.getJSONAtURL(url);
		}catch(JSONException e){
			return list;
		}

		for(int i=0;i<json.getJSONArray("results").length();i++){
			list.add(new TVShow(json.getJSONArray("results").getJSONObject(i).optInt("id")));
		}


		return list;
	}

	public static ArrayList<TVShow> searchTVShows(String query){
		ArrayList<TVShow> list = new ArrayList<TVShow>();

		String url = "https://api.themoviedb.org/3/search/tv?api_key="+Main.apiKey+"&query="+query;
		JSONObject json;
		try{
			json = Main.getJSONAtURL(url);
		}catch(JSONException e){
			return list;
		}

		for(int i=0;i<json.getJSONArray("results").length();i++){
			list.add(new TVShow(json.getJSONArray("results").getJSONObject(i).optInt("id")));
		}


		return list;
	}

	@Override
	public String toString(){
		String descriptionString = "";
		descriptionString+= "---------- TV SHOW DESCRIPTION ----------\n";
		descriptionString+= this.name+" (id:"+this.id+")"+" created by: "+this.creator+". Origin: "+this.countryOfOrigin+".\n";
		descriptionString+= this.genre+"\n";
		descriptionString+= "Number of seasons: "+this.nbSeasons+" and episodes: "+this.nbEpisodes+(inProduction?". Still in production.\n":". Production complete.\n");
		descriptionString+= this.overview+"\n";
		descriptionString+= "Popularity: "+this.popularity+"\n";
		descriptionString+= "User rating: "+this.voteAverage+"/10 ("+this.voteCount+" votes)\n";
		descriptionString+= "Latest episode: "+this.nextAiringTime+"\n";
		descriptionString+= "-----------------------------------------\n";
		return descriptionString;
	}

	public String getName() {
		return name;
	}

	public String getCreator() {
		return creator;
	}

	public String getGenre() {
		return genre;
	}

	public Integer getId() {
		return id;
	}

	public boolean isInProduction() {
		return inProduction;
	}

	public Integer getNbEpisodes() {
		return nbEpisodes;
	}

	public Integer getNbSeasons() {
		return nbSeasons;
	}

	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public String getOverview() {
		return overview;
	}

	public BufferedImage getPoster() {
		return poster;
	}

	public Double getPopularity() {
		return popularity;
	}

	public Double getVoteAverage() {
		return voteAverage;
	}

	public Integer getVoteCount() {
		return voteCount;
	}

	public LocalDate getNextAiringTime() {
		return this.nextAiringTime;
	}
	
	public Boolean getNextEpisodeisSoon() {
		return nextEpisodeisSoon;
	}

	public void setNextEpisodeisSoon(Boolean nextEpisodeisSoon) {
		this.nextEpisodeisSoon = nextEpisodeisSoon;

	public ArrayList<TVSeason> getSeasons(){
		return seasons;
	}
}
