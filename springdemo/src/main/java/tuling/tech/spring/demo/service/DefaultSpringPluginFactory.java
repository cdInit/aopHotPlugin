package tuling.tech.spring.demo.service;

import tuling.tech.spring.demo.po.PluginConfig;

public class DefaultSpringPluginFactory implements SpringPluginFactory{


    /**
     * 1 下载插件jar包
     * 2 装载插件jar包到classLoader
     * 3 获取plugin class 并实例化
     * 4 把advice添加到aop bean
     * 5 将插件配置保存到本地
     * @param pluginConfig
     * @param active
     */
    @Override
    public void installPlugin(PluginConfig pluginConfig, Boolean active) {

    }
}
