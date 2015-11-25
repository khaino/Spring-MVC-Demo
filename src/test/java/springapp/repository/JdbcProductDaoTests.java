package springapp.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import springapp.domain.Product;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "test-context.xml") 
@Transactional
public class JdbcProductDaoTests{

	@Autowired
    private ProductDao productDao;
    
    public void setProductDao(ProductDao productDao) {
    	
        this.productDao = productDao;
    }

    @Test
    public void testGetProductList() {
        
        List<Product> products = productDao.getProductList();        
        assertEquals("wrong number of products?", 3, products.size());               
    }
    
    @Test
    @Rollback(true)
    public void testSaveProduct() {
        
        List<Product> products = productDao.getProductList();

        for (Product p : products) {
            p.setPrice(200.12);
            productDao.saveProduct(p);
        }
        
        List<Product> updatedProducts = productDao.getProductList();

        for (Product p : updatedProducts) {
            assertEquals("wrong price of product?", Double.valueOf(200.12), p.getPrice());
           
        }      
    }
}