package concurrent;

import java.io.*;
import java.net.URL;

public class FileDownload {

    private static class Attributes {
        private String url;
        private int delay;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getDelay() {
            return delay;
        }

        public void setDelay(int delay) {
            this.delay = delay;
        }

        public boolean isValid() {
            boolean result = false;
            if (url != null && url.length() > 0 && delay > 0) {
                result = true;
            }
            return result;
        }
    }


    public static void main(String[] args) throws Exception {
        Attributes attr = parseArgs(args);
        if (attr.isValid()) {
            String fileName = attr.getUrl().substring(attr.getUrl().lastIndexOf('/') + 1);
            try (BufferedInputStream in = new BufferedInputStream(new URL(attr.getUrl()).openStream());
                 FileOutputStream fileOutputStream =
                         new FileOutputStream(
                                 fileName.length() > 0 ? fileName : "file".concat(String.valueOf(System.currentTimeMillis())))) {
                byte[] dataBuffer = new byte[attr.getDelay()];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, attr.getDelay())) != -1) {
                    System.out.print("\r Loading ... ");
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    Thread.sleep(1000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Wrong params!");
        }
    }

    static public Attributes parseArgs(String[] arg) {
        Attributes result = new Attributes();
        if (arg.length > 1) {
            if (arg[0].length() > 0) {
                result.setUrl(arg[0]);
            }
            if (arg[1].length() > 0) {
                result.setDelay(Integer.parseInt(arg[1]));
            }
        }
        return result;
    }


}