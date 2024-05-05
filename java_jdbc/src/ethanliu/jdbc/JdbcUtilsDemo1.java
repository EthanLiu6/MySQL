package ethanliu.jdbc;

import ethanliu.jdbc.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author EthanLiu 16693226842@163.com
 * @Date 2024/5/5 12:26
 * @Project MySQL
 * @Theme JDBC工具类测试
 */
public class JdbcUtilsDemo1 {

    @Test
    public void insertTest() {

        Connection conn = null;
        PreparedStatement pstemt = null;
        int res = -1;

        try {
            conn = JdbcUtils.getConnection(); //连接数据库

            //添加事务
            conn.setAutoCommit(false);

            String sql = "insert into Login(username, password) values (?, ?);"; //编写SQL(带占位符)
            pstemt = conn.prepareStatement(sql); //数据库执行对象
            //设置占位符的值
            pstemt.setString(1, "liuaihui");
            pstemt.setString(2, "l20031220");

            //添加数据
            res = pstemt.executeUpdate(); //返回受影响行数
            //执行DML语句时，执行后返回受影响的行数   受影响行数为0表示增、删、改没有成功
            //执行DDL语句，执行后返回0表示成功

            if (res > 0) {
                conn.commit();
                System.out.println("添加成功");
            }

        } catch (SQLException e) {
            try {
                conn.rollback();
                System.out.println("添加失败");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } finally {
            //关闭资源
            JdbcUtils.close(pstemt, conn);
        }
    }
}
