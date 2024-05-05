package ethanliu.example;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @Author EthanLiu 16693226842@163.com
 * @Date 2024/5/5 15:33
 * @Project MySQL
 * @Theme xxx
 */
//Dao类
public class BrandDao {

    private static Properties prop = null;
    private static Connection conn = null;
    private static DataSource ds = null;
    private static PreparedStatement pstmt = null;
    private static String sql = null;
    private static ResultSet res = null;
    private static int resStatue = -1;


    // 建立连接池对象并建立连接
    static {
        //数据库连接
        prop = new Properties();
        try {
            prop.load(new FileReader("src/jdbc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 建立连接池对象, 使用配置文件参数
        try {
            ds = DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            conn = ds.getConnection(); //建立连接对象
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.setAutoCommit(false); //开启手动提交事务
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //1. 全部查询
    public static List<Brand> findAll() throws SQLException {
        sql = "SELECT id, brand_name, company_name, ordered, description, status FROM tb_brand;";
        pstmt = conn.prepareStatement(sql);
        ResultSet res = pstmt.executeQuery();
        conn.commit();

        //处理查询结果
        List<Brand> brands = new ArrayList<>();
        while (res != null && res.next()) {
            int id = res.getInt("id");
            String brandName = res.getString("brand_name");
            String companyName = res.getString("company_name");
            int ordered = res.getInt("ordered");
            String description = res.getString("description");
            int status = res.getInt("status");

            //创建Brand对象
            Brand brand = new Brand(id, brandName, companyName, ordered, description, status);
            brands.add(brand);
        }
        //关闭资源
        {
            res.close();
            pstmt.close();
            conn.close();
        }

        return brands;
    }

    //2. 添加
    public static void addBrand(Brand brand) throws SQLException {

        int id = brand.getId();
        String brandName = brand.getBrandName();
        String companyName = brand.getCompanyName();
        int ordered = brand.getOrdered();
        String description = brand.getDescription();
        int status = brand.getStatus();

        sql = "insert into tb_brand(id, brand_name, company_name, ordered, description, status) values(null, ?, ?, ?, ?, ?);";
        pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, brandName);
        pstmt.setString(2, companyName);
        pstmt.setInt(3, ordered);
        pstmt.setString(4, description);
        pstmt.setInt(5, status);

        try {
//            pstmt = conn.prepareStatement(sql);
            resStatue = pstmt.executeUpdate();
            if (resStatue > 0) {
                conn.commit();
                System.out.println("添加成功");
            } else {
                System.out.println("未添加任何数据");
            }
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("添加失败!\n失败原因: " + e.getMessage());
            e.printStackTrace();
        } finally {
            //关闭资源
            pstmt.close();
            conn.close();
        }
    }

    //3. 删除
    public static void deleteBrand(int id, String brandName) throws SQLException {

        sql = "delete from tb_brand where id = ? and brand_name=?";
        try {
            //获取 PreparedStatement对象
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, brandName);
            resStatue = pstmt.executeUpdate();


            if (resStatue > 0) {
                conn.commit();
                System.out.println("删除成功");
            } else {
                System.out.println("未删除任何数据");
            }

        } catch (SQLException e) {
            conn.rollback();
            System.out.println("删除失败!\n失败原因: " + e.getMessage());
            e.printStackTrace();

        } finally {
            //释放资源
            pstmt.close();
            conn.close();
        }
    }

    //4. 修改
    public static void updateBrand(int id, String brandName, Brand brand) throws SQLException {
        String newBrandName = brand.getBrandName();
        String companyName = brand.getCompanyName();
        int ordered = brand.getOrdered();
        String description = brand.getDescription();
        int status = brand.getStatus();

        String sql = "update tb_brand set brand_name  = ?,company_name= ?,ordered= ?,description = ?,status= ? where id = ? and brand_name = ?";

        pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, newBrandName);
        pstmt.setString(2, companyName);
        pstmt.setInt(3, ordered);
        pstmt.setString(4, description);
        pstmt.setInt(5, status);
        pstmt.setInt(6, id);
        pstmt.setString(7, brandName);

        try {
            resStatue = pstmt.executeUpdate();

            if (resStatue > 0) {
                conn.commit();
                System.out.println("更改成功");
            } else {
                System.out.println("未更改任何数据");
            }

        } catch (
                SQLException e) {
            conn.rollback();
            System.out.println("更改失败!\n失败原因: " + e.getMessage());
            e.printStackTrace();

        } finally {
            //释放资源
            pstmt.close();
            conn.close();
        }
    }

}