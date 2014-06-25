package com.github.svenwltr.thetvdbtripleizer.config;

import java.net.UnknownHostException;

import org.jongo.Jongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.DB;
import com.mongodb.MongoClient;

@Configuration
public class DatabaseConfig {

	public static final String DBNAME = "tvdbcache";

	@Bean
	DB provideDatabase() throws UnknownHostException {
		return new MongoClient().getDB(DBNAME);
	}

	@Bean
	public Jongo provideJongo(DB database) {
		return new Jongo(database);

	}

}
