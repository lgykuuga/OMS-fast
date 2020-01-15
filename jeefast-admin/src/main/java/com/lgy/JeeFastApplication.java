package com.lgy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动程序
 *
 * @author lgy
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class JeeFastApplication {
    public static void main(String[] args) {
        SpringApplication.run(JeeFastApplication.class, args);
        System.out.println("Hello World!! 启动成功" +
                " __       ____                \n" +
                "/\\ \\     /\\  _`\\              \n" +
                "\\ \\ \\    \\ \\ \\L\\_\\  __  __    \n" +
                " \\ \\ \\  __\\ \\ \\L_L /\\ \\/\\ \\   \n" +
                "  \\ \\ \\L\\ \\\\ \\ \\/, \\ \\ \\_\\ \\  \n" +
                "   \\ \\____/ \\ \\____/\\/`____ \\ \n" +
                "    \\/___/   \\/___/  `/___/> \\\n" +
                "                        /\\___/\n" +
                "                        \\/__/");
    }
}