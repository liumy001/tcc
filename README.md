# springBoot 微服务工程
* dao mapper entity 代码采用mybatis-generator 插件自动生成无需手写
* 生成代码无需执行maven命令，需要执行test包下TestGenerator 下方法即可，如下
```
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
```
