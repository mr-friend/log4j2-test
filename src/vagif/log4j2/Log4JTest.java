package vagif.log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MapMessage;

public class Log4JTest {

	public static void main(String[] args) {
		Logger feedLogger = LogManager.getLogger("FeedLogger");
		Logger logger = LogManager.getLogger();
		
		long startTime = System.currentTimeMillis();
		
		logger.entry();
		
		Map<String, String> data = new HashMap<String, String>(5);
		data.put("src", "SRC1");
		data.put("feed", "feed-123.txt");
		
		Random r = new Random(); 
		long avgLogOpTime = 0;

		for (int i=1; i<1000000; i++) {
			data.put("src", "SRC" + r.nextInt(10));
			data.put("feed", "feed-" + r.nextInt(10) + ".txt");
			
			data.put("line", String.valueOf(i));
			data.put("code", String.valueOf(r.nextInt(1000)));
			data.put("msg", "This message is the description of the code supplied as the value of the code key: "+i);
			
			long s = System.nanoTime();
			feedLogger.debug(new MapMessage(data));
			long e = System.nanoTime();

			avgLogOpTime = (avgLogOpTime == 0)?e-s:(avgLogOpTime*4+(e-s)*3)/7;
		}

		logger.debug("avgLogOpTime: " + avgLogOpTime);
		
		logger.exit(System.currentTimeMillis()-startTime);
	}

}
