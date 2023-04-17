package project.spring.quanlysach.application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.spring.quanlysach.domain.entity.Brand;
import project.spring.quanlysach.domain.entity.Contact;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
        List<Contact> findContactByCustomerId(int customerId);

//        @Query("SELECT u FROM Contact u WHERE u.city LIKE :city")
        //SOUNDEX used convert a word to number code base on sound's word.
        //The word has as number code as same probably the same word
        @Query("SELECT u FROM Contact  u WHERE SOUNDEX(u.city) = SOUNDEX(:city)")
        List<Contact> findContactByCity(@Param("city") String city);

//        @Query("DELETE FROM Contact u WHERE u.id =:  ")
//        void deleteContact(@Param("contactId") int id);
}
