package vn.asiantech.core;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReportListener extends TestListenerAdapter {
    private long totalScenarios = 0;
    private long totalSteps = 0;
    private long totalFeatures = 0;
    private long totalTime = 0;

    private long featurePassed = 0;
    private long stepPassed = 0;
    private long scenarioPassed = 0;

    private long scenarioFailed = 0;
    private long featureFailed = 0;
    private long stepFailed = 0;

    private long stepSkipped = 0;
    private long stepPending = 0;
    private long stepUndefined = 0;

    private File[] files;

    @Override
    public final void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
        try {
            mergeJsonFiles();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void mergeJsonFiles() throws IOException, ParseException {
        int size;
        files = getAllFiles();
        List<String> resultIds = new ArrayList<>();
        JSONArray resultFeatures;
        if (files.length > 0) {
            resultFeatures = getFeatures(new FileReader(files[0].getPath()));
            size = resultFeatures.size();
            for (int i = 0; i < size; i++) {
                resultIds.add((String) ((JSONObject) resultFeatures.get(i)).get("id"));
            }
        } else {
            return;
        }

        for (int pos = 1; pos < files.length; pos++) {
            JSONArray fileFeatures = getFeatures(new FileReader(files[pos].getPath()));
            for (int i = 0; i < size; i++) {
                for (Object fileFeature : fileFeatures) {
                    JSONObject mergeObject = (JSONObject) resultFeatures.get(i);
                    JSONObject object = (JSONObject) fileFeature;
                    String idMergeFeature = (String) mergeObject.get("id");
                    String idFeature = (String) object.get("id");
                    if (idMergeFeature.equals(idFeature)) {
                        JSONArray mainElements = (JSONArray) mergeObject.get("elements");
                        JSONArray elements = (JSONArray) object.get("elements");
                        mainElements.addAll(elements);
                    } else if (!resultIds.contains(idFeature)) {
                        resultIds.add(idFeature);
                        resultFeatures.add(fileFeature);
                    }
                }
            }
        }
        Files.write(Paths.get(files[0].getPath()), resultFeatures.toJSONString().getBytes());
        deleteFiles();

        totalFeatures = resultFeatures.size();
        System.out.println("Sum of features: " + totalFeatures);
        for (Object resultFeature : resultFeatures) {
            JSONObject feature = (JSONObject) resultFeature;
            JSONArray elements = (JSONArray) feature.get("elements");
            for (Object element : elements) {
                JSONObject elementObject = (JSONObject) element;
                if (elementObject.get("type").equals("scenario")) {
                    JSONArray steps = (JSONArray) elementObject.get("steps");
                    boolean isPassed = true;
                    for (Object step : steps) {
                        JSONObject stepObject = (JSONObject) step;
                        JSONObject resultObject = (JSONObject) stepObject.get("result");
                        String status = (String) resultObject.get("status");
                        if (!status.equals("passed")) {
                            isPassed = false;
                            break;
                        }
                    }
                    if (isPassed) {
                        scenarioPassed++;
                    } else {
                        scenarioFailed++;
                    }
                }

                if (elementObject.get("type").equals("scenario")) {
                    totalScenarios++;
                }

                JSONArray steps = (JSONArray) elementObject.get("steps");
                totalSteps += steps.size();
                for (Object step : steps) {
                    JSONObject stepObject = (JSONObject) step;
                    JSONObject resultObject = (JSONObject) stepObject.get("result");
                    String status = (String) resultObject.get("status");
                    long duration = (long) resultObject.get("duration");
                    totalTime += duration;
                    switch (status) {
                        case "failed":
                            stepFailed++;
                            break;
                        case "skipped":
                            stepSkipped++;
                            break;
                        case "pending":
                            stepPending++;
                            break;
                        case "undefined":
                            stepUndefined++;
                            break;
                        default:
                            stepPassed++;
                            break;
                    }
                }
            }
        }

        for (Object resultFeature : resultFeatures) {
            boolean isFeaturePassed = true;
            JSONObject feature = (JSONObject) resultFeature;
            JSONArray elements = (JSONArray) feature.get("elements");
            for (Object element : elements) {
                JSONObject elementObject = (JSONObject) element;
                if (elementObject.get("type").equals("scenario")) {
                    JSONArray steps = (JSONArray) elementObject.get("steps");
                    boolean isPassed = true;
                    for (Object step : steps) {
                        JSONObject stepObject = (JSONObject) step;
                        JSONObject resultObject = (JSONObject) stepObject.get("result");
                        String status = (String) resultObject.get("status");
                        if (!status.equals("passed")) {
                            isPassed = false;
                            break;
                        }
                    }
                    if (!isPassed) {
                        isFeaturePassed = false;
                        break;
                    }
                }
            }
            if (isFeaturePassed) {
                featurePassed++;
            } else {
                featureFailed++;
            }
        }

        System.out.println("Sum of scenarios: " + totalScenarios);
        System.out.println("Sum of steps: " + totalSteps);
        System.out.println("Sum of step passed: " + stepPassed);
        System.out.println("Sum of step failed: " + stepFailed);
        System.out.println("Sum of scenario passed: " + scenarioPassed);
        System.out.println("Sum of scenario failed: " + scenarioFailed);
        System.out.println("Sum of feature passed: " + featurePassed);
        System.out.println("Sum of feature failed: " + scenarioFailed);
        System.out.println("Sum of duration: " + getTime(totalTime));
        createGitReport();
    }

    private JSONArray getFeatures(final FileReader file) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object object = parser.parse(file);
        return (JSONArray) object;
    }

    private String getTime(long microSecond) {
        long miliSecond = microSecond / 1000000;
        return DurationFormatUtils.formatDuration(miliSecond, "HH:mm:ss,SSS");
    }

    private File[] getAllFiles() {
        File dir = new File("target/cucumber-reports/");
        return dir.listFiles((dir1, name) -> name.endsWith(".json"));
    }

    private void deleteFiles() {
        for (int i = 1; i < files.length; i++) {
            files[i].delete();
        }
    }

    private void createGitReport() throws IOException {
        JSONObject step = new JSONObject();
        step.put("totalSteps", totalSteps);
        step.put("stepPassed", stepPassed);
        step.put("stepFailed", stepFailed);
        step.put("stepSkipped", stepSkipped);
        step.put("stepPending", stepPending);
        step.put("stepUndefined", stepUndefined);

        JSONObject scenario = new JSONObject();
        scenario.put("totalScenarios", totalScenarios);
        scenario.put("scenarioPassed", scenarioPassed);
        scenario.put("scenarioFailed", scenarioFailed);

        JSONObject feature = new JSONObject();
        feature.put("totalFeatures", totalFeatures);
        feature.put("featurePassed", featurePassed);
        feature.put("featureFailed", featureFailed);

        JSONObject duration = new JSONObject();
        duration.put("totalDuration", getTime(totalTime));

        JSONObject report = new JSONObject();
        report.put("features", feature);
        report.put("scenarios", scenario);
        report.put("steps", step);
        report.put("durations", duration);
        Files.write(Paths.get("target/GitHubReport.json"), report.toJSONString().getBytes());
    }
}
