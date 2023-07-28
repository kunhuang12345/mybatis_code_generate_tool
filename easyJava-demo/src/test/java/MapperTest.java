
import com.hk.RunDemoApplication;
import com.hk.entity.po.TUser0;
import com.hk.entity.po.TUser1;
import com.hk.entity.po.TUser2;
import com.hk.entity.query.TUser0Query;
import com.hk.entity.query.TUser1Query;
import com.hk.entity.query.TUser2Query;
import com.hk.mapper.TUser0Mapper;
import com.hk.mapper.TUser1Mapper;
import com.hk.mapper.TUser2Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = RunDemoApplication.class)

public class MapperTest {

    @Resource
    private TUser0Mapper<TUser0, TUser0Query> tUser0Mapper;
    @Resource
    private TUser1Mapper<TUser1, TUser1Query> tUser1Mapper;
    @Resource
    private TUser2Mapper<TUser2, TUser2Query> tUser2Mapper;


    @Test
    public void selectList(){
        TUser0Query tUser0Query = new TUser0Query();
        tUser0Query.setDateTestEnd("2023-7-24");
        tUser0Query.setNameFuzzy("三");
        TUser1Query tUser1Query = new TUser1Query();

        List<TUser0> tUser0s = tUser0Mapper.selectList(tUser0Query);
        System.out.println(tUser0s);
        System.out.println(tUser0s.size());
        List<TUser1> tUser1s = tUser1Mapper.selectList(tUser1Query);
        System.out.println("---------------------------------------------------------");
        System.out.println(tUser1s);
        System.out.println(tUser1s.size());
        System.out.println("---------------------------------------------------------");
        TUser1Query tUser1Query1 = new TUser1Query();
        tUser1Query1.setAge(12);
        System.out.println(tUser1Mapper.selectCount(tUser1Query1));
    }

    @Test
    public void insertTest(){

        TUser0 tUser0 = new TUser0();
        tUser0.setName("张三");
        tUser0.setDateTimeTest(new Date());
        tUser0.setDateTest(new Date());
        tUser0.setAge(12);
        tUser0.setGender(8);
        tUser0.setUserId(12L);
        Integer insert = tUser0Mapper.insert(tUser0);
        System.out.println(insert);
        System.out.println(tUser0.getId());
    }


    @Test
    public void updateOrInsertTest(){
      /*  TUser0 tUser0 = new TUser0();
        tUser0.setName("张三");
        tUser0.setDateTimeTest(new Date());
        tUser0.setDateTest(new Date());
        tUser0.setBigNum(new BigDecimal(1235L));
        tUser0.setAge(13);
        tUser0.setGender(9);
        tUser0.setId(1L);
        tUser0.setUserId(12L);
        Long insert = tUser0Mapper.insertOrUpdate(tUser0);
        System.out.println(insert);
        System.out.println(tUser0.getId());*/

        TUser0 tUser0_1 = new TUser0();
        tUser0_1.setName("歌起四");
        tUser0_1.setDateTimeTest(new Date());
        tUser0_1.setDateTest(new Date());
        tUser0_1.setAge(19);
        tUser0_1.setGender(5);
        tUser0_1.setUserId(123L);
        Integer insert_ = tUser0Mapper.insertOrUpdate(tUser0_1);
        System.out.println(insert_);
        System.out.println(tUser0_1.getId());
    }

    @Test
    public void insertBatchTest(){
        List<TUser0> tUser0s = new ArrayList<>();
        TUser0 tUser0_1 = new TUser0();
        tUser0_1.setName("歌起四3");
        tUser0_1.setDateTimeTest(new Date());
        tUser0_1.setDateTest(new Date());
        tUser0_1.setAge(19);
        tUser0_1.setGender(10);
        tUser0_1.setUserId(123L);

        tUser0s.add(tUser0_1);
        TUser0 tUser0_2 = new TUser0();
        tUser0_2.setName("歌起四4");
        tUser0_2.setDateTimeTest(new Date());
        tUser0_2.setDateTest(new Date());
        tUser0_2.setAge(20);
        tUser0_2.setGender(11);
        tUser0_2.setUserId(123L);
        tUser0s.add(tUser0_2);

        Integer insertBatch = tUser0Mapper.insertBatch(tUser0s);
        System.out.println(insertBatch);
    }

    @Test
    public void insertOrUpdateBatchTest(){
        List<TUser0> tUser0s = new ArrayList<>();
        TUser0 tUser0_1 = new TUser0();
        tUser0_1.setName("歌起四3");
        tUser0_1.setAge(19);
        tUser0_1.setGender(10);
        tUser0_1.setUserId(114514L);

        tUser0s.add(tUser0_1);
        TUser0 tUser0_2 = new TUser0();
        tUser0_2.setName("歌起四6");
        tUser0_2.setAge(21);
        tUser0_2.setGender(13);
        tUser0_2.setUserId(123L);
        tUser0s.add(tUser0_2);

        Integer insertOrUpdateBatch = tUser0Mapper.insertOrUpdateBatch(tUser0s);
        System.out.println(insertOrUpdateBatch);
    }

    @Test
    public void deleteByIdTest(){
        Integer integer = tUser0Mapper.deleteById(24L);
        System.out.println(integer);
    }

    @Test
    public void selectByIdTest(){
        TUser0 tUser0 = tUser0Mapper.selectById(22L);
        System.out.println(tUser0);
    }

    @Test
    public void updateByIdTest(){
        TUser0 tUser0_1 = new TUser0();
        tUser0_1.setName("歌起四99");
        tUser0_1.setAge(19);
        tUser0_1.setGender(99);
        tUser0_1.setId(34L);
        tUser0_1.setUserId(114514L);
        Integer updateById = tUser0Mapper.updateById(tUser0_1, 34L);
        System.out.println(updateById);
        System.out.println(tUser0Mapper.updateById(tUser0_1, 34L));
    }

    @Test
    public void updateByNameAndAgeTest(){
        TUser0 tUser0_1 = new TUser0();
        tUser0_1.setName("歌起四99");
        tUser0_1.setAge(19);
        tUser0_1.setGender(99);
        tUser0_1.setId(34L);
        tUser0_1.setUserId(114514111L);
        System.out.println(tUser0Mapper.updateByNameAndAge(tUser0_1, "歌起四99", 19));
    }


}
