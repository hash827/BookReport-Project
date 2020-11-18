package ddwucom.mobile.finalproject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;


public class NaverBookXmlParser {

    public enum TagType { NONE, TITLE, AUTHOR, IMAGE, ISBN ,PUBLISHER,DESCRIPTION,PRICE };

    final static String TAG_ITEM = "item";
    final static String TAG_TITLE = "title";
    final static String TAG_AUTHOR = "author";
    final static String TAG_IMAGE = "image";
    final static String TAG_ISBN = "isbn";
    final static String TAG_PUBLISHER = "publisher";
    final static String TAG_DESCRIPTION = "description";
    final static String TAG_PRICE = "price";

    public NaverBookXmlParser() {
    }

    public ArrayList<NaverBookDto> parse(String xml) {

        ArrayList<NaverBookDto> resultList = new ArrayList();
        NaverBookDto dto = null;

        TagType tagType = TagType.NONE;

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals(TAG_ITEM)) {
                            dto = new NaverBookDto();
                        } else if (parser.getName().equals(TAG_TITLE)) {
                            if (dto != null) tagType = TagType.TITLE;
                        } else if (parser.getName().equals(TAG_AUTHOR)) {
                            if (dto != null) tagType = TagType.AUTHOR;
                        } else if (parser.getName().equals(TAG_IMAGE)) {
                            if (dto != null) tagType = TagType.IMAGE;
                        }else if (parser.getName().equals(TAG_ISBN)){
                            if (dto != null) tagType = TagType.ISBN;
                        }
                        else if (parser.getName().equals(TAG_PUBLISHER)){
                            if (dto != null) tagType = TagType.PUBLISHER;
                        }
                        else if (parser.getName().equals(TAG_DESCRIPTION)){
                            if (dto != null) tagType = TagType.DESCRIPTION;
                        }
                        else if (parser.getName().equals(TAG_PRICE)){
                            if (dto != null) tagType = TagType.PRICE;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals(TAG_ITEM)) {
                            resultList.add(dto);
                            dto = null;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        switch(tagType) {
                            case TITLE:
                                dto.setTitle(parser.getText());
                                break;
                            case AUTHOR:
                                dto.setAuthor(parser.getText());
                                break;
                            case IMAGE:
                                dto.setImageLink(parser.getText());
                                break;
                            case PUBLISHER:
                                dto.setPublisher(parser.getText());
                                break;
                            case ISBN:
                                dto.setIbsn(parser.getText());
                                break;
                            case DESCRIPTION:
                                dto.setDescription(parser.getText());
                                break;
                            case PRICE:
                                dto.setPrice(parser.getText());
                                break;
                        }
                        tagType = TagType.NONE;
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
