package com.tysci.ballq.modles;

import java.util.List;

/**
 * Created by HTT on 2016/6/10.
 * 比赛预测数据实体
 */
public class BallQMatchForecastDataEntity {
    private int id;
    private int odds_type;
    private int match_id;
    private float rate1;
    private float rate2;
    private float rate3;
    private String result_date;
    /**
     * home_name : 萨拉米斯
     * away_name : 阿普尔
     * match_date : 2016-01-13T17:00:00.000Z
     * tournament : 塞浦路斯杯
     * match_id : 84288
     * rt_odds : ["560@1.98","557@1.95","552@2.00","548@1.98","532@2.15","470@2.05","457@1.88","454@1.90","451@1.93","448@1.95","444@1.88"]
     * first_odds : 1.98
     * last_odds : 1.88
     * rate : 0.0
     * handicap : 0.75
     * handicap_records : ["560@0.75"]
     * home_score : 0
     * away_score : 0
     */

    private List<MatchForecastDataEntity> matchs;

    public void setId(int id)
    {
        this.id = id;
    }

    public void setOdds_type(int odds_type)
    {
        this.odds_type = odds_type;
    }

    public void setMatch_id(int match_id)
    {
        this.match_id = match_id;
    }

    public void setRate1(float rate1)
    {
        this.rate1 = rate1;
    }

    public void setRate2(float rate2)
    {
        this.rate2 = rate2;
    }

    public void setRate3(float rate3)
    {
        this.rate3 = rate3;
    }

    public void setResult_date(String result_date)
    {
        this.result_date = result_date;
    }

    public void setMatchs(List<MatchForecastDataEntity> matchs)
    {
        this.matchs = matchs;
    }

    public int getId()
    {
        return id;
    }

    public int getOdds_type()
    {
        return odds_type;
    }

    public int getMatch_id()
    {
        return match_id;
    }

    public float getRate1()
    {
        return rate1;
    }

    public float getRate2()
    {
        return rate2;
    }

    public float getRate3()
    {
        return rate3;
    }

    public String getResult_date()
    {
        return result_date;
    }

    public List<MatchForecastDataEntity> getMatchs()
    {
        return matchs;
    }

    public static final class MatchForecastDataEntity{
        private String home_name;
        private String away_name;
        private String match_date;
        private String tournament;
        private int match_id;
        private double first_odds;
        private double last_odds;
        private double rate;
        private double handicap;
        private int home_score;
        private int away_score;
        private List<String> rt_odds;
        private List<String> handicap_records;
        private String result;

        public void setHome_name(String home_name)
        {
            this.home_name = home_name;
        }

        public void setAway_name(String away_name)
        {
            this.away_name = away_name;
        }

        public void setMatch_date(String match_date)
        {
            this.match_date = match_date;
        }

        public void setTournament(String tournament)
        {
            this.tournament = tournament;
        }

        public void setMatch_id(int match_id)
        {
            this.match_id = match_id;
        }

        public void setFirst_odds(double first_odds)
        {
            this.first_odds = first_odds;
        }

        public void setLast_odds(double last_odds)
        {
            this.last_odds = last_odds;
        }

        public void setRate(double rate)
        {
            this.rate = rate;
        }

        public void setHandicap(double handicap)
        {
            this.handicap = handicap;
        }

        public void setHome_score(int home_score)
        {
            this.home_score = home_score;
        }

        public void setAway_score(int away_score)
        {
            this.away_score = away_score;
        }

        public void setRt_odds(List<String> rt_odds)
        {
            this.rt_odds = rt_odds;
        }

        public void setHandicap_records(List<String> handicap_records)
        {
            this.handicap_records = handicap_records;
        }

        public String getHome_name()
        {
            return home_name;
        }

        public String getAway_name()
        {
            return away_name;
        }

        public String getMatch_date()
        {
            return match_date;
        }

        public String getTournament()
        {
            return tournament;
        }

        public int getMatch_id()
        {
            return match_id;
        }

        public double getFirst_odds()
        {
            return first_odds;
        }

        public double getLast_odds()
        {
            return last_odds;
        }

        public double getRate()
        {
            return rate;
        }

        public double getHandicap()
        {
            return handicap;
        }

        public int getHome_score()
        {
            return home_score;
        }

        public int getAway_score()
        {
            return away_score;
        }

        public List<String> getRt_odds()
        {
            return rt_odds;
        }

        public List<String> getHandicap_records()
        {
            return handicap_records;
        }

        public String getResult()
        {
            return result;
        }

        public void setResult(String result)
        {
            this.result = result;
        }

    }
}
