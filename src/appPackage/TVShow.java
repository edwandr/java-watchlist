package appPackage;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javafx.concurrent.Task;

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
	public BufferedImage poster;
	
	private Double popularity;
	private Double voteAverage;
	private Integer voteCount;
	
	private LocalDate nextAiringTime;
	
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
			this.poster = ImageIO.read(new URL("http://image.tmdb.org/t/p/"+sizeOption+json.optString("poster_path")));
		} catch (Exception e) {
			
		}

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
				upcomingEpisode = episodes.getJSONObject(episodes.length()-1).optString("air_date", "1970-01-01");
				//Check the other episodes. We look for the nearest upcoming episode that is still in the future.
				if(LocalDate.parse(upcomingEpisode).isBefore(this.nextAiringTime)&&LocalDate.parse(upcomingEpisode).isAfter(LocalDate.now()))
						this.nextAiringTime=LocalDate.parse(upcomingEpisode);
			}
		}
		
	}
	
	public TVShow(Integer showID, String withMultithreading){
		class Creator implements Runnable {
			public void run() {

				String url = "https://api.themoviedb.org/3/tv/"+showID.toString()+"?api_key="+Main.apiKey;
				JSONObject json;
				try{
					json = Main.getJSONAtURL(url);
				}catch(JSONException e){return;}

				String defaultString = "N/A";
				name = json.optString("name", defaultString);
				nbEpisodes = json.optInt("number_of_episodes");
				overview = json.optString("overview", defaultString);
				popularity = json.optDouble("popularity");
				id = json.optInt("id");
				nbSeasons = json.optInt("number_of_seasons");
				voteCount = json.optInt("vote_count");
				voteAverage = json.optDouble("vote_average");
				inProduction = json.optBoolean("in_production");


				//Create a string representing all creators
				creator="";
				for(int i=0;i<json.getJSONArray("created_by").length();i++){
					creator+=json.getJSONArray("created_by").getJSONObject(i).optString("name");
					//Add a comma if it is not the last name of the array
					if(i<json.getJSONArray("created_by").length()-1) creator+=", ";
				}

				//Create a string representing all genres
				genre="";
				for(int i=0;i<json.getJSONArray("genres").length();i++){
					genre+=json.getJSONArray("genres").getJSONObject(i).optString("name");
					//Add a comma if it is not the last genre of the array
					if(i<json.getJSONArray("genres").length()-1) genre+=", ";
				}

				//Create a string representing all countries of origin
				countryOfOrigin="";
				for(int i=0;i<json.getJSONArray("origin_country").length();i++){
					countryOfOrigin+=json.getJSONArray("origin_country").optString(i, defaultString);
					//Add a comma if it is not the last name of the array
					if(i<json.getJSONArray("origin_country").length()-1) countryOfOrigin+=", ";
				}
				
				//For test
				System.out.println(String.format("%-20s: text fields filled", name));
				/*
				class ImageFetcher implements Runnable {
					private String posterPath;
					
					public ImageFetcher(String posterPath){
						this.posterPath=posterPath;
					}

					public void run() {
						System.out.println("Hey, I'm Runny !!");
						try {
							//available size options include "w92", "w154", "w185", "w342", "w500", "w780" and "original"
							String sizeOption = "w185";
							poster = ImageIO.read(new URL("http://image.tmdb.org/t/p/"+sizeOption+posterPath));
						} catch (Exception e) {
							
						}
						//For test
						System.out.println(String.format("%-20s: poster retrieved", name));
					}
				}*/

				Thread runny = new Thread(new ImageFetcher(json.optString("poster_path")));
				runny.setPriority(Thread.MAX_PRIORITY);
				runny.start();
				try {
					runny.join();
				} catch (Exception e) {
					// TODO: handle exception
				}

				class AirDateFetcher implements Runnable {
					private JSONObject json;
					
					public AirDateFetcher(JSONObject json){
						this.json=json;
					}

					public void run() {
						//If the show is still in production, get the next airing time
						//Is it exactly the same as the number of seasons ? Let's calculate it just to be sure
						Integer lastSeason = json.getJSONArray("seasons").length()-1;
						String url;
						if(lastSeason>=0){
							url = "https://api.themoviedb.org/3/tv/"+id.toString()
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
								url = "https://api.themoviedb.org/3/tv/"+id.toString()
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
							nextAiringTime = LocalDate.parse(upcomingEpisode);

							for (int i=episodes.length()-2;i>=0;i--){
								upcomingEpisode = episodes.getJSONObject(episodes.length()-1).optString("air_date", "1970-01-01");
								//Check the other episodes. We look for the nearest upcoming episode that is still in the future.
								if(LocalDate.parse(upcomingEpisode).isBefore(nextAiringTime)&&LocalDate.parse(upcomingEpisode).isAfter(LocalDate.now()))
									nextAiringTime=LocalDate.parse(upcomingEpisode);
							}
						}
						
						//For test
						System.out.println(String.format("%-20s: next airing time determined", name));
					}
				}
				
				Thread runnaroo = new Thread(new AirDateFetcher(json));
				runnaroo.start();
				
			}
		}
		
		Thread theArtist = new Thread(new Creator());
		theArtist.start();
		
		return;
	}

	/*
	//TODO Make a function to clone a TVShow
	public TVShow(TVShow show){

		this.name = show.name;
	}
	*/
	
	public void fetchImage(){
		
	}
	

//	public static TVShow fetchFromID(Integer showID){
//		TVShow result = new TVShow();
//		
//		class Creator implements Runnable {
//			public void run() {
//
//				String url = "https://api.themoviedb.org/3/tv/"+showID.toString()+"?api_key="+Main.apiKey;
//				JSONObject json;
//				try{
//					json = Main.getJSONAtURL(url);
//				}catch(JSONException e){return;}
//
//				String defaultString = "N/A";
//				result.name = json.optString("name", defaultString);
//				result.nbEpisodes = json.optInt("number_of_episodes");
//				result.overview = json.optString("overview", defaultString);
//				result.popularity = json.optDouble("popularity");
//				result.id = json.optInt("id");
//				result.nbSeasons = json.optInt("number_of_seasons");
//				result.voteCount = json.optInt("vote_count");
//				result.voteAverage = json.optDouble("vote_average");
//				result.inProduction = json.optBoolean("in_production");
//
//
//				//Create a string representing all creators
//				result.creator="";
//				for(int i=0;i<json.getJSONArray("created_by").length();i++){
//					result.creator+=json.getJSONArray("created_by").getJSONObject(i).optString("name");
//					//Add a comma if it is not the last name of the array
//					if(i<json.getJSONArray("created_by").length()-1) result.creator+=", ";
//				}
//
//				//Create a string representing all genres
//				result.genre="";
//				for(int i=0;i<json.getJSONArray("genres").length();i++){
//					result.genre+=json.getJSONArray("genres").getJSONObject(i).optString("name");
//					//Add a comma if it is not the last genre of the array
//					if(i<json.getJSONArray("genres").length()-1) result.genre+=", ";
//				}
//
//				//Create a string representing all countries of origin
//				result.countryOfOrigin="";
//				for(int i=0;i<json.getJSONArray("origin_country").length();i++){
//					result.countryOfOrigin+=json.getJSONArray("origin_country").optString(i, defaultString);
//					//Add a comma if it is not the last name of the array
//					if(i<json.getJSONArray("origin_country").length()-1) result.countryOfOrigin+=", ";
//				}
//				
//				//For test
//				System.out.println(String.format("%-20s: text fields filled", result.name));
//				/*
//				class ImageFetcher implements Runnable {
//					private String posterPath;
//					
//					public ImageFetcher(String posterPath){
//						this.posterPath=posterPath;
//					}
//
//					public void run() {
//						System.out.println("Hey, I'm Runny !!");
//						try {
//							//available size options include "w92", "w154", "w185", "w342", "w500", "w780" and "original"
//							String sizeOption = "w185";
//							result.poster = ImageIO.read(new URL("http://image.tmdb.org/t/p/"+sizeOption+posterPath));
//						} catch (Exception e) {
//							
//						}
//						//For test
//						System.out.println(String.format("%-20s: poster retrieved", result.name));
//					}
//				}
//
//				Thread runny = new Thread(new ImageFetcher(json.optString("poster_path")));
//				runny.setName("PosterFetcher");
//				runny.start();*/
//				
//				Task<Void> task = new Task<Void>() {
//				    @Override protected Void call() throws Exception {
//				    	System.out.println("Hey, I'm Born Again Runny !!");
//						try {
//							//available size options include "w92", "w154", "w185", "w342", "w500", "w780" and "original"
//							String sizeOption = "w185";
//							result.poster = ImageIO.read(new URL("http://image.tmdb.org/t/p/"+sizeOption+json.optString("poster_path")));
//						} catch (Exception e) {
//							
//						}
//						//For test
//						System.out.println(String.format("%-20s: poster retrieved", result.name));
//				    	
//				    	
//				    	return null;
//				    }
//				};
//				
//				Thread runny = new Thread(task);
//				runny.setName("PosterFetcher");
//				runny.start();
//				
//				
//				
//				class AirDateFetcher implements Runnable {
//					private JSONObject json;
//					
//					public AirDateFetcher(JSONObject json){
//						this.json=json;
//					}
//
//					public void run() {
//						//If the show is still in production, get the next airing time
//						//Is it exactly the same as the number of seasons ? Let's calculate it just to be sure
//						Integer lastSeason = json.getJSONArray("seasons").length()-1;
//						String url;
//						if(lastSeason>=0){
//							url = "https://api.themoviedb.org/3/tv/"+result.id.toString()
//							+"/season/"+lastSeason.toString()+"?api_key="+Main.apiKey;
//							try{
//								json = Main.getJSONAtURL(url);
//							}catch(JSONException e){
//								return;
//							}
//							JSONArray episodes = json.getJSONArray("episodes");
//							
//							//If the season is empty, we'll look at the preceding one
//							if(episodes.length()==0){
//								lastSeason -=1;
//								if (lastSeason<0) return;
//								url = "https://api.themoviedb.org/3/tv/"+result.id.toString()
//								+"/season/"+lastSeason.toString()+"?api_key="+Main.apiKey;
//								try{
//									json = Main.getJSONAtURL(url);
//								}catch(JSONException e){
//									return;
//								}
//								episodes = json.getJSONArray("episodes");
//							}
//							
//							if(episodes.length()==0) return;
//							String upcomingEpisode = episodes.getJSONObject(episodes.length()-1).optString("air_date", "1970-01-01");
//							result.nextAiringTime = LocalDate.parse(upcomingEpisode);
//
//							for (int i=episodes.length()-2;i>=0;i--){
//								upcomingEpisode = episodes.getJSONObject(episodes.length()-1).optString("air_date", "1970-01-01");
//								//Check the other episodes. We look for the nearest upcoming episode that is still in the future.
//								if(LocalDate.parse(upcomingEpisode).isBefore(result.nextAiringTime)&&LocalDate.parse(upcomingEpisode).isAfter(LocalDate.now()))
//									result.nextAiringTime=LocalDate.parse(upcomingEpisode);
//							}
//						}
//						
//						//For test
//						System.out.println(String.format("%-20s: next airing time determined", result.name));
//					}
//				}
//				
//				Thread runnaroo = new Thread(new AirDateFetcher(json));
//				runnaroo.setName("AirDateFetcher");
//				runnaroo.start();
//				
//			}
//		}
//		
//		Thread theArtist = new Thread(new Creator());
//		theArtist.setName("TVShowFetcher");
//		theArtist.start();
//		
//		return result;
//	}

	public static TVShow fetchFromID(Integer showID){
		TVShow result = new TVShow();
		
		Task<Void> creatorTask = new Task<Void>() {
		    @Override protected Void call() throws Exception {

				String url = "https://api.themoviedb.org/3/tv/"+showID.toString()+"?api_key="+Main.apiKey;
				JSONObject json;
				try{
					json = Main.getJSONAtURL(url);
				}catch(JSONException e){return null;}

				String defaultString = "N/A";
				result.name = json.optString("name", defaultString);
				result.nbEpisodes = json.optInt("number_of_episodes");
				result.overview = json.optString("overview", defaultString);
				result.popularity = json.optDouble("popularity");
				result.id = json.optInt("id");
				result.nbSeasons = json.optInt("number_of_seasons");
				result.voteCount = json.optInt("vote_count");
				result.voteAverage = json.optDouble("vote_average");
				result.inProduction = json.optBoolean("in_production");


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
					result.countryOfOrigin+=json.getJSONArray("origin_country").optString(i, defaultString);
					//Add a comma if it is not the last name of the array
					if(i<json.getJSONArray("origin_country").length()-1) result.countryOfOrigin+=", ";
				}
				
				//For test
				System.out.println(String.format("%-20s: text fields filled", result.name));
				
				Task<Void> task = new Task<Void>() {
				    @Override protected Void call() throws Exception {
				    	System.out.println("Hey, I'm Born Again Runny !!");
						try {
							//available size options include "w92", "w154", "w185", "w342", "w500", "w780" and "original"
							String sizeOption = "w185";
							result.poster = ImageIO.read(new URL("http://image.tmdb.org/t/p/"+sizeOption+json.optString("poster_path")));
						} catch (Exception e) {
							
						}
						//For test
						System.out.println(String.format("%-20s: poster retrieved", result.name));
				    	
				    	
				    	return null;
				    }
				};
				
				Thread runny = new Thread(task);
				runny.setName("PosterFetcher");
				runny.start();
				
				Task<Void> taskTwo = new Task<Void>() {
				    @Override protected Void call() throws Exception {
						//If the show is still in production, get the next airing time
						//Is it exactly the same as the number of seasons ? Let's calculate it just to be sure
						Integer lastSeason = json.getJSONArray("seasons").length()-1;
						String url;
						@SuppressWarnings("unused")
						JSONObject jsonTwo;
						if(lastSeason>=0){
							url = "https://api.themoviedb.org/3/tv/"+result.id.toString()
							+"/season/"+lastSeason.toString()+"?api_key="+Main.apiKey;
							try{
								jsonTwo = Main.getJSONAtURL(url);
							}catch(JSONException e){
								return null;
							}
							JSONArray episodes = json.getJSONArray("episodes");
							
							//If the season is empty, we'll look at the preceding one
							if(episodes.length()==0){
								lastSeason -=1;
								if (lastSeason<0) return null;
								url = "https://api.themoviedb.org/3/tv/"+result.id.toString()
								+"/season/"+lastSeason.toString()+"?api_key="+Main.apiKey;
								try{
									jsonTwo = Main.getJSONAtURL(url);
								}catch(JSONException e){
									return null;
								}
								episodes = json.getJSONArray("episodes");
							}
							
							if(episodes.length()==0) return null;
							String upcomingEpisode = episodes.getJSONObject(episodes.length()-1).optString("air_date", "1970-01-01");
							result.nextAiringTime = LocalDate.parse(upcomingEpisode);

							for (int i=episodes.length()-2;i>=0;i--){
								upcomingEpisode = episodes.getJSONObject(episodes.length()-1).optString("air_date", "1970-01-01");
								//Check the other episodes. We look for the nearest upcoming episode that is still in the future.
								if(LocalDate.parse(upcomingEpisode).isBefore(result.nextAiringTime)&&LocalDate.parse(upcomingEpisode).isAfter(LocalDate.now()))
									result.nextAiringTime=LocalDate.parse(upcomingEpisode);
							}
						}
						
						//For test
						System.out.println(String.format("%-20s: next airing time determined", result.name));
						return null;
				    }
				};
				
				Thread runnaroo = new Thread(taskTwo);
				runnaroo.setName("AirDateFetcher");
				runnaroo.start();
				
				
				return null;
			}
		};
		
		Thread theArtist = new Thread(creatorTask);
		theArtist.setName("TVShowFetcher");
		theArtist.start();
		
		return result;
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
			
			//list.add(new TVShow(json.getJSONArray("results").getJSONObject(i).optInt("id")));
			//list.add(new TVShow(json.getJSONArray("results").getJSONObject(i).optInt("id"),"WithMultiThreading"));
			list.add(TVShow.fetchFromID(json.getJSONArray("results").getJSONObject(i).optInt("id")));
		}
		
		System.out.println("Hey, is everything initiated ?");
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("Maybe ?!");
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
		descriptionString+= "-----------------------------------------";
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
}
