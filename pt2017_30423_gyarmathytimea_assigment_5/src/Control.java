import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Control {
	private List<MonitoredData> list;

	public Control(List<MonitoredData> list) {
		super();
		this.list = list;
	}

	public List<MonitoredData> getList() {
		return list;
	}

	public void setList(List<MonitoredData> list) {
		this.list = list;
	}

	public int countDays() {
		List<LocalDate> uniques = list.stream().map(line -> line.getStartTime().toLocalDate()).distinct()
				.collect(Collectors.toList());

		return uniques.size();
	}

	public Map<String, Integer> actionOccurences() {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Map<String, Integer> myMap = (Map) list.stream()
				.collect(Collectors.groupingBy(MonitoredData::getActivityLabel, Collectors.counting()));
		Path myPath = Paths.get("actionOccurences.txt");
		try {
			Files.write(myPath, () -> myMap.entrySet().stream().<CharSequence>map(e -> e.getKey() + ": " + e.getValue())
					.iterator());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myMap;
	}

	public void actionOccurencesByDay() {

		Map<LocalDate, List<MonitoredData>> myMonitoredMap = (Map<LocalDate, List<MonitoredData>>) list.stream()
				.collect(Collectors.groupingBy(MonitoredData::getStartDate));

		Map<LocalDate, Map<String, Integer>> myFinalMap = myMonitoredMap.entrySet().stream().collect(
				Collectors.toMap(Map.Entry::getKey, entry -> (new Control(entry.getValue()).actionOccurences())));

		Path myPath = Paths.get("actionOccurencesByDay.txt");
		try {
			Files.write(myPath, () -> myFinalMap.entrySet().stream()
					.<CharSequence>map(e -> e.getKey() + ": " + e.getValue()).iterator());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void activityDurations(int h) {
		long hours = h * 60 * 60;
		Map<String, Long> myMap = list.stream().collect(Collectors.groupingBy(MonitoredData::getActivityLabel,
				Collectors.summingLong(MonitoredData::getDuration)));

		Path myPath = Paths.get("activityDurations.txt");
		try {
			Files.write(myPath, () -> myMap.entrySet().stream().filter(t -> t.getValue() > hours)
					.<CharSequence>map(e -> e.getKey() + ": " + e.getValue()).iterator());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void filterActivities(int m) {
		long min = m * 60;
		Map<String, Long> allOccurences = list.stream()
				.collect(Collectors.groupingBy(MonitoredData::getActivityLabel, Collectors.counting()));
		Map<String, Long> occurencesLessThan = list.stream().filter(t -> t.getDuration() <= min)
				.collect(Collectors.groupingBy(MonitoredData::getActivityLabel, Collectors.counting()));

		List<String> myList = occurencesLessThan.entrySet().stream().map(entry -> entry.getKey())
				.filter(b -> occurencesLessThan.get(b) * 100 / allOccurences.get(b) >= 90).collect(Collectors.toList());

		Path myPath = Paths.get("filterActivities.txt");
		try {
			Files.write(myPath, () -> myList.stream().<CharSequence>map(e -> e).iterator());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
