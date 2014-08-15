package threadPool;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.BlockingQueue;
import java.util.ConcurrentModificationException;

import collectionTest.globalObjects;
public class threadService {
    private static ExecutorService executor = null;
	public static void startService(globalObjects gb){
		executor = Executors.newFixedThreadPool(100);
		return;
	}
	
	final class Task implements Runnable{
		private globalObjects gb = null; 
		public Task(globalObjects gb01){
			this.gb = gb01;
		}
		public void performOperation(){
			
		} 
		public void run(){
			
			return;
		}
	}
}
