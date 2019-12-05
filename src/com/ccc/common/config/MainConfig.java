package com.ccc.common.config;

import com.ccc.common.model._MappingKit;
import com.ccc.common.route.AdminRoute;
import com.ccc.common.route.FrontRoute;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;

public class MainConfig extends JFinalConfig {
    /**
     * 配置JFinal常量
     */
    @Override
    public void configConstant(Constants me) {

        PropKit.use("config.properties");
        me.setDevMode(PropKit.getBoolean("devMode"));

        me.setInjectDependency(true);

        me.setBaseUploadPath("upload/temp/");
        me.setBaseDownloadPath("upload/temp/");

        me.setMaxPostSize(1024 * 1024 * 10);

        me.setViewType(ViewType.JFINAL_TEMPLATE);
        me.setError404View("/error/404.html");
        me.setError500View("/error/500.html");

        me.setJsonFactory(MixedJsonFactory.me());

    }

    /**
     * 配置JFinal路由映射
     */
    @Override
    public void configRoute(Routes me) {

        me.add(new FrontRoute());
        me.add(new AdminRoute());

    }

    /**
     * 配置JFinal插件 数据库连接池 ORM 缓存等插件 自定义插件
     */
    @Override
    public void configPlugin(Plugins me) {

        DruidPlugin dbPlugin = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"));
        ActiveRecordPlugin arp = new ActiveRecordPlugin(dbPlugin);
        arp.setShowSql(PropKit.getBoolean("devMode"));
        arp.setDialect(new MysqlDialect());
        dbPlugin.setDriverClass("com.mysql.jdbc.Driver");

        /******** 在此添加数据库 表-Model 映射 *********/
        // 如果使用了JFinal Model 生成器 生成了BaseModel 把下面注释解开即可
        _MappingKit.mapping(arp);

        // 添加到插件列表中
        me.add(dbPlugin);
        me.add(arp);
    }

    /**
     * 配置全局拦截器
     */
    @Override
    public void configInterceptor(Interceptors me) {
        me.addGlobalActionInterceptor(new SessionInViewInterceptor());

    }

    /**
     * 配置全局处理器
     */
    @Override
    public void configHandler(Handlers me) {

        me.add(new ContextPathHandler("base"));

    }

    /**
     * 配置模板引擎
     */
    @Override
    public void configEngine(Engine me) {

        me.setDevMode(true);
        me.addSharedFunction("/admin/comm/_layout.html");
        me.addSharedFunction("/admin/comm/_main.html");

        me.addSharedFunction("/fronts/_comm/_layout.html");
        me.addSharedFunction("/fronts/_comm/_user.html");

    }

    public static void main(String[] args) {
        JFinal.start("WebRoot", 1314, "/", 5);
    }

}