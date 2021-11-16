package cn.cusanity.travel.Druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DruidDemo {
    public static void main(String[] args) throws Exception {
//        Properties pro = new Properties();
//        InputStream is = DruidDemo.class.getClassLoader().getResourceAsStream("druid.properties");
//        pro.load(is);
//        DataSource ds = DruidDataSourceFactory.createDataSource(pro);
//        Connection conn = ds.getConnection();
//        System.out.println(conn);
        Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.148.132:3306/travel", "root", "root");
        System.out.println(conn);
    }
}
