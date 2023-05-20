package com.dev.course;

import database.LogExceptionService;
import database.entity.LogException;
import database.exception.DatabaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogExceptionServiceTest {
    private LogExceptionService service;

    @BeforeAll
    public void setup(){
        service = new LogExceptionService();
    }

    @Test
    public void given_CityAndException_When_PersistLog_Then_NewObjectIsReturned() throws DatabaseException {
        LogException entry = service.saveException("City",new RuntimeException("test"));
        assertThat(entry).isNotNull();
    }

    @Test
    public void given_CityAndNullException_When_PersistLog_Then_ExceptionIsThrown()  {
        DatabaseException exception = Assertions.assertThrows(DatabaseException.class, () -> {
            service.saveException("City",null);
        });
        Assertions.assertEquals("Error adding city log to database!", exception.getMessage()) ;
    }

    @Test
    public void given_Mock_When_PersistLog_Then_CorrectObjectIsThrown() throws DatabaseException {
        LogExceptionService mockService = Mockito.mock(LogExceptionService.class);
        DatabaseException mockException = Mockito.mock(DatabaseException.class);
        when(mockException.getMessage()).thenReturn("Dummy exception");
        when(mockService.saveException(anyString(), any(Exception.class))).thenThrow(mockException);
        DatabaseException exception = Assertions.assertThrows(DatabaseException.class, () -> {
            mockService.saveException("City", new Exception());
        });
        Assertions.assertEquals("Dummy exception", exception.getMessage()) ;
    }
}
