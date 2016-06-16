package com.tysci.ballq.modles;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/10.
 */
public class BallQMatchGuessBettingEntity implements Parcelable {
    private int id;
    private int AO_cnt;
    private int HO_cnt;
    private int UO_cnt;
    private int OO_cnt;
    private int DO_cnt;
    private int MLA_cnt;
    private int MLH_cnt;
    private String odata;
    private String otype;
    private int eid;
    private int etype;
    private int dataType;//0表示竞猜数据，1表示投注数据;

    private String bettingInfo;
    private int bettingMoney;
    private String bettingType;



    public int getAO_cnt() {
        return AO_cnt;
    }

    public void setAO_cnt(int AO_cnt) {
        this.AO_cnt = AO_cnt;
    }

    public int getDO_cnt() {
        return DO_cnt;
    }

    public void setDO_cnt(int DO_cnt) {
        this.DO_cnt = DO_cnt;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getEtype() {
        return etype;
    }

    public void setEtype(int etype) {
        this.etype = etype;
    }

    public int getHO_cnt() {
        return HO_cnt;
    }

    public void setHO_cnt(int HO_cnt) {
        this.HO_cnt = HO_cnt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMLA_cnt() {
        return MLA_cnt;
    }

    public void setMLA_cnt(int MLA_cnt) {
        this.MLA_cnt = MLA_cnt;
    }

    public int getMLH_cnt() {
        return MLH_cnt;
    }

    public void setMLH_cnt(int MLH_cnt) {
        this.MLH_cnt = MLH_cnt;
    }

    public String getOdata() {
        return odata;
    }

    public void setOdata(String odata) {
        this.odata = odata;
    }

    public int getOO_cnt() {
        return OO_cnt;
    }

    public void setOO_cnt(int OO_cnt) {
        this.OO_cnt = OO_cnt;
    }

    public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype;
    }

    public int getUO_cnt() {
        return UO_cnt;
    }

    public void setUO_cnt(int UO_cnt) {
        this.UO_cnt = UO_cnt;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getBettingInfo() {
        return bettingInfo;
    }

    public void setBettingInfo(String bettingInfo) {
        this.bettingInfo = bettingInfo;
    }

    public int getBettingMoney() {
        return bettingMoney;
    }

    public void setBettingMoney(int bettingMoney) {
        this.bettingMoney = bettingMoney;
    }

    public String getBettingType() {
        return bettingType;
    }

    public void setBettingType(String bettingType) {
        this.bettingType = bettingType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.AO_cnt);
        dest.writeInt(this.HO_cnt);
        dest.writeInt(this.UO_cnt);
        dest.writeInt(this.OO_cnt);
        dest.writeInt(this.DO_cnt);
        dest.writeInt(this.MLA_cnt);
        dest.writeInt(this.MLH_cnt);
        dest.writeString(this.odata);
        dest.writeString(this.otype);
        dest.writeInt(this.eid);
        dest.writeInt(this.etype);
        dest.writeInt(this.dataType);
        dest.writeString(this.bettingInfo);
        dest.writeInt(this.bettingMoney);
        dest.writeString(this.bettingType);
    }

    public BallQMatchGuessBettingEntity() {
    }

    private BallQMatchGuessBettingEntity(Parcel in) {
        this.id = in.readInt();
        this.AO_cnt = in.readInt();
        this.HO_cnt = in.readInt();
        this.UO_cnt = in.readInt();
        this.OO_cnt = in.readInt();
        this.DO_cnt = in.readInt();
        this.MLA_cnt = in.readInt();
        this.MLH_cnt = in.readInt();
        this.odata = in.readString();
        this.otype = in.readString();
        this.eid = in.readInt();
        this.etype = in.readInt();
        this.dataType = in.readInt();
        this.bettingInfo = in.readString();
        this.bettingMoney = in.readInt();
        this.bettingType = in.readString();
    }

    public static final Creator<BallQMatchGuessBettingEntity> CREATOR = new Creator<BallQMatchGuessBettingEntity>() {
        public BallQMatchGuessBettingEntity createFromParcel(Parcel source) {
            return new BallQMatchGuessBettingEntity(source);
        }

        public BallQMatchGuessBettingEntity[] newArray(int size) {
            return new BallQMatchGuessBettingEntity[size];
        }
    };
}
