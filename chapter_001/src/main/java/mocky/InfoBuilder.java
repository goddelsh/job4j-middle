package mocky;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

public class InfoBuilder {

    public String getCamerasJSON(String url) throws ExecutionException, InterruptedException {
        return new Gson().toJson(this.getAndComputeInfo(url));
    }

    public List<Map<String, String>> getAndComputeInfo(String mainUrl) throws ExecutionException, InterruptedException {
        List<Map<String, String>> result = new ArrayList<>();
        List<CompletableFuture<Void>> results = new ArrayList<>();
        List<Map<String, String>> cameras = new Gson().fromJson(HttpRequester.getStringFromRequest(mainUrl), new TypeToken<List<Map<String, String>>>() { }.getType());

        for (Map<String, String> camera : cameras) {
            CompletableFuture<Void> newCompletable = new CompletableFuture<>();
            Map<String, String> map = new ConcurrentHashMap<>();
            result.add(map);
            map.put("id", camera.get("id"));
            results.add(newCompletable.runAsync(() -> {
                Map<String, String> tempResult = this.getAndComputeDetails(camera.get("sourceDataUrl"));
                map.putAll(tempResult);
                System.out.println(camera.get("id") + " first request completed");
            }));
            results.add(newCompletable.runAsync(() -> {
                Map<String, String> tempResult = this.getAndComputeDetails(camera.get("tokenDataUrl"));
                map.putAll(tempResult);
                System.out.println(camera.get("id") + " second request completed");
            }));

        }

        CompletableFuture<Void> compAll = CompletableFuture.allOf(results.toArray(new CompletableFuture[0]));
        compAll.get();

        return result;
    }


    public Map<String, String> getAndComputeDetails(String sourceUrl) {
        Map<String, String> result = new Gson().fromJson(HttpRequester.getStringFromRequest(sourceUrl), Map.class);
        return result;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String result = new InfoBuilder().getCamerasJSON("http://www.mocky.io/v2/5c51b9dd3400003252129fb5");
        System.out.println(result);
    }

}
