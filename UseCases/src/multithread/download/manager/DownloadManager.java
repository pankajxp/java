package multithread.download.manager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DownloadManager {

	public static void main(String[] args) {
		URL file = null;
		ReadableByteChannel rbc = null;
		try {
			// spurce file
			 String url = "http://dl1.video.varzesh3.com/video/clip93/12/video/havashi/top5_save_derby_dortmond.mp4";
//			String url = "D:\\medicines.txt";
			file = new URL(url);
			rbc = Channels.newChannel(file.openStream());
			int srcSize = file.openConnection().getContentLength();
			int partSize = srcSize / 2;
			ExecutorService pool = Executors.newFixedThreadPool(4);
			// target files
			FileOutputStream outStream1 = new FileOutputStream("D:\\Testdir\\tmp1.mp4");
			FileChannel fc1 = outStream1.getChannel();
//			long destSize1 = outStream1.getChannel().position();
			pool.submit(new Downloader(fc1, 1, 0, partSize, rbc));

			FileOutputStream outStream2 = new FileOutputStream("D:\\Testdir\\tmp2.mp4");
			FileChannel fc2 = outStream2.getChannel();
//			long destSize2 = outStream2.getChannel().position();
			pool.submit(new Downloader(fc2, 2, partSize, partSize, rbc));

			downloadPercent(srcSize, fc1, fc2);
			pool.shutdown();
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (MalformedURLException | InterruptedException ex) {
			Logger.getLogger(DownloadManager.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(DownloadManager.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static void downloadPercent(long srcSize, FileChannel... fileOutputStreams) throws IOException {
		Thread pecentThread = new PercentThread(srcSize, fileOutputStreams);
		pecentThread.start();
	}

}

class PercentThread extends Thread {
	long srcSize; FileChannel[] fileChannels;
	long sizeDownloaded = 0;
	long downloadPercent = 0;
	public PercentThread(long srcSize, FileChannel[] fileChannels) {
		super();
		this.srcSize = srcSize;
		this.fileChannels = fileChannels;
	}

	public void run(){
		while(true){
			sizeDownloaded = 0;
			for (FileChannel fc : fileChannels) {
				try {
					sizeDownloaded += fc.position();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			downloadPercent = (sizeDownloaded / srcSize) * 100;
			System.out.println("download percent" + downloadPercent);
			if(downloadPercent==100) {
				return;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}