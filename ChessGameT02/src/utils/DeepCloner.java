package utils;

import java.awt.image.BufferedImage;

import com.rits.cloning.Cloner;

public class DeepCloner {

	private static Cloner instance;

	public static Cloner getInstance() {
		if (instance == null) {
			instance = new Cloner();
			instance.dontCloneInstanceOf(BufferedImage.class);
		}
		return instance;
	}

}
