
import org.example.App;
import org.example.utils.ApiTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static spark.Spark.awaitInitialization;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class APItest {

    @BeforeAll
    public void setUp()  {
        App.main(null);
        awaitInitialization();
    }
    @Test
    public void ApiConnectionTest(){
        String testUrl = "/";

        ApiTestUtils.TestResponse res = ApiTestUtils.request("GET", testUrl, null);
        assertEquals(200, res.status);
    }

    @Test
    public void filesUploadsRouteTest(){
        String testUrl = "/files/uploads";
        ApiTestUtils.TestResponse res = ApiTestUtils.request("GET", testUrl, null);
        assertEquals(200, res.status);
    }

    @Test
    public void getRouteTest(){
        String testUrl = "/animals";
        ApiTestUtils.TestResponse res = ApiTestUtils.request("GET", testUrl, null);
        assertEquals(200, res.status);
    }





}
