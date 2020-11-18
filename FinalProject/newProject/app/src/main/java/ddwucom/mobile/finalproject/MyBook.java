package ddwucom.mobile.finalproject;

import android.text.Html;
import android.text.Spanned;

import java.io.Serializable;

public class MyBook implements Serializable {
    long _id;
    String title;
    String author;
    String isbn;
    String publisher;
    String image;
    String content;
    String startDate;
    String endDate;
    String price;
    String description;

    public MyBook() {

    }

    public MyBook(String title, String author, String isbn, String publisher,
                  String image, String content, String startDate, String endDate,
                  String rating, String bookState) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.image = image;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rating = rating;
        this.bookState = bookState;
    }

    public MyBook(String title,String image) {
        this.image = image;
        this.title = title;
    }

    public MyBook(String title, String author, String isbn, String publisher, String image, String price, String description) {

        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.image = image;
        this.price = price;
        this.description = description;

    }

    public MyBook(long _id, String title, String author, String isbn, String publisher,
                  String image, String content, String startDate, String endDate,
                  String rating, String bookState) {
        this._id = _id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.image = image;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rating = rating;
        this.bookState = bookState;
    }
    public MyBook(long _id, String isbn, String content, String startDate, String endDate, String rating, String bookState) {
        this._id = _id;
        this.content = content;
        this.startDate = startDate;
        this.isbn = isbn;
        this.endDate = endDate;
        this.rating = rating;
        this.bookState = bookState;
    }

    public MyBook(String title, String author, String isbn, String publisher, String image){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.image = image;
    }
    public MyBook(long _id, String title, String author, String isbn, String publisher, String image){
        this._id = _id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.image = image;
    }



    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        Spanned spanned = Html.fromHtml(description);
        return spanned.toString();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        Spanned spanned = Html.fromHtml(title);
        return spanned.toString();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        Spanned spanned = Html.fromHtml(author);
        return spanned.toString();
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        Spanned spanned = Html.fromHtml(publisher);
        return spanned.toString();
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getBookState() {
        return bookState;
    }

    public void setBookState(String bookState) {
        this.bookState = bookState;
    }

    String rating;
    String bookState;


}
