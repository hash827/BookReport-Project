package ddwucom.mobile.finalproject;

import java.io.Serializable;

public class MyReport implements Serializable {

    String isbn;
    String date;
    String page;
    String reportContent;
    long bookId;
    long _id;

    public MyReport(long _id,long bookId,String isbn, String date, String page, String reportContent) {
        this._id = _id;
        this.bookId = bookId;
        this.isbn = isbn;
        this.date = date;
        this.page = page;
        this.reportContent = reportContent;
    }

    public MyReport(long bookId,String isbn, String date, String page, String reportContent) {
        this.isbn = isbn;
        this.bookId = bookId;
        this.date = date;
        this.page = page;
        this.reportContent = reportContent;
    }
    public MyReport(long _id,String date, String page, String reportContent) {
        this._id = _id;
        this.date = date;
        this.page = page;
        this.reportContent = reportContent;
    }


    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }
}
