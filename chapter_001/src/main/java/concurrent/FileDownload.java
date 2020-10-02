package concurrent;

import java.io.*;
import java.net.URL;

public class FileDownload {

    final private Attributes attr;

    public FileDownload(String[] args) {
        this.attr = parseArgs(args);
    }

    boolean startDownloading() throws InterruptedException {
        boolean result = attr.isValid();
        if (result) {
            Downloader downloader = new Downloader();
            Thread thread = new Thread(downloader, "Download process");
            thread.start();
            while (thread.isAlive()) {
                System.out.print("\r Loading ... ");
            }
        }
        return result;
    }

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


    private class Downloader implements Runnable {

        @Override
        public void run() {
            String fileName = attr.getUrl().substring(attr.getUrl().lastIndexOf('/') + 1);
            try (BufferedInputStream in = new BufferedInputStream(new URL(attr.getUrl()).openStream());
                 FileOutputStream fileOutputStream =
                         new FileOutputStream(
                                 fileName.length() > 0 ? fileName : "file".concat(String.valueOf(System.currentTimeMillis())))) {
                byte[] dataBuffer = new byte[attr.getDelay()];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, attr.getDelay())) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    Thread.sleep(1000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws Exception {

        FileDownload fileDownload = new FileDownload(args);

        if (!fileDownload.startDownloading()) {
            System.out.println("Wrong params!");
        }
    }

    private Attributes parseArgs(String[] arg) {
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