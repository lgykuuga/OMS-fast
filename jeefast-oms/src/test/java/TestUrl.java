import org.springframework.util.AntPathMatcher;

public class TestUrl {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();



    public static void main(String[] args) {
        String url = "www.baidu.com/application/list/abc";

        String resource = "/application";

        System.out.println(matchs(url, resource));
    }



    private static boolean matchs(String path, String... patterns) {
        String[] var2 = patterns;
        int var3 = patterns.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String pattern = var2[var4];
            if (PATH_MATCHER.match(pattern, path)) {
                return true;
            }
        }

        return false;
    }

}
