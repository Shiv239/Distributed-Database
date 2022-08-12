package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

	public static void writeFile(String data, String fileName) throws IOException {
		try {
			FileWriter file = new FileWriter(fileName, true);
			file.write(data);
			file.append("\n");
			file.flush();
			file.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public static boolean ReadFile(String user) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("UserInformation.txt"));
		boolean result = false;
		try {
			String line = br.readLine();
			while (line != null) {
				String id = line.split(",")[0];
				if (id.equals(user)) {
					result = true;
					break;
				}
				line = br.readLine();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public static List<String> ReadFullFile(String fileName) throws IOException {
		List<String> lines = new ArrayList<>();

		try {
			File file = new File(fileName);
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				lines.add(sc.nextLine());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;

	}

	public static String createTableQuery(String fileName, String filePath) throws IOException {
		String[] parts = fileName.split("_");
		String tableName = parts[0];
		String query = "DROP TABLE IF EXISTS `" + tableName + "`;" + "\n\n";

		query += "CREATE TABLE `" + tableName + "` ( \n";
		List<String> metaData = ReadFullFile(filePath);

		for (String columnData : metaData) {
			String[] rowData = columnData.split("\\|");
			String keyInfo = "";
			String referencesInfo = "";
			String nullInfo = "NULL";
			if (columnData.toLowerCase().contains("Primary".toLowerCase())) {
				nullInfo = "NOT NULL";
			}
			if (rowData.length > 2) {
				keyInfo = rowData[2] + " KEY";
				if (rowData.length > 3) {
					referencesInfo = rowData[3];
				}
			}
			query += rowData[0] + " " + rowData[1] + " " + nullInfo + " " + keyInfo + " " + referencesInfo;
			if (columnData != metaData.get(metaData.size() - 1)) {
				query += ",";
			}
			query += "\n";
		}
		query += ");";
		return tableName;
	}

//    public static boolean ReadFile(String user) throws IOException {
//        BufferedReader br = new BufferedReader(new FileReader("UserInformation.txt"));
//        boolean result = false;
//        try {
//            String line = br.readLine();
//            while (line != null) {
//                String id = line.split("||")[0];
//                if (id.equals(user)) {
//                    result = true;
//                    break;
//                }
//                line = br.readLine();
//            }
//				return query;
//		}
//	}

		public static String insertIntoTableQuery(String fileName, String filePath) throws IOException {
				String[] parts = fileName.split("\\.");
				String tableName = parts[0];
				String query = "INSERT INTO `" + tableName + "` VALUES";

				List<String> rowData = ReadFullFile(filePath);

				for (String columnData : rowData) {
						if(columnData!=rowData.get(0)) {
								String[] cellData = columnData.split("\\|");
								query += "(";
								for (int i = 0; i < cellData.length; i++) {
										System.out.println("cellData[i]  " + cellData[i]);
										query += cellData[i];
										if (i != cellData.length - 1) {
												query += ",";
										}
								}
								query += ")";
								if (columnData != rowData.get(rowData.size() - 1)) {
										query += ",";
								} else {
										query += ";";
								}
						}
				}

				return query;
		}

}
