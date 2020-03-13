package test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import com.it.dao.BrandMapper;
import com.it.entity.Brand;
import com.it.entity.BrandExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

public class MyBatisTest {

    SqlSessionFactory sqlSessionFactory;

	@Before
	public void initSqlSessionFactory() throws IOException {
		String resource = "mybatis.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	/**
	 * 测试代码生成器
     * 示例1
     */
	@Test
	public void test(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        //查询所有
        List<Brand> list = mapper.selectByExample(null);
        for (Brand brand : list) {
            System.out.println(brand);
        }
    }

	/**
	 * 示例2
	 */
	@Test
	public void test2(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
		BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

		//带复杂条件的查询
		//select * from t_brand name like ？ and first_char = ?
        BrandExample example = new BrandExample();
		//排序
		example.setOrderByClause("id DESC");
		//使用example创建一个Criteria（查询准则）
		BrandExample.Criteria criteria = example.createCriteria();

		Brand brand = new Brand();
        brand.setName("中");
        brand.setFirstChar("z");

        criteria.andNameLike("%"+brand.getName()+"%");
        criteria.andFirstCharEqualTo(brand.getFirstChar());
		
        List<Brand> list2 = mapper.selectByExample(example);
		for (Brand brand2 : list2) {
				System.out.println(brand2);
		}
	}


	/**
	 * 示例2
	 */
	@Test
	public void test3(){
	    /*
		多个复杂条件
        select * from t_teacher where  (id=? and teacherName like ?) or (address like ? and birthday=？)

		TeacherExample example = new TeacherExample();

		//一个Criteria能封装一整个条件
		Criteria criteria = example.createCriteria();
		criteria.andIdGreaterThan(1);
		criteria.andTeachernameLike("%a%");

		//创建第二个查询条件 设置criteria2与criteria1为or方式关联
		Criteria criteria2 = example.or();
		criteria2.andAddressLike("%%");
		criteria2.andBirthDateBetween(new Date(), new Date());

		mapper.selectByExample(example);
         */
    }
	


}
