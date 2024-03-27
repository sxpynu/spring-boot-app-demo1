package ynu.sxp.demo.common.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    // ModelMapper是一个对象映射工具，用于将一个对象的属性映射到另一个对象上
    // 例如将数据库中的实体映射到VO上
    // 通过这种方式，可以避免在业务逻辑中进行大量的属性赋值操作
    // 从而提高代码的可读性和可维护性
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
