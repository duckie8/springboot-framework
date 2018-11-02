package me.myschools.demo.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "me.myschools.xtjauthorizationcenter.dao.authcenter",sqlSessionTemplateRef = "acSqlSessionTemplate")
public class AcDataSourceConfig {

    @Bean(name = "acDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.authcenter")
    public DataSource setDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "acDataSourceTransactionManager")
    public DataSourceTransactionManager setDataSourceTransactionManager(@Qualifier("acDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "acSqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("acDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/authcenter/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "acSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("acSqlSessionFactory")SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
