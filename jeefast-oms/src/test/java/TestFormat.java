/**
 * @Description TODO
 * @Author LGy
 * @Date 2020/5/13 18:57
 **/
public class TestFormat {

    public static void main(String[] args) {
        int i = 0;
        String shipGroupSeqId = frontCompWithZore(i, 5);
        System.out.println(shipGroupSeqId);
    }



    private static String frontCompWithZore(int sourceDate, int formatLength) {
        return String.format("%0" + formatLength + "d", sourceDate);
    }

}
