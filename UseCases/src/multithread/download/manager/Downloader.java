package multithread.download.manager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Downloader implements Runnable {
	private volatile FileChannel fc;
    private int num;
    private long start;
    private long end;
    ReadableByteChannel rbc;
    
    
	public Downloader(FileChannel fc, int num, int start, int end, ReadableByteChannel rbc) {
		this.fc = fc;
        this.num = num;
        this.start = start;
        this.end = end;
        this.rbc = rbc;
	}

	@Override
	public void run() {
		download();
	}
	
	private void download(){
        try {
            System.out.println(num + " is executing");
            fc.transferFrom(rbc, start, end);
            System.out.println("hello download");
//            downloadPercent = (fc.position() / 56465) * 100;
//			System.out.println("download percent" + downloadPercent);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
