package ethanliu.jdbc.utils;

/**
 * @Author EthanLiu 16693226842@163.com
 * @Date 2024/5/5 12:03
 * @Project MySQL
 * @Theme JDBC工具类
 */

import java.sql.*;

/**
 * 构造方法私有化
 * 提供静态方法
 */
public class JdbcUtils {
    private JdbcUtils() {
    }

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/Server";
    private static final String NAME = "root";
    private static final String PWD = "l20031220";

    // 1. 注册驱动(注册一次)
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 2. 数据库连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, NAME, PWD);
    }

    // 3. 释放资源
    public static void close(PreparedStatement pstemt, Connection conn){
        close(null, pstemt, conn); //方法重用
    }

    public static void close(ResultSet res, PreparedStatement pstemt, Connection conn) {
        if (res != null) {
            try {
                res.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (pstemt != null) {
            try {
                pstemt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
