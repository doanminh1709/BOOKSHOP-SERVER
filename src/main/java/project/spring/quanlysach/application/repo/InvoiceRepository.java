package project.spring.quanlysach.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.spring.quanlysach.domain.entity.Invoice;
import project.spring.quanlysach.domain.entity.Tag;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findInvoiceBySupplierId(int supplierId);


    // Recomment
    //Use 'SOUNDEX'

    @Query("SELECT u FROM Invoice u WHERE u.status LIKE :x")
    List<Invoice> searchInvoiceByStatus(@Param("x") String status);

}
