package de.stormfox2.core.de.stormfox2.chat;

public class ChatArg
{

    private String replace;
    private String value;

    public ChatArg(String replace, String value){

        this.replace = replace;
        this.value = value;
    }

    public String getReplace()
    {
        return replace;
    }

    public String getValue()
    {
        return value;
    }
}