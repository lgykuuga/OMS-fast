import java.util.Date;

/**
 * @Description TODO
 * @Author LGy
 * @Date 2019/12/28 17:19
 **/
public class TestDate {
    public static void main(String[] args) {
        System.out.println(new Date());

        String begin = "Mon Jun 01 08:00:00 CST 2020";

        Date date = new Date(begin);
        System.out.println(date);


    }
}
