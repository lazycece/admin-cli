package com.lazycece.admin.server.config.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import javax.annotation.Resource;

/**
 * @author lazycece
 */
@Configuration
public class MongoConfig {

    @Resource(name = "mongoDefaultProperties")
    private MongoProperties mongoDefaultProperties;

    @Resource(name = "mongoLogProperties")
    private MongoProperties mongoLogProperties;

    private MongoDbFactory mongoDefaultFactory(MongoProperties mongoProperties) {
        ServerAddress serverAddress = new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort());
        MongoClient mongoClient = new MongoClient(
                serverAddress,
                MongoCredential.createCredential(mongoProperties.getUsername(), mongoProperties.getDatabase(), mongoProperties.getPassword()),
                new MongoClientOptions.Builder().build());
        return new SimpleMongoDbFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Bean("mongoTemplate")
    public MongoTemplate mongoDefaultTemplate() {
        return new MongoTemplate(mongoDefaultFactory(mongoDefaultProperties));
    }

    @Bean("mongoLogTemplate")
    public MongoTemplate mongoLogTemplate() {
        return new MongoTemplate(mongoDefaultFactory(mongoLogProperties));
    }
}
