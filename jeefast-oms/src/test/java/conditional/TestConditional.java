package conditional;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description TODO
 * @Author LGy
 * @Date 2020/5/25 11:17
 **/

public class TestConditional {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("chinese");
        applicationContext.register(AppConfig.class);
        applicationContext.refresh();
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName); // 这里你可以看看打印的bean是否和你想的一样
        }
        System.out.println("==================================");
    }
}