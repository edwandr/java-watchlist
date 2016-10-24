package appPackage;

import java.io.*;
import java.time.LocalDate;
import java.util.*;


public class User {
	
	private ArrayList<TVShow> favorite = new ArrayList<TVShow>();
	private String favoriteId;
	
	// Sauvegarder l'utilisateur à la fermeture 
	public void saveUser() {
		favoriteId = ""; 
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
	// Récupérer l'utilisateur (ses favoris) à l'ouverture
	public void loadUser() {
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
		} 
	    
	    favoriteId = favoriteId.replaceAll("\n", "").replaceAll(" " ,"");
		String[] favoriteArray = favoriteId.split("\\;",-1);
		for (int i = 0 ; i < favoriteArray.length - 1; i++) {
			addFavorite(Integer.parseInt(favoriteArray[i]));
		}
	}

	//Renvoyer le tableau des favoris 
	public ArrayList<TVShow> getFavorite(){
		return favorite;
	}
	
	// Renvoyer le tableau des favoris trié 
	public ArrayList<TVShow> getFavorite(String sortType)
	{
			ArrayList<TVShow> favoriteClone = new ArrayList<TVShow>(favorite);
			if ( "alphabetical".equals(sortType) ) {
				Collections.sort(favoriteClone, new Comparator<TVShow>() {
			        @Override
			        public int compare(TVShow TVShow1, TVShow TVShow2) {
			        	String name1 = (String)TVShow1.getName();
			            String name2 = (String)TVShow2.getName();
			            int result = name1.compareTo(name2);
			            return result;
			        }
			    });
			}		
		     else if ("popularity".equals(sortType)) {
		    	 Collections.sort(favoriteClone, new Comparator<TVShow>() {
				        @Override
				        public int compare(TVShow TVShow1, TVShow TVShow2) {
				        	Double popularity1 = (Double)TVShow1.getPopularity();
				        	Double popularity2 = (Double)TVShow2.getPopularity();
				            int result = popularity1.compareTo(popularity2);
				            if(result == 0){
				            	String name1 = (String)TVShow1.getName();
					            String name2 = (String)TVShow2.getName();
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
				        			String name1 = (String)TVShow1.getName();
						            String name2 = (String)TVShow2.getName();
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
					            	String name1 = (String)TVShow1.getName();
						            String name2 = (String)TVShow2.getName();
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
	public User()
	{	
	}
	// Donner une description des favoris
	public String AllFavtoString ()
	{
		String description = "Favorite TV Shows : \n \n";
		for(int i = 0; i < favorite.size(); i++)
	    { 
	      description += favorite.get(i).toString() + "----" +"\n" ;
	    }  
		return description;
	}
	//Supprimer un favoris
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
	//Ajouter un favoris
	public void addFavorite(Integer id)
	{
		this.removeFavorite(id);
		TVShow newFavorite = new TVShow(id);
		favorite.add(newFavorite);	
		this.saveUser();
	}
	//Test si la série est déjà dans favorites 
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
}
