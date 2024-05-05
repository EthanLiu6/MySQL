package ethanliu.jdbc;

import com.mysql.jdbc.Driver;
import com.sun.source.tree.WhileLoopTree;
import org.junit.Test;

import java.sql.*;

/**
 * @Author EthanLiu 16693226842@163.com
 * @Date 2024/5/3 22:14
 * @Project MySQL
 * @Theme JDBC快速入门
 */
public class QuickStart {

    @Test
    public void quickStart() throws ClassNotFoundException, SQLException {
        // 1.注册驱动(确认要用哪个数据库)->mysql 8.0
        Class.forName("com.mysql.cj.jdbc.Driver"); //加载Driver驱动

        // 2. 连接数据库
        String url = "jdbc:mysql://localhost:3306/JX";
        //String url = "jdbc:mysql://localhost:3306/JX?useUnicode=true&characterEncoding=utf8"; //解决中文乱码问题
        Connection conn = DriverManager.getConnection(url, "root", "l20031220");

        // 3. 编写SQL语句
        String sql = "Select Sid, Sname, Age, City From Student";

        // 4. 发送给数据库(数据库执行SQL语句并返回执行结果)
        Statement stmt = conn.createStatement(); //基于数据库连接对象创建一个数据库操作对象
        ResultSet res = stmt.executeQuery(sql);

        // 5. 处理SQL的执行结果
        while (res.next()){
            System.out.print(res.getInt("Sid") + "\t\t");
            System.out.print(res.getString("Sname") + "\t\t");
            System.out.print(res.getInt("Age") + "\t\t");
            System.out.println(res.getString("City") + "\t\t");

        }

        // 6. 释放资源(断开和数据库的连接)
        res.close();
        stmt.close();
        conn.close();


    }
}
