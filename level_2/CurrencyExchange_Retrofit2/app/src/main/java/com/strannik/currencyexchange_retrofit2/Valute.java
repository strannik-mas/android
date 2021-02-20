package com.strannik.currencyexchange_retrofit2;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Valute", strict = false)
public class Valute {
    @Element(name = "CharCode")
    private String CharCode;

    @Element(name = "Value")
    private String Value;

    @Element(name = "Nominal")
    private String Nominal;

    @Element(name = "Name")
    private String Name;

    public String getCharCode ()
    {
        return CharCode;
    }

    public void setCharCode (String CharCode)
    {
        this.CharCode = CharCode;
    }

    public String getValue ()
    {
        return Value;
    }

    public void setValue (String Value)
    {
        this.Value = Value;
    }

    public String getNominal ()
    {
        return Nominal;
    }

    public void setNominal (String Nominal)
    {
        this.Nominal = Nominal;
    }

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }
}
