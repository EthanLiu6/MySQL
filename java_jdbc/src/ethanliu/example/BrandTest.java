package ethanliu.example;

import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author EthanLiu 16693226842@163.com
 * @Date 2024/5/5 15:32
 * @Project MySQL
 * @Theme 综合案例测试代码
 */
public class BrandTest {

    @Test
    public void brandDaoFindAll() throws SQLException {
        List<Brand> brands = BrandDao.findAll();
        for (Brand brand : brands) {
            System.out.println(brand.toString());
        }
    }

    @Test
    public void brandDaoAddBrand() throws SQLException {
        Brand brand = new Brand("法拉利", "法拉利科技有限公司", 12, "要想跑得快, 必须法拉利", 1);
            BrandDao.addBrand(brand);
    }

    @Test
    public void brandDaoDeleteBrand() throws SQLException {
        BrandDao.deleteBrand(7,"法拉利");
    }

    @Test
    public void brandDaoUpdateBrand() throws SQLException {
        Brand brand = new Brand("小米", "小米科技有限公司", 5, "米家的东西伴你一生",1);
        BrandDao.updateBrand(3, "小米", brand);
    }
}
