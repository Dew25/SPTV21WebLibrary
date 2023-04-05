

package session;

import entity.Book;
import entity.History;
import entity.Reader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class HistoryFacade extends AbstractFacade<History> {

    @PersistenceContext(unitName = "SPTV21WebLibraryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoryFacade() {
        super(History.class);
    }

    public List<History> getHistoriesWithTakedBooks() {
        try {
            return em.createQuery("SELECT h FROM History h WHERE h.returnBook = null")
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<Book> getReadingBook(Reader reader) {
        try {
            return em.createQuery("SELECT h.book FROM History h WHERE h.returnBook = null AND h.reader = :reader")
                    .setParameter("reader", reader)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<History> getListHistory(String selectYear, String selectMonth, String selectDay) {
        if(selectYear == null || selectYear.isEmpty()) return new ArrayList<>();
        if((selectDay == null || selectDay.isEmpty()) && (selectMonth == null || selectMonth.isEmpty()) && selectYear != null && !selectYear.isEmpty()){
            LocalDateTime startOfYear = LocalDateTime.of(Integer.parseInt(selectYear), 1, 1, 0, 0, 0);
            LocalDateTime startOfNextYear = startOfYear.plusYears(1);
            Date beginDate = Date.from(startOfYear.atZone(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(startOfNextYear.atZone(ZoneId.systemDefault()).toInstant());
            try {
                return em.createQuery("SELECT h FROM History h WHERE h.takeOnBook > :beginDate AND h.takeOnBook < :endDate")
                        .setParameter("beginDate", beginDate)
                        .setParameter("endDate", endDate)
                        .getResultList();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        }else if((selectDay == null || selectDay.isEmpty()) && (selectMonth != null && !selectMonth.isEmpty()) && (selectYear != null && !selectYear.isEmpty())){
            LocalDateTime startOfMonth = LocalDateTime.of(Integer.parseInt(selectYear), Integer.parseInt(selectMonth), 1, 0, 0, 0);
            LocalDateTime startOfNextMonth = startOfMonth.plusMonths(1);
            Date beginDate = Date.from(startOfMonth.atZone(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(startOfNextMonth.atZone(ZoneId.systemDefault()).toInstant());
            try {
                return em.createQuery("SELECT h FROM History h WHERE h.takeOnBook > :beginDate AND h.takeOnBook < :endDate")
                        .setParameter("beginDate", beginDate)
                        .setParameter("endDate", endDate)
                        .getResultList();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        }else if((selectDay != null && !selectDay.isEmpty()) && (selectMonth != null && !selectMonth.isEmpty()) && (selectYear != null && !selectYear.isEmpty())){
            LocalDateTime startOfDay = LocalDateTime.of(Integer.parseInt(selectYear), Integer.parseInt(selectMonth), Integer.parseInt(selectDay), 0, 0, 0);
            LocalDateTime startOfNextDay = startOfDay.plusDays(1);
            Date beginDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(startOfNextDay.atZone(ZoneId.systemDefault()).toInstant());
            try {
                return em.createQuery("SELECT h FROM History h WHERE h.takeOnBook > :beginDate AND h.takeOnBook < :endDate")
                        .setParameter("beginDate", beginDate)
                        .setParameter("endDate", endDate)
                        .getResultList();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        }else{
            return new ArrayList<>();
        }
    }

}
