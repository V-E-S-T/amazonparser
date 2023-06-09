package com.amazonparser.Util;

import com.amazonparser.exceptions.NotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ParserUtil {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
    public static Document getHTMLDocument(String reference){
        try {
            return Jsoup.connect(reference)
                    .userAgent(USER_AGENT)
                    .get();
        } catch (IOException e) {
//            throw new NotFoundException("Can not connect to this reference: " + reference);
            throw new NotFoundException();
        }
    }
}
