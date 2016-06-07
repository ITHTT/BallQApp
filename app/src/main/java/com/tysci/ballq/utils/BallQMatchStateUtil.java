package com.tysci.ballq.utils;

/**
 * Created by HTT on 2016/6/5.
 */
public class BallQMatchStateUtil {
    private static String getBasketBallMatchState(int state) {
        switch (state) {
            case 0:
                return "未开始";
            case 1:
                return "进行中";
            case 10:
                return "第一节";
            case 20:
                return "第二节";
            case 30:
                return "第三节";
            case 40:
                return "第四节";
            case 50:
                return "加时";
            case 60:
                return "取消";
            case 70:
                return "延期";
            case 100:
                return "结束";
            default:
                return "";
        }
    }

    private static String getFootBallMatchState(int status) {
        switch (status) {
            case 0:
                return "未开始";
            case 1:
                return "第一节";
            case 2:
                return "第二节";
            case 3:
                return "第三节";
            case 4:
                return "第四节";
            case 5:
                return "第五节";
            case 6:
                return "上半场";
            case 7:
                return "下半场";
            case 8:
                return "1st set";
            case 9:
                return "2nd set";
            case 10:
                return "3rd set";
            case 11:
                return "4th set";
            case 12:
                return "5th set";
            case 13:
                return "1st quarter";
            case 14:
                return "3nd quarter";
            case 15:
                return "3rd quarter";
            case 16:
                return "4th quarter";
            case 20:
                return "Started";
            case 30:
                return "Pause";
            case 31:
                return "中场";
            case 32:
                return "等待加时赛";
            case 33:
                return "加时赛中场";
            case 34:
                return "等待点球决胜";
            case 40:
                return "加时";
            case 41:
                return "加时第一节";
            case 42:
                return "加时第二节";
            case 50:
                return "点球";
            case 60:
                return "延期";
            case 61:
                return "延迟开赛";
            case 70:
                return "取消";
            case 80:
                return "中断";
            case 90:
                return "弃赛";
            case 91:
                return "Walkover";
            case 92:
                return "Retired";
            case 100:
                return "完场";
            case 110:
                return "加时赛结束";
            case 120:
                return "点球决胜后";
            case 301:
                return "First break";
            case 302:
                return "Second break";
            case 303:
                return "Third break";
            default:
                return "";
        }
    }

    public static String getMatchState(int status, int etype){
        return etype == 0 ? getFootBallMatchState(status) : getBasketBallMatchState(status);
    }
}
