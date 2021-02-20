package com.example.strannik.currencyrates;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class UtilityDownload {
    private static final String TAG = "UtilityDownload";
    private static final String KEY_CHAR_CODE = "CharCode";
    private static final String KEY_VALUE = "Value";
    private static final String KEY_NOMINAL = "Nominal";
    private static final String KEY_NAME = "Name";
    // Загрузка данных с сервера по URL,
    // возвращение или ArrayList данных валют и добавки к title или исключения
    public static Result<ArrayList<Map<String, String>>> download(String url) {
        Result<ArrayList<Map<String, String>>> res = new Result<>();
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> m;
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document dom = db.parse(in);
                Element docElement = dom.getDocumentElement();
                String date = docElement.getAttribute("Date");
                res.title = " на " + date;
                NodeList nodeList = docElement.getElementsByTagName("Valute");
                if(nodeList != null && nodeList.getLength() >0) {
                    for (int i = 0; i<nodeList.getLength(); i++) {
                        Element entry = (Element) nodeList.item(i);
                        m = new HashMap<String, String>();

                        String charCode = entry.getElementsByTagName(KEY_CHAR_CODE).item(0)
                                .getFirstChild().getNodeValue();

                        String value = entry.getElementsByTagName(KEY_VALUE).item(0)
                                .getFirstChild().getNodeValue();

                        String nominal = "за " + entry.getElementsByTagName(KEY_NOMINAL).item(0)
                                .getFirstChild().getNodeValue();

                        String name = entry.getElementsByTagName(KEY_NAME).item(0)
                                .getFirstChild().getNodeValue();

                        m.put(KEY_CHAR_CODE, charCode);
                        m.put(KEY_VALUE, value);
                        m.put(KEY_NOMINAL, nominal);
                        m.put(KEY_NAME, name);
                        list.add(m);
                    }
                }
            } else {
                Log.i(TAG, "download: ResponseCode = " + responseCode);
                throw new ConnectException("wrong response");
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            res.exception = e;
        }

        res.result = list;
        return res;
    }
}
