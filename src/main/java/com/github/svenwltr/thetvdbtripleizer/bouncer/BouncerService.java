package com.github.svenwltr.thetvdbtripleizer.bouncer;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Service
public class BouncerService {

	private static final int WATCH_INTERVAL = 60;
	private static final long REQUESTS_PER_INTERVAL = 120; // 2 per second;
	private static final long MS_TO_SECONDS = 1000;

	private final BlockingDeque<Long> counter;
	private final Thread cleaner;

	protected BouncerService() {
		int limit = (int) (REQUESTS_PER_INTERVAL-1);
		counter = new LinkedBlockingDeque<>(limit);
		cleaner = new Thread(new Cleaner());
		cleaner.start();

	}

	public void lineUp() {
		try {
			counter.putLast(DateTime.now().getMillis());

		} catch (InterruptedException e) {
			e.printStackTrace();

		}

	}

	public double getLoad() {
		double numerator = counter.size();
		double denominator = REQUESTS_PER_INTERVAL;
		double load = numerator / denominator;
		return load;

	}

	private class Cleaner implements Runnable {

		@Override
		public void run() {
			try {
				while (true) {
					long now = DateTime.now().getMillis();
					long next = counter.takeFirst();
					long wait = next - now + (WATCH_INTERVAL * MS_TO_SECONDS);

					if(wait < 0)
						wait = (WATCH_INTERVAL * 1000) / REQUESTS_PER_INTERVAL;
					
					Thread.sleep(wait);

				}

			} catch (InterruptedException e) {
				e.printStackTrace();
				return;

			}

		}

	}


}
