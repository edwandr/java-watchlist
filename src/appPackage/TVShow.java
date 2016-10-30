package appPackage;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.concurrent.Task;
import javafx.scene.image.Image;

public class TVShow{

	public String name;
	private String creator;
	private String genre;
	private Integer id;
	private boolean inProduction;
	private Integer nbEpisodes;
	private Integer nbSeasons;
	private String countryOfOrigin;
	private String overview;
	private String posterPath;
	private Image poster;
	private Image bigPoster;
	private Double popularity;
	private Double voteAverage;
	private Integer voteCount;
	private ArrayList<TVSeason> seasons = new ArrayList<TVSeason>();

	private LocalDate nextAiringTime;
	private Boolean nextEpisodeisSoon;
	
	//To notify when images are loaded
	private List<Observer> observers = new ArrayList<Observer>();

	private static ExecutorService tvShowThreadPool = Executors.newFixedThreadPool(8);
	private static Image noPosterImage = new Image("NoPoster.png");
	
	public TVShow() {
		
	}

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
			this.poster = new Image("http://image.tmdb.org/t/p/"+sizeOption+posterPath);
		} catch (Exception e) {

		}

		this.fetchNextAiringTime();
	}

	public void fetchPoster(){
		if(this.poster!=null) return;
		
		TVShow show = this;
		
		Task<Void> task = new Task<Void>() {
		    @Override protected Void call() throws Exception {
		    	
		    	//Waiting for the posterPath to be filled by the constructor but not indefinitely
		    	int counter=0;
		    	while(show.posterPath==null && counter<150){
		    		Thread.sleep(100);
		    		counter++;
		    	}
		    	if(counter==150) return null;
		    	
		    	//Dealing with shows that have no poster in the database
		    	if(show.posterPath.length()<=1){
		    		show.poster=noPosterImage;
		    		show.notifyObservers(show);
		    		return null;
		    	}
		    	
				//available size options include "w92", "w154", "w185", "w342", "w500", "w780" and "original"
				String sizeOption = "w185";
				show.poster = new Image("http://image.tmdb.org/t/p/"+sizeOption+show.posterPath);
				
				//TODO Parameter that simulate network lag with following function
				//Random rng = new Random();
				//Thread.sleep(rng.nextInt(1500));
				
				show.notifyObservers(show);
		    	return null;
		    }
		};
		
		tvShowThreadPool.execute(task);
		
	}
	
	public void fetchBigPoster(){
		if(this.bigPoster!=null) return;
		
		//To pass the show to the anonymous class of task
		TVShow show = this;
		
		Task<Void> task = new Task<Void>() {
		    @Override protected Void call() throws Exception {
				try {
					//available size options include "w92", "w154", "w185", "w342", "w500", "w780" and "original"
					String sizeOption = "w500";
					show.bigPoster = new Image("http://image.tmdb.org/t/p/"+sizeOption+show.posterPath);
				} catch (Exception e) {return null;}
				
				show.notifyObservers(show);
		    	return null;
		    }
		};
		
		tvShowThreadPool.execute(task);
		
	}

	//TODO Put the computation on a separate thread
	//TODO Avoid an output error when the show was improperly fetched or API is inaccessible and retry later
	public void fetchNextAiringTime() {
		//If the show is still in production, get the next airing time
		//Is it exactly the same as the number of seasons ? Let's calculate it just to be sure
		Integer lastSeason = this.nbSeasons;
		String url;
		JSONObject json;
		if (lastSeason >= 0) {
			url = "https://api.themoviedb.org/3/tv/" + this.id.toString()
					+ "/season/" + lastSeason.toString() + "?api_key=" + Main.apiKey;
			try {
				json = Main.getJSONAtURL(url);
			} catch (JSONException e) {
				return;
			}
			JSONArray episodes = json.getJSONArray("episodes");

			//If the season is empty, we'll look at the preceding one
			if (episodes.length() == 0) {
				lastSeason -= 1;
				if (lastSeason < 0) return;
				url = "https://api.themoviedb.org/3/tv/" + this.id.toString()
						+ "/season/" + lastSeason.toString() + "?api_key=" + Main.apiKey;
				try {
					json = Main.getJSONAtURL(url);
				} catch (JSONException e) {
					return;
				}
				episodes = json.getJSONArray("episodes");
			}

			if (episodes.length() == 0) return;
			String upcomingEpisode = episodes.getJSONObject(episodes.length() - 1).optString("air_date", "1970-01-01");
			this.nextAiringTime = LocalDate.parse(upcomingEpisode);

			for (int i = episodes.length() - 2; i >= 0; i--) {
				upcomingEpisode = episodes.getJSONObject(i).optString("air_date", "1970-01-01");
				//Check the other episodes. We look for the nearest upcoming episode that is still in the future.
				if (LocalDate.parse(upcomingEpisode).isBefore(this.nextAiringTime) && LocalDate.parse(upcomingEpisode).isAfter(LocalDate.now().minusDays(1)))
					this.nextAiringTime = LocalDate.parse(upcomingEpisode);
			}

			this.nextEpisodeisSoon = Boolean.FALSE;
			if (LocalDate.now().isAfter(this.nextAiringTime.minusDays(8)) && this.nextAiringTime.isAfter(LocalDate.now())) {
				this.nextEpisodeisSoon = Boolean.TRUE;
			}
		}
	}
	
	public static TVShow fetchFromID(Integer showID){
		return TVShow.fetchFromID(showID, tvShowThreadPool);
	}

	//TODO Retry later if the API access was unsuccessful
	public static TVShow fetchFromID(Integer showID, ExecutorService threadPool){
		TVShow result = new TVShow();
		result.id=showID;

		Task<Void> creatorTask = new Task<Void>() {
		    @Override protected Void call() throws Exception {

				String url = "https://api.themoviedb.org/3/tv/"+showID.toString()+"?api_key="+Main.apiKey;
				JSONObject json;
				try{
					json = Main.getJSONAtURL(url);
				}catch(JSONException e){return null;}

				
				if(result.name==null) result.name = json.optString("name", "[Name unavailable]");
				result.nbEpisodes = json.optInt("number_of_episodes");
				result.overview = json.optString("overview", "No overview available.");
				result.popularity = json.optDouble("popularity");
				result.nbSeasons = json.optInt("number_of_seasons");
				result.voteCount = json.optInt("vote_count");
				result.voteAverage = json.optDouble("vote_average");
				result.inProduction = json.optBoolean("in_production");
				result.posterPath = json.optString("poster_path");
				

				//Create a string representing all creators
				result.creator="";
				for(int i=0;i<json.getJSONArray("created_by").length();i++){
					result.creator+=json.getJSONArray("created_by").getJSONObject(i).optString("name");
					//Add a comma if it is not the last name of the array
					if(i<json.getJSONArray("created_by").length()-1) result.creator+=", ";
				}

				//Create a string representing all genres
				result.genre="";
				for(int i=0;i<json.getJSONArray("genres").length();i++){
					result.genre+=json.getJSONArray("genres").getJSONObject(i).optString("name");
					//Add a comma if it is not the last genre of the array
					if(i<json.getJSONArray("genres").length()-1) result.genre+=", ";
				}

				//Create a string representing all countries of origin
				result.countryOfOrigin="";
				for(int i=0;i<json.getJSONArray("origin_country").length();i++){
					result.countryOfOrigin+=json.getJSONArray("origin_country").optString(i);
					//Add a comma if it is not the last name of the array
					if(i<json.getJSONArray("origin_country").length()-1) result.countryOfOrigin+=", ";
				}

				//Verbose
				System.out.println(String.format("%-20s: text fields filled", result.name));
				
				return null;
			}
		};

		threadPool.execute(creatorTask);

		return result;
	}
	
	public void addObserver(Observer o){
		getObservers().add(o);
	}
	
	public void notifyObservers(TVShow tVShow){
		for (Observer observer : getObservers()) {
	         observer.update(tVShow);
	      }
	}

	public List<Observer> getObservers() {
		return observers;
	}

	public void setObservers(List<Observer> observers) {
		this.observers = observers;
	}

	public static ArrayList<TVShow> getPopularTVShows(){
		ArrayList<TVShow> list = new ArrayList<TVShow>();
		ExecutorService threadPool = Executors.newFixedThreadPool(20);

		String url = "https://api.themoviedb.org/3/tv/popular?api_key="+Main.apiKey;
		JSONObject json;
		try{
			json = Main.getJSONAtURL(url);
		}catch(JSONException e){
			return list;
		}

		for(int i=0;i<json.getJSONArray("results").length();i++){
			TVShow show = TVShow.fetchFromID(json.getJSONArray("results").getJSONObject(i).optInt("id"),threadPool);
			show.name = json.getJSONArray("results").getJSONObject(i).optString("name");
			list.add(show);
		}
		
		threadPool.shutdown();
		return list;
	}
	
	//TODO Deal with shows that have no posters (ex search "hello")
	public static ArrayList<TVShow> searchTVShows(String query){
		ArrayList<TVShow> list = new ArrayList<TVShow>();
		ExecutorService threadPool = Executors.newFixedThreadPool(20);

		String url = "https://api.themoviedb.org/3/search/tv?api_key="+Main.apiKey+"&query="+query;
		JSONObject json;
		try{
			json = Main.getJSONAtURL(url);
		}catch(JSONException e){
			return list;
		}

		for(int i=0;i<json.getJSONArray("results").length();i++){
			TVShow show = TVShow.fetchFromID(json.getJSONArray("results").getJSONObject(i).optInt("id"),threadPool);
			show.name = json.getJSONArray("results").getJSONObject(i).optString("name");
			list.add(show);
		}

		threadPool.shutdown();
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

	public Image getPoster() {
		return poster;
	}

	public Image getBigPoster(){
		return this.bigPoster;
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
		if(this.nextAiringTime==null){
			this.fetchNextAiringTime();
		}
		return this.nextAiringTime;
	}

	public Boolean getNextEpisodeisSoon() {
		return nextEpisodeisSoon;
	}

	public void setNextEpisodeisSoon(Boolean nextEpisodeisSoon) {
		this.nextEpisodeisSoon = nextEpisodeisSoon;
	}

	public ArrayList<TVSeason> getSeasons(){
		return seasons;
	}
	
	public static void shutdownThreadPoolNow(){
		tvShowThreadPool.shutdownNow();
	}
	
}
