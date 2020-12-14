package com.lgy.common.annotation;

import com.lgy.common.constant.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;


/**
 * MyMongoDB
 *
 * @author LGy
 * @date 2020-10-13
 */
@Configuration
public class MyMongoDB implements TypeFilter {

    private static final String MONGO_AUTO_CONFIGURATION = "MongoAutoConfiguration";
    private static final String MONGO_DATA_AUTO_CONFIGURATION = "MongoDataAutoConfiguration";

    /**
     * mongoDB
     */
    @Value("${lgy.mongoDB}")
    private String mongoDB;

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        ClassMetadata classMetadata = metadataReader.getClassMetadata();

        System.out.println(classMetadata.getClassName());

        //开启mongoDB设置,用mongoDB保存,否则用DB保存
        if (Constants.ON.equals(mongoDB)) {
            return true;
        }

        return !classMetadata.getClassName().contains(MONGO_AUTO_CONFIGURATION)
                && !classMetadata.getClassName().contains(MONGO_DATA_AUTO_CONFIGURATION);
    }
}
