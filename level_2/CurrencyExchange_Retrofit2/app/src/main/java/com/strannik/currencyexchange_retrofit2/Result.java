package com.strannik.currencyexchange_retrofit2;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="ValCurs", strict = false)
public class Result {
    @Attribute(name = "Date")
    private String Date;

    @ElementList(name = "Valute", inline = true)
    private List<Valute> valuteList;

    public String getDate ()
    {
        return Date;
    }

    public void setDate (String Date)
    {
        this.Date = Date;
    }

    public List<Valute> getValuteList ()
    {
        return valuteList;
    }

    public void setValuteList (List<Valute> valuteList)
    {
        this.valuteList = valuteList;
    }
}
