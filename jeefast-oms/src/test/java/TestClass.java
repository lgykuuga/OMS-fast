import com.lgy.oms.domain.Trade;
import com.lgy.oms.mapper.TradeMapper;
import com.lgy.oms.service.IShopCommodityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 测试类
 * @Author LGy
 * @Date 2019/10/17 19:21
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestClass.class)
@MapperScan("com.lgy.oms.mapper")
public class TestClass {

    @Autowired
    TradeMapper tradeMapper;
    @Autowired
    IShopCommodityService shopCommodityService;

    @Test
    public void test1() {
        String tid = "12345";
        String shop = "12345";
        List<Trade> trades = tradeMapper.checkOrderExist(tid, shop, true);
        System.out.println(trades);
    }

    public void test2() {

    }
}
