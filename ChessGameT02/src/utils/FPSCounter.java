package utils;

public class FPSCounter {

	private static FPSCounter instance = null;

	public static FPSCounter getInstance() {
		if (instance == null)
			instance = new FPSCounter();
		return instance;
	}

	private int fps;
	private int recorded_fps;
	private long start_time;

	public FPSCounter() {
		fps = 0;
		recorded_fps = 0;
		start_time = System.nanoTime();
	}

	public boolean count() {
		long elapsed = System.nanoTime() - start_time;
		if (elapsed > 1000000000) {
			start_time = System.nanoTime();
			recorded_fps = (int) (fps / (elapsed / 1000000000));
			fps = 0;
			return true;
		}
		fps++;
		return false;
	}

	public int getFPS() {
		return recorded_fps;
	}

}
