package it.algos.vaadflow14.wiki;

import java.time.*;
import java.time.format.*;

/**
 * Project vaadflow14
 * Created by Algos
 * User: gac
 * Date: dom, 20-giu-2021
 * Time: 07:19
 */
public class WrapPage {

    private long pageid;

    private String title;

    private String text;

    private String domain;

    private LocalDateTime time;

    public WrapPage(final String domain, final long pageid, final String title, final String text, final String stringTimestamp) {
        this.domain = domain;
        this.pageid = pageid;
        this.title = title;
        this.text = text;
        this.time = LocalDateTime.parse(stringTimestamp, DateTimeFormatter.ISO_DATE_TIME);
    }

    public String getDomain() {
        return domain;
    }

    public long getPageid() {
        return pageid;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getTime() {
        return time;
    }

}
