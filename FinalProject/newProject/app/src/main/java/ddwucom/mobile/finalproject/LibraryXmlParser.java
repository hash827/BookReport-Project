package ddwucom.mobile.finalproject;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;


public class LibraryXmlParser {

    public enum TagType { NONE, LIBCODE, LIBNAME, ADDRESS, TEL ,HOMEPAGE,CLOSED,OPERATINGTIME,XCNTS,YDNTS };

    final static String TAG_ITEM = "row";
    final static String TAG_LIBCODE = "LBRRY_SEQ_NO";
    final static String TAG_LIBNAME = "LBRRY_NAME";
    final static String TAG_ADDRESS = "ADRES";
    final static String TAG_TEL = "TEL_NO";
    final static String TAG_HOMEPAGE = "HMPG_URL";
    final static String TAG_CLOSE = "FDRM_CLOSE_DATE";
    final static String TAG_OPERATING = "OP_TIME";
    final static String TAG_XCNTS = "XCNTS";
    final static String TAG_YDNTS = "YDNTS";

    public LibraryXmlParser() {
    }

    public ArrayList<LibraryDto> parse(String xml) {

        ArrayList<LibraryDto> resultList = new ArrayList();
        LibraryDto dto = null;

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
                            dto = new LibraryDto();
                        } else if (parser.getName().equals(TAG_LIBCODE)) {
                            if (dto != null) tagType = TagType.LIBCODE;
                        } else if (parser.getName().equals(TAG_LIBNAME)) {
                            if (dto != null) tagType = TagType.LIBNAME;
                        } else if (parser.getName().equals(TAG_ADDRESS)) {
                            if (dto != null) tagType = TagType.ADDRESS;
                        }else if (parser.getName().equals(TAG_TEL)){
                            if (dto != null) tagType = TagType.TEL;
                        }
                        else if (parser.getName().equals(TAG_HOMEPAGE)){
                            if (dto != null) tagType = TagType.HOMEPAGE;
                        }
                        else if (parser.getName().equals(TAG_CLOSE)){
                            if (dto != null) tagType = TagType.CLOSED;
                        }
                        else if (parser.getName().equals(TAG_OPERATING)){
                            if (dto != null) tagType = TagType.OPERATINGTIME;
                        }
                        else if (parser.getName().equals(TAG_XCNTS)){
                            if (dto != null) tagType = TagType.XCNTS;
                        }
                        else if (parser.getName().equals(TAG_YDNTS)){
                            if (dto != null) tagType = TagType.YDNTS;
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
                            case LIBCODE:
                                dto.setLibCode(parser.getText());
                                break;
                            case LIBNAME:
                                dto.setLibName(parser.getText());
                                break;
                            case ADDRESS:
                                dto.setAddress(parser.getText());
                                break;
                            case TEL:
                                dto.setTel(parser.getText());
                                break;
                            case HOMEPAGE:
                                dto.setHomepage(parser.getText());
                                break;
                            case CLOSED:
                                dto.setClosed(parser.getText());
                                break;
                            case OPERATINGTIME:
                                dto.setOperatingTime(parser.getText());
                                break;
                            case XCNTS:
                                dto.setXcnts(parser.getText());
                                break;
                            case YDNTS:
                                dto.setYdnts(parser.getText());
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
