package net.masterthought.cucumber;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.masterthought.cucumber.json.Feature;
import net.masterthought.cucumber.util.Util;

import com.google.gson.Gson;

public class ReportParser {

	private TreeMap<String, List<Feature>> jsonReportFiles;

	public ReportParser(List<String> jsonReportFiles) throws IOException {
		this.jsonReportFiles = parseJsonResults(jsonReportFiles);
	}

	public Map<String, List<Feature>> getFeatures() {
		return jsonReportFiles;
	}

	private TreeMap<String, List<Feature>> parseJsonResults(
			List<String> jsonReportFiles) throws IOException {
		TreeMap<String, List<Feature>> featureResults = new TreeMap<String, List<Feature>>();
		for (String jsonFile : jsonReportFiles) {
			String fileContent = Util.readFileAsString(jsonFile);
			if (Util.isValidCucumberJsonReport(fileContent)) {
				Feature[] features = new Gson().fromJson(Util.U2U(fileContent),
						Feature[].class);
				featureResults.put(jsonFile, Arrays.asList(features));
			}
		}
		return featureResults;
	}

}
