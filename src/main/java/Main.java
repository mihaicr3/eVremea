import Threads.ThreadUpdateCacheCall;
import controller.ApiController;
import database.LogExceptionService;
import database.entity.LogException;
import database.exception.DatabaseException;
import org.junit.jupiter.api.Assertions;

public class Main {
    public static void main(String[] args) {
        LogExceptionService service=new LogExceptionService();


        ApiController apiController=new ApiController();

        apiController.startSparkHttpServer();

        ThreadUpdateCacheCall threadUpdateCacheCall=new ThreadUpdateCacheCall();
        threadUpdateCacheCall.start();

        DatabaseException exception = Assertions.assertThrows(DatabaseException.class, () -> {
            service.saveException("City",null);
        });
        Assertions.assertEquals("Error adding city log to database!", exception.getMessage()) ;

    }
}