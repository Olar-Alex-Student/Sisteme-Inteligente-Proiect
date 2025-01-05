package utils;

//utils/ChatLogger.java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChatLogger {
	private static final String LOG_DIRECTORY = "chat_logs/";
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	// Asigură existența directorului de log-uri
	static {
		new File(LOG_DIRECTORY).mkdirs();
	}

	// Salvează un mesaj în fișierul de log
	public static void logMessage(Message message) {
		String fileName = LOG_DIRECTORY + LocalDate.now().format(DATE_FORMAT) + ".log";
		try (FileWriter fw = new FileWriter(fileName, true); BufferedWriter bw = new BufferedWriter(fw)) {
			bw.write(message.toString());
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Citește istoricul mesajelor dintr-o zi specifică
	public static List<String> readHistory(LocalDate date) {
		List<String> history = new ArrayList<>();
		String fileName = LOG_DIRECTORY + date.format(DATE_FORMAT) + ".log";

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				history.add(line);
			}
		} catch (IOException e) {
			System.err.println("Nu s-a putut citi istoricul pentru data " + date);
		}

		return history;
	}

	// Salvează mesaje în bulk (pentru eficiență)
	public static void logMessages(List<Message> messages) {
		String fileName = LOG_DIRECTORY + LocalDate.now().format(DATE_FORMAT) + ".log";
		try (FileWriter fw = new FileWriter(fileName, true); BufferedWriter bw = new BufferedWriter(fw)) {
			for (Message message : messages) {
				bw.write(message.toString());
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Curăță log-urile mai vechi de n zile
	public static void cleanOldLogs(int daysToKeep) {
		File logDir = new File(LOG_DIRECTORY);
		File[] files = logDir.listFiles();
		if (files != null) {
			LocalDate cutoffDate = LocalDate.now().minusDays(daysToKeep);
			for (File file : files) {
				try {
					LocalDate fileDate = LocalDate.parse(file.getName().replace(".log", ""), DATE_FORMAT);
					if (fileDate.isBefore(cutoffDate)) {
						file.delete();
					}
				} catch (Exception e) {
					// Skip files that don't match the expected format
					System.err.println("Fișier invalid în directorul de log-uri: " + file.getName());
				}
			}
		}
	}
}
