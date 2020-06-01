package conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @Description TODO
 * @Author LGy
 * @Date 2020/5/25 11:18
 **/
@Configuration
public class AppConfig {

    @Profile("english")
    @Bean
    public English getEnglish() {
        return new English();
    }

    @Profile("chinese")
    @Bean
    public Chinese getChinese() {
        return new Chinese();
    }

}

class Chinese {
}

class English {
}
