package Export;

import utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Export {

		public void getDump(String databaseName) throws IOException {
				String pathString = "Database/" + databaseName;
				Path path = Path.of(pathString);
				if (Files.notExists(path)) {
						System.out.println("Database does not exist");
				} else {
						//Create dumps folder
						File theDir = new File("dumps/" + databaseName);
						if (!theDir.exists()) {
								theDir.mkdirs();
						}

						//Get all the tables in a database
						File file = new File(pathString);
						String[] fileList = file.list();

						//Create dumps
						for (String filename : fileList) {
								String[] parts = filename.split("_");
								String tableName = parts[0];
								String dumpFilePath = "dumps/" + databaseName + "/" + tableName + ".sql";

								if (filename.contains("metadata.txt")) {
										String filePath = pathString + "/" + filename;
										String query = Utils.createTableQuery(filename, filePath);
										File dumpFile = new File(dumpFilePath);
										if (!dumpFile.exists()) {
												dumpFile.createNewFile();
										} else {
												PrintWriter writer = new PrintWriter(dumpFilePath);
												writer.print("");
										}
										Utils.writeFile(
												"--\n" + "-- Table structure for table `" + tableName + "`\n" + "--",
												dumpFilePath);
										Utils.writeFile(query, dumpFilePath);

								}
						}

						for (String filename : fileList) {

								if (!filename.contains("metadata.txt")) {
										String[] parts = filename.split("\\.");
										String tableName = parts[0];
										String dumpFilePath = "dumps/" + databaseName + "/" + tableName + ".sql";
										String filePath = pathString + "/" + filename;
										Utils.writeFile(
												"--\n" + "-- Dumping data for table `" + tableName + "`\n" + "--",
												dumpFilePath);
										Utils.writeFile(
												"[SET SQL_MODE = \"NO_AUTO_VALUE_ON_ZERO\";\n" + "START TRANSACTION;\n"
														+ "SET time_zone = \"+00:00\";\n", dumpFilePath);
										String query = Utils.insertIntoTableQuery(filename, filePath);
										Utils.writeFile(query, dumpFilePath);
										Utils.writeFile("COMMIT;", dumpFilePath);

								}
						}

				}
		}
}
