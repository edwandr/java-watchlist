package appPackage;

public interface Observer {
	
	
	public default void update(String query) {}
	
	public default void update(Boolean type) {}
	
	public default void update(TVShow tVShow) {}

	public default void update(TVSeason seasons) {}
	
}
