import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class MonitoredData {
	
	LocalDateTime startTime;
	LocalDateTime endTime;
	String activityLabel;
	
	@Override
	public String toString() {
		return "MonitoredData [startTime=" + startTime + ", endTime=" + endTime + ", activityLabel=" + activityLabel
				+ "]";
	}
	
	public MonitoredData(LocalDateTime startTime, LocalDateTime endTime, String activityLabel) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.activityLabel = activityLabel;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public LocalDate getStartDate() {
		return startTime.toLocalDate();
	}
	
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public String getActivityLabel() {
		return activityLabel;
	}
	public void setActivityLabel(String activityLabel) {
		this.activityLabel = activityLabel;
	}
	
	public long getDuration(){
		return ChronoUnit.SECONDS.between(startTime, endTime);
	}

}
