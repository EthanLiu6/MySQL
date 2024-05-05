package ethanliu.jdbc;

import com.mysql.jdbc.Driver;
import org.junit.Test;

import java.sql.*;

/**
 * @Author EthanLiu 16693226842@163.com
 * @Date 2024/5/4 13:22
 * @Project MySQL
 * @Theme xxx
 */
public class Demo2 {

    @Test
    public void jdbcDemo2() throws ClassNotFoundException, SQLException {
        // 1. 注册MySQL驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2. 连接数据库
        final String URL = "jdbc:mysql://127.0.0.1:3306/Server";
        final String NAME = "root";
        final String PWD = "l20031220";
        Connection conn = DriverManager.getConnection(URL, NAME, PWD);

        // 3. 编写SQL语句
        String strUser = "aaa";
        String strPwd = "l20031220";
        String sql = "Select username, password\n" +
                "from Login\n" +
                "where username='" + strUser + "' and password='"+ strPwd +"';";

        // 4. 执行SQL语句
        Statement stmt = conn.createStatement();// 创建执行对象
        ResultSet res = stmt.executeQuery(sql);// 执行SQL并接收返回结果

        // 5. 处理SQL执行结果
        if (res.next()) { // 当执行结果只有一条的时候, 用if
            String name = res.getString("username");
            String pwd = res.getString("password");
            System.out.println("欢迎" + name + "登录");
        } else {
            System.out.println("输入账号密码有误");
        }

        // 6. 释放资源(从下到上)
        res.close();
        stmt.close();
        conn.close();
    }


}
