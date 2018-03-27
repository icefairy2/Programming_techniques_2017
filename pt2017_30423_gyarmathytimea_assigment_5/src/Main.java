import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {

		String fileName = "Activities.txt";
		List<MonitoredData> list = new ArrayList<>();

		//Reading the file
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			list = stream
					// .filter(line -> !line.contains("Toileting"))
					.map(line -> {
						String[] lines = line.split("		");
						DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

						return new MonitoredData(LocalDateTime.parse(lines[0], format),
								LocalDateTime.parse(lines[1], format), lines[2]);
					}).collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Printing answers		
		Control ctrl = new Control(list);
		
		System.out.println("Number of distinct days monitored: " + ctrl.countDays());
		ctrl.actionOccurences();
		ctrl.actionOccurencesByDay();
		ctrl.activityDurations(10);
		ctrl.filterActivities(5);
		/*Predicate<Integer> greaterThanTen = x -> x > 10;
		Predicate<Integer> lessThanOrEqualToTen = greaterThanTen.negate();
		System.out.println(greaterThanTen.test(10));
		System.out.println(lessThanOrEqualToTen.test(10));*/
		// list.forEach(System.out::println);

	}

}
