import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description TODO
 * @Author LGy
 * @Date 2020/1/16 17:16
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedis.class)
public class TestRedis {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Test
    public void testRedis() {
        String key = "test";
        String value = "测试";
        //插入缓存
        redisTemplate.opsForValue().set(key, value);
        //取缓存
        System.out.println(redisTemplate.opsForValue().get("test"));
    }


}
