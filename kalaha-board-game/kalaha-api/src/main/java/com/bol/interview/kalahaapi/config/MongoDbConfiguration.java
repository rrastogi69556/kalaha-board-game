package com.bol.interview.kalahaapi.config;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class MongoDbConfiguration extends AbstractMongoClientConfiguration {

    private final String mongoDbUri;
    private final String dbName;

    public MongoDbConfiguration(@Value("${spring.data.mongodb.uri:mongodb://mongodb:27017/kalaha_db}") String mongoDbUri,
                                @Value("${spring.data.mongodb.database:kalaha_db}") String dbName
                                ) {
        this.mongoDbUri = mongoDbUri;
        this.dbName = dbName;
    }
    @Override
    protected String getDatabaseName() {
        return this.dbName;
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(mongoDbUri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("com.bol.interview.kalahaapi");
    }

}
