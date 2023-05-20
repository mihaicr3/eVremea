package database;

import jakarta.persistence.PersistenceException;
import database.entity.LogException;
import database.exception.DatabaseException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LogExceptionService {

    public Session newDatabaseSession(){
        return HibernateUtil.getSessionFactory().openSession();
    }

    public LogException saveException(String city, Throwable throwable) throws DatabaseException {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            LogException log = new LogException(city, throwable);
            log = session.merge(log);
            transaction.commit();
            return log;
        }catch (Exception e) {
            try {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
            }catch (IllegalStateException | PersistenceException exc){
                System.out.println("rollback exception");
            }
            throw new DatabaseException("Error adding city log to database!", e);
        }
    }

    public List<LogException> fetchLogs() throws DatabaseException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from LogException", LogException.class).list();
        } catch (Exception e) {
            throw new DatabaseException("Error fetch all logs from database!", e);
        }
    }
}
