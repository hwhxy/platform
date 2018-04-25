import com.fulu.game.common.enums.RedisKeyEnum;
import com.fulu.game.common.properties.Config;
import com.fulu.game.core.CoreDaoApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by bwang.abcft on 2018/4/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CoreDaoApplication.class)
@Slf4j
public class Test1 {


    @Test
    public void test1(){
        String key =RedisKeyEnum.USER_ORDER_RECEIVE_TIME_KEY.getKey();
        System.out.println(key);
    }

}
