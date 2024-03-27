package ynu.sxp.demo;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import ynu.sxp.demo.user.service.UserInitService;

/**
 * 应用事件配置
 */
@Configuration
@CommonsLog
public class AppEventConfig {

    private final UserInitService userInitService;
    private final Environment environment;
    AppEventConfig(UserInitService userInitService, Environment environment){
        this.userInitService = userInitService;
        this.environment = environment;
    }

    @EventListener(ApplicationReadyEvent.class) // 监听应用启动完成事件
    void onAppReady() {
        // 初始化内置角色和用户
        this.userInitService.initBuildinRole();
        this.userInitService.initBuildinUser();
        log.info("Server 启动成功！端口: " + environment.getProperty("local.server.port"));
    }

}
