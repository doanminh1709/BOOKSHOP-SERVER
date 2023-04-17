package project.spring.quanlysach.application.excel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IExcelFile {
    void writeTitle();

    void writeData();

    //HttpServletResponse : Provide methods to handle requests Http send from Client
    //and return HTTP response corresponding(tuong ung)
    void export(HttpServletResponse response) throws IOException;

}
