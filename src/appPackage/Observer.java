package appPackage;

public interface Observer {
	public default void update (String query) {}
	
	public default void update() {}
	
	public default void update(int tVShowId) {}
}
