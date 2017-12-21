package test;

import com.eric.demo.commons.util.HttpClientUtil;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestGenerator {
    private File configFile;

    @Before
    public void before() {
        //读取mybatis参数
        configFile = new File("D:/prod/tcc/src/main/resources/mybatis-generator-config.xml");

    }

    @Test
    public void test() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }


    @Test
    public void  test1()throws Exception{
        while (true){
            String a=  HttpClientUtil.httpGetRequest("http://localhost:55673/json/CHQueryBaiduMapAddress?Keyword=%E9%93%B6%E8%A1%8C&CityName=%E5%8C%97%E4%BA%AC&pageNum=0&pageSize=10&cityOnly=true");
            System.out.println(a);
        }
    }
}
