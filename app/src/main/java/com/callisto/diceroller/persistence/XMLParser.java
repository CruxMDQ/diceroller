package com.callisto.diceroller.persistence;

import android.graphics.Color;

import com.callisto.diceroller.application.App;
import com.callisto.diceroller.persistence.objects.Stat;
import com.callisto.diceroller.tools.Constants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

import io.realm.RealmList;

public class XMLParser
{
    public static RealmList<Stat> parseStats()
    {
        XmlPullParserFactory factory;

        try
        {
            factory = XmlPullParserFactory.newInstance();

            XmlPullParser parser = factory.newPullParser();

            InputStream is = App.getRes().getAssets().open("cofdrules.xml");

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

            parser.setInput(is, null);

            return processParsing(parser);
        }
        catch (XmlPullParserException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static RealmList<Stat> processParsing(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        RealmList<Stat> stats = new RealmList<>();

        int eventType = parser.getEventType();

        Stat currentStat = null;

        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            String eltName;

            switch (eventType)
            {
                case XmlPullParser.START_TAG:
                {
                    eltName = parser.getName();

                    if (Constants.XmlTags.TAG_STAT_SINGLE.getText().equals(eltName))
                    {
                        if (currentStat != null)
                        {
                            RealmHelper.getInstance().save(currentStat);
                        }

                        currentStat = new Stat();

                        currentStat.setId(RealmHelper.getInstance().getLastId(Stat.class));

                        stats.add(currentStat);
                    }
                    else if (currentStat != null)
                    {
                        if (Constants.XmlTags.TAG_STAT_FIELD_NAME.getText().equals(eltName))
                        {
                            currentStat.setName(parser.nextText());
                        }
                        else if (Constants.XmlTags.TAG_STAT_FIELD_CATEGORY.getText()
                            .equals(eltName))
                        {
                            currentStat.setCategory(parser.nextText());
                        }
                        else if (Constants.XmlTags.TAG_STAT_FIELD_TYPE.getText().equals(eltName))
                        {
                            currentStat.setType(parser.nextText());
                        }
                        else if (Constants.XmlTags.TAG_STAT_FIELD_KIND.getText().equals(eltName))
                        {
                            currentStat.setKind(parser.nextText());
                        }
                        else if (Constants.XmlTags.TAG_STAT_FIELD_COLOR.getText().equals(eltName))
                        {
                            currentStat.setColor(Color.parseColor(parser.nextText()));
                        }
                        else if (Constants.XmlTags.TAG_STAT_FIELD_OBSERVER.getText()
                            .equals(eltName))
                        {
                            currentStat.addWatcher(parser.nextText());
                        }
                        else if (Constants.XmlTags.TAG_STAT_FIELD_OBSERVES.getText()
                            .equals(eltName))
                        {
                            currentStat.addWatchedStat(parser.nextText());
                        }
                    }
                    break;
                }
            }

            eventType = parser.next();
        }

        for (Stat stat : stats)
        {
            for (String watcher : stat.getWatchers())
            {
                for (Stat t : stats)
                {
                    if (t.getName().equals(watcher))
                    {
                        stat.addObserver(t);
                    }
                }
            }
            for (String watched : stat.getWatchedStats())
            {
                for (Stat t : stats)
                {
                    if (t.getName().equals(watched))
                    {
                        stat.addObservedStat(t);
                    }
                }
            }

            stat.getWatchers().clear();
            stat.getWatchedStats().clear();
        }

        return stats;
    }
}
