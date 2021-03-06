package appPackage;

import java.io.*;
import java.time.LocalDate;
import java.util.*;



public class User{
	
	private ArrayList<TVShow> favorite = new ArrayList<TVShow>();

	private static User currentUser;	

	/**
	 * Save favorites 
	 */
	public void saveUser() {
		String favoriteId = "";
		Iterator<TVShow> it = favorite.iterator();
		while (it.hasNext()) {
			favoriteId += it.next().getId().toString() + ";";
		}
		
		try {
			PrintWriter out = new PrintWriter("sauvegarde.txt");
			out.println(favoriteId);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Load user and its favorites
	 */
	public void loadUser() {
		
		Thread t = new Thread(new Runnable() {
            public void run() {
            	String favoriteId;
        	    try {
        	    	BufferedReader reader = new BufferedReader(new FileReader ("sauvegarde.txt"));
        		    String         line = null;
        		    StringBuilder  stringBuilder = new StringBuilder();
        		    String         ls = System.getProperty("line.separator");
        	        while((line = reader.readLine()) != null) {
        	            stringBuilder.append(line);
        	            stringBuilder.append(ls);
        	        }
        	        favoriteId = stringBuilder.toString();
        	        reader.close();
        	    } catch (IOException e) {
        			e.printStackTrace();
        			return;
        		} 
        	    
        	    favoriteId = favoriteId.replaceAll("\n", "").replaceAll(" " ,"");
        		String[] favoriteArray = favoriteId.split("\\;",-1);
        		for (int i = 0 ; i < favoriteArray.length - 1; i++) {
        			TVShow show = TVShow.fetchFromID(Integer.parseInt(favoriteArray[i]));
        			User.getUser().addFavorite(show.getId());
        		}
        		Iterator<TVShow> it = favorite.iterator();
        		while (it.hasNext()) {
        			it.next().fetchNextAiringTime();
        		}
            }
        });

        t.start();
	}

	/**
	 * @param sortType : "alphabetical", "popularity", "airingTime", "lastAdded"
	 * @return sorted favorites array
	 */
	public ArrayList<TVShow> getFavorite(String sortType)
	{
		ArrayList<TVShow> favoriteClone = new ArrayList<TVShow>(favorite);
		if ( "alphabetical".equals(sortType) ) {
			Collections.sort(favoriteClone, new Comparator<TVShow>() {
				@Override
				public int compare(TVShow TVShow1, TVShow TVShow2) {
					String name1 = TVShow1.getName();
					String name2 = TVShow2.getName();
					if (name1 == null || name2 == null){
						return 0;
					}
					int result = name1.compareTo(name2);
					return result;
				}
			});
		}		
		else if ("popularity".equals(sortType)) {
			Collections.sort(favoriteClone, new Comparator<TVShow>() {
				@Override
				public int compare(TVShow TVShow1, TVShow TVShow2) {
					Double popularity1 = TVShow1.getPopularity();
					Double popularity2 = TVShow2.getPopularity();
					if (popularity1 == null || popularity2 == null){
						String name1 = TVShow1.getName();
						String name2 = TVShow2.getName();
						if (name1 == null || name2 == null){
							return 0;
						}
						int result2 = name1.compareTo(name2);
						return result2;
					}
					int result = popularity1.compareTo(popularity2);
					if(result == 0){
						String name1 = TVShow1.getName();
						String name2 = TVShow2.getName();
						if (name1 == null || name2 == null){
							return 0;
						}
						int result2 = name1.compareTo(name2);
						return result2;
					}
					return result;
				}
			});	    	 	    
		}
		
		else if ("airingTime".equals(sortType)) {

			Collections.sort(favoriteClone, new Comparator<TVShow>() {
				@Override
				public int compare(TVShow TVShow1, TVShow TVShow2) {
					LocalDate nextAiringTime1 = TVShow1.getNextAiringTime();
					LocalDate nextAiringTime2 = TVShow2.getNextAiringTime();
					if (nextAiringTime1 == null){
						if (nextAiringTime2 == null){
							String name1 = TVShow1.getName();
							String name2 = TVShow2.getName();
							if (name1 == null || name2 == null){
								return 0;
							}
							int result2 = name1.compareTo(name2);
							return result2;
						}
						else {
							return -1;
						}
					}
					else if (nextAiringTime2 == null){
						return 1;
					}
					else {
						int result = nextAiringTime1.compareTo(nextAiringTime2);
						if(result == 0){
							String name1 = TVShow1.getName();
							String name2 = TVShow2.getName();
							int result2 = name1.compareTo(name2);
							return result2;
						}
						return result;
					}

				}
			});	    	 	    
		}
		else if ("lastAdded".equals(sortType)) {}
		return favoriteClone;
	}
	
	/**
	 * @return unsorted favorites array
	 */
	public ArrayList<TVShow> getFavorite(){
		return this.getFavorite("lastAdded");
	}
	
	/**
	 * Effectively makes all constructor private.
	 */
	private User()
	{	
	}
	

	/**
	 * @return description of favorites
	 */
	public String AllFavtoString()
	{
		String description = "Favorite TV Shows : \n \n";
		for(int i = 0; i < favorite.size(); i++)
	    { 
	      description += favorite.get(i).toString() + "----" +"\n" ;
	    }  
		return description;
	}
	
	
	/**
	 * Remove one TVShow in favorites
	 * @param id : TVShow id 
	 */
	public void removeFavorite(Integer id)
	{
		Iterator<TVShow> it = favorite.iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(id)) {
                it.remove();                
            }
        }
        this.saveUser();
	}
	/**
	 * Add favorite
	 * @param id : TVShow id
	 */
	public void addFavorite(Integer id)
	{
		this.removeFavorite(id);
		TVShow newFavorite = TVShow.fetchFromID(id);
		favorite.add(newFavorite);	
		this.saveUser();
	}

	
	/**
	 * @param id : TVShow id 
	 * @return boolean if TVShow is in favorite or not
	 */
	public boolean isInFavorite(Integer id){
		boolean result = false ;
		Iterator<TVShow> it = favorite.iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(id)) {
                result = true ;                
            }
        }
		return result;
	}
	
	/**
	 * @return favorite array
	 */
	public ArrayList<TVShow> getFavoriteShows(){
		return this.favorite;
	}
	
	/**
	 * @return currentUser
	 */
	public static User getUser() {
		if (currentUser == null){ 
			currentUser = new User();
			currentUser.loadUser();
		}
		return currentUser; 
	}
}
