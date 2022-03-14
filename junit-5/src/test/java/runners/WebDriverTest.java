package runners;

import org.apiguardian.api.API;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.annotation.Testable;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@API(status = API.Status.STABLE)
@TestTemplate
@Testable
@ExtendWith(BstackRunner.class)
public @interface WebDriverTest {
}
