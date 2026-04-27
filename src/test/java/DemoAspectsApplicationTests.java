import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.itis.semestr.aspect.service.MyService;
import ru.itis.semestr.config.TestConfig;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(classes = TestConfig.class)
public class DemoAspectsApplicationTests {

    @Autowired
    private MyService service;

    @Test
    public void testLoggable() {
        List<String> list = new ArrayList<>();
        list.add("test");
        list.add("test2");

        service.method1(list);
        service.method2();
        assertTrue(service.check());
    }

}
