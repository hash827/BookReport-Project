package ddwucom.mobile.finalproject;

import android.text.Html;
import android.text.Spanned;

public class NaverBookDto {

    private int _id;
    private String title;
    private String author;
    private String link;
    private String imageLink;
    private String publisher;
    private String ibsn;
    private String description;
    private String price;
    private String imageFileName;       // 외부저장소에 저장했을 때의 파일명


    public int get_id() {
        return _id;
    }

    public String getPublisher() {
        Spanned spanned = Html.fromHtml(publisher);
        return spanned.toString();
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIbsn() {
        return ibsn;
    }

    public void setIbsn(String ibsn) {
        this.ibsn = ibsn;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAuthor() {
        Spanned spanned = Html.fromHtml(author);
        return spanned.toString();
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getTitle() {
        //xml을 보면 중간중간에 <b>주식</b>처럼 태그가 들어가 있어서 그걸 string으로 바꿔주는 함수
        Spanned spanned = Html.fromHtml(title);
        return spanned.toString();
//        return title;
    }

    public String getDescription() {
        Spanned spanned = Html.fromHtml(description);
        return spanned.toString();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return  _id + ": " + title + " (" + author + ')';
    }
}
