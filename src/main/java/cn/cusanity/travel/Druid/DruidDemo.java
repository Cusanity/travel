package cn.cusanity.travel.Druid;

import java.sql.Connection;
import java.sql.DriverManager;

public class DruidDemo {
    public static void main(String[] args) throws Exception {
//        Properties pro = new Properties();
//        InputStream is = DruidDemo.class.getClassLoader().getResourceAsStream("druid.properties");
//        pro.load(is);
//        DataSource ds = DruidDataSourceFactory.createDataSource(pro);
//        Connection conn = ds.getConnection();
//        System.out.println(conn);
        Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.1.189:3306/travel", "root", "Wyc935398521!");
        System.out.println(conn);
    }
}
