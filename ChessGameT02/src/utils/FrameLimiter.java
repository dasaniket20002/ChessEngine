package utils;

import java.awt.GraphicsEnvironment;

public class FrameLimiter {

	private static long startTime;
	private static int targetFPS = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0]
			.getDisplayMode().getRefreshRate();

	public static void startLimiter(long time) {
		startTime = time;
	}

	public static void limitFPS() {
		long elapsed = System.currentTimeMillis() - startTime;
		long sleepTime = 1000 / targetFPS - elapsed;

		try {
			if (sleepTime > 0)
				Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
