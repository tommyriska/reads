package no.badask.reads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReadsApplication {

	private static final Logger log = LoggerFactory.getLogger("ReadsApplication");

	public static void main(String[] args) {
		log.info("\n" +
				"  _____                _     \n" +
				" |  __ \\              | |    \n" +
				" | |__) |___  __ _  __| |___ \n" +
				" |  _  // _ \\/ _` |/ _` / __|\n" +
				" | | \\ \\  __/ (_| | (_| \\__ \\\n" +
				" |_|  \\_\\___|\\__,_|\\__,_|___/\n" +
				"                             \n" +
				"                             ");
		SpringApplication.run(ReadsApplication.class, args);
	}

}
