package com.example.healthcheckapp.pojo;

/**
 * Created By MMT6540 on 03 Apr, 2018
 */
public class SendEmailToHealthCheckOwnerPojo {
    private EmailContentPojo content;

    private String to;

    private String subject;

    private String from;

    private String cc;

    private String triggerName;

    public EmailContentPojo getContent ()
    {
        return content;
    }

    public void setContent (EmailContentPojo content)
    {
        this.content = content;
    }

    public String getTo ()
    {
        return to;
    }

    public void setTo (String to)
    {
        this.to = to;
    }

    public String getSubject ()
    {
        return subject;
    }

    public void setSubject (String subject)
    {
        this.subject = subject;
    }

    public String getFrom ()
    {
        return from;
    }

    public void setFrom (String from)
    {
        this.from = from;
    }

    public String getCc ()
    {
        return cc;
    }

    public void setCc (String cc)
    {
        this.cc = cc;
    }

    public String getTriggerName ()
    {
        return triggerName;
    }

    public void setTriggerName (String triggerName)
    {
        this.triggerName = triggerName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [content = "+content+", to = "+to+", subject = "+subject+", from = "+from+", cc = "+cc+", triggerName = "+triggerName+"]";
    }
}