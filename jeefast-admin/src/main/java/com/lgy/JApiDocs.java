package com.lgy;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;

/**
 * @Description JApiDocs 文档生成
 * https://japidocs.agilestudio.cn/#/zh-cn/
 * @Author LGy
 * @Date 2020/6/20 11:38
 **/
public class JApiDocs {

    public static void main(String[] args) {

        DocsConfig config = new DocsConfig();
        // 项目根目录
        config.setProjectPath("D:\\IdeaProjects\\jeefast2.0");
        // 项目名称
        config.setProjectName("OMS");
        // 声明该API的版本
        config.setApiVersion("V1.0");
        // 生成API 文档所在目录-
        // 配置自动生成
        config.setAutoGenerate(Boolean.TRUE);
        // 执行生成文档
        Docs.buildHtmlDocs(config);
    }

}
