package appPackage;

public interface Observer {
	public default void update (String query) {}
}
