package com.bolnizar.itfest.persistance;

import com.orm.SugarRecord;

/**
 * Created by Musafir on 11/26/2016.
 */
public class SubscriptionRecord extends SugarRecord {

    public int userId;
    public int classId;
    public int subId;

    public SubscriptionRecord(int userId, int classId, int subId) {
        this.userId = userId;
        this.classId = classId;
        this.subId = subId;
    }

    public SubscriptionRecord() {
    }
}
