package appPackage;

import java.util.ArrayList;

public class User {
	
	ArrayList<TVShow> favorite = new ArrayList<TVShow>();
	public User()
	{	
	}
	
	public void addFavorite(Integer id)
	{
		TVShow newFavorite = new TVShow(id);
		favorite.add(newFavorite);			
	}
	
	public String AllFavtoString ()
	{
		String description = "Favorite TV Shows : \n \n";
		for(int i = 0; i < favorite.size(); i++)
	    { 
	      description += favorite.get(i).toString() + "----" +"\n" ;
	    }  
		return description;
	}
	
	public void removeFavorite(Integer id)
	{
		for(int i = 0; i < favorite.size(); i++){
			if (favorite.get(i).getId() == id);
				favorite.remove(i);
		}
	}
	
	
}
