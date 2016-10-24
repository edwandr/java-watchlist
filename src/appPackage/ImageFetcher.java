package appPackage;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

class ImageFetcher implements Runnable {
	private String posterPath;
	private TVShow result;
	
	public ImageFetcher(String posterPath){
		this.posterPath=posterPath;
	}

	public void run() {
		System.out.println("Hey, I'm Runny !!");
		try {
			//available size options include "w92", "w154", "w185", "w342", "w500", "w780" and "original"
			String sizeOption = "w185";
			BufferedImage poster = ImageIO.read(new URL("http://image.tmdb.org/t/p/"+sizeOption+posterPath));
		} catch (Exception e) {
			
		}
		//For test
		//System.out.println(String.format("%-20s: poster retrieved", result.name));
		System.out.println("Goodbye");
	}
}