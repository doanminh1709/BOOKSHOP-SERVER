package project.spring.quanlysach.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.spring.quanlysach.domain.entity.InvoiceDetail;

import java.util.List;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Integer> {

    InvoiceDetail findInvoiceDetailByProductIdAndInvoiceId(int productId, int invoiceId);
    //@Query("SELECT u FROM InvoiceDetail  u WHERE u.product.id =: productId AND u.invoice.id =: invoiceId")
    //InvoiceDetail findInvoiceDetailByProductIdAndInvoiceId(@Param("productId") int productId, @Param("invoiceId") int invoiceId);

    void deleteInvoiceDetailByProductIdAndInvoiceId(int productId, int invoiceId);

    //@Query("DELETE FROM InvoiceDetail  u WHERE u.product.id = :productId AND u.invoice.id = :invoiceId")
    //void deleteInvoiceDetail(@Param("productId") int productId, @Param("invoiceId") int invoiceId);

    @Query("SELECT u FROM InvoiceDetail  u WHERE u.productName LIKE :productName")
    List<InvoiceDetail> searchByName(@Param("productName") String productName);

//    @Query("SELECT u FROM InvoiceDetail u ORDER BY u.quantity DESC LIMIT 3")
//    List<InvoiceDetail> show3Product();
}
