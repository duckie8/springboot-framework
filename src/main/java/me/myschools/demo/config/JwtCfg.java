package me.myschools.demo.config;

import cn.zucc.netdisc.utils.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtCfg {

    @Bean
    public FilterRegistrationBean jwtFilter(){
        final FilterRegistrationBean registrationBean=new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/file/*");
        registrationBean.addUrlPatterns("/folder/*");
        registrationBean.addUrlPatterns("/share/*");
        registrationBean.addUrlPatterns("/user/*");
        return  registrationBean;
    }
}
