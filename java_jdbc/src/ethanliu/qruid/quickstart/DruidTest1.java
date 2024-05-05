package ethanliu.qruid.quickstart;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mysql.cj.jdbc.MysqlDataSource;
import ethanliu.jdbc.utils.JdbcUtils;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Author EthanLiu 16693226842@163.com
 * @Date 2024/5/5 14:06
 * @Project MySQL
 * @Theme Druid快速入门
 */
public class DruidTest1 {

    //要使用Druid连接池, 需要: 导入druid.jar文件
    @Test
    public void testDruid() throws Exception {

        //2. 配置文件
        Properties prop = new Properties(); //创建配置文件对象
        try {
            prop.load(new FileReader("druid.properties")); //加载配置文件
        } catch (IOException e) {
            e.printStackTrace();
        }

        //1. 建立连接池对象, 使用配置文件参数
        DataSource ds = DruidDataSourceFactory.createDataSource(prop);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 获得连接
            conn = ds.getConnection();
            // 获得发送sql的对象
            String sql = "select username, password from Login where username=? and password=?";
            pstmt = conn.prepareStatement(sql);
            // 如果有问号,需要 设置参数,注意:下标从1开始
            pstmt.setString(1, "EthanLiu");
            pstmt.setString(2, "l20031220");
            // 执行sql 获得结果
            rs = pstmt.executeQuery();
            // 处理结果
            if (rs.next()) {
                String uname = rs.getString("username");
                String pwd = rs.getString("password");
                System.out.print(uname + "\t" + pwd);

            } else {
                System.out.println("没有查到对应的用户信息!");
            }
        } catch (Exception e) {
        } finally {
            JdbcUtils.close(rs, pstmt, conn);
        }
    }
}