package tigerhacks.android.tigerhacksapp;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleItemList {

    @SerializedName("schedule")
    @Expose
    private List<ScheduleItem> schedule = null;

    public List<ScheduleItem> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ScheduleItem> schedule) {
        this.schedule = schedule;
    }

}