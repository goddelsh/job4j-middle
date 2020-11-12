package mocky;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class InfoBuilderTest {

    Requester testRequster = new Requester() {
        @Override
        public String getStringFromRequest(String url) {
            String result = null;
            if ("mainAdress".equals(url)) {
                result = new Gson().toJson(List.of(
                        Map.of("id", "1",
                                "sourceDataUrl", "sourceadress",
                                "tokenDataUrl", "tokenadress"),
                        Map.of("id", "2",
                                "sourceDataUrl", "sourceadress1",
                                "tokenDataUrl", "tokenadress2")
                ));
            } else if (url.startsWith("sourceadress")) {
                result = new Gson().toJson(Map.of("value", url,
                        "ttl", url));
            } else if (url.startsWith("tokenadress")) {
                result = new Gson().toJson(Map.of("urlType", url,
                        "videoUrl", url));
            }

            return result;
        }
    };


    @Test
    public void checkEqualsRequestAndPArseMethods() throws ExecutionException, InterruptedException {
        List<Map<String, String>> result = new InfoBuilder(testRequster).getAndComputeInfo("mainAdress");
        assertThat(result.size(), is(2));
        assertThat(new Gson().toJson(result).equals(new InfoBuilder(testRequster).getCamerasJSON("mainAdress")), is(true));
    }

    @Test
    public void checkDetailsAdMap() {
        Map<String, String> resultFirst = new InfoBuilder(testRequster).getAndComputeDetails("sourceadress123");
        assertThat(resultFirst.get("value"), is("sourceadress123"));
        assertThat(resultFirst.get("ttl"), is("sourceadress123"));

        Map<String, String> resultSecond = new InfoBuilder(testRequster).getAndComputeDetails("tokenadress123");
        assertThat(resultSecond.get("urlType"), is("tokenadress123"));
        assertThat(resultSecond.get("videoUrl"), is("tokenadress123"));
    }
}