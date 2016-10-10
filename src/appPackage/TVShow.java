package appPackage;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.json.JSONObject;

public class TVShow {
	public String name; 
	public String creator;
	public String genre;
	public Integer id;
	public boolean inProduction;
	public Integer nbEpisodes;
	public Integer nbSeasons;
	public String countryOfOrigin;
	public String overview;
	public BufferedImage poster;
	public ArrayList<TVSeason> seasons; // Is it really useful ?
	public Double popularity;
	public Double voteAverage;
	public Integer voteCount;
	
	public TVShow(Integer id){
		String url = "https://api.themoviedb.org/3/tv/"+id.toString()+"?api_key="+Main.apiKey;
		JSONObject json = Main.getJSONAtURL(url);
		
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
			this.poster = ImageIO.read(new URL("http://image.tmdb.org/t/p/"+sizeOption+json.optString("poster_path")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString(){
		String descriptionString = "";
		descriptionString+= this.name+" (id:"+this.id+")"+" created by: "+this.creator+". Origin: "+this.countryOfOrigin+".\n";
		descriptionString+= this.genre+"\n";
		descriptionString+= "Number of seasons: "+this.nbSeasons+" and episodes: "+this.nbEpisodes+(inProduction?". Still in production.\n":". Production complete.\n");
		descriptionString+= this.overview+"\n";
		descriptionString+= "Popularity: "+this.popularity+"\n";
		descriptionString+= "User rating: "+this.voteAverage+"/10 ("+this.voteCount+" votes)\n";
		return descriptionString;
	}
}
