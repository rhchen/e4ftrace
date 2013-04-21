package net.sf.e4ftrace.test.io;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.SortedMap;

import net.sf.commonstringutil.StringUtil;
import net.sf.e4ftrace.test.util.Grep;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.collect.TreeBasedTable;

public class NioReadLineTest {

	//private String filePath = "samples/ftrace/sched_05.log";
	private String filePath = "samples/ftrace/sched_07_irq_switch.log";
	
	private File file;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("NioReadLineTest : setUpBeforeClass");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("NioReadLineTest : tearDownAfterClass");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("NioReadLineTest : setUp");
	
		file = new File(filePath);
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("NioReadLineTest : tearDown");
	}

	@Test
	public void test_grep() throws IOException{
	
		long timeStart = System.currentTimeMillis();
		
//		Grep.compile("(?i).*sched_switch.*");
//		Grep.grep(file);
		
		printTime("test_grep", timeStart);
	}
	
	@Test
	public void test_read_page() throws IOException{
	
		long timeStart = System.currentTimeMillis();
		
		FileInputStream fis = new FileInputStream(file);
		
		FileChannel fileChannel = fis.getChannel();
		
		long size = fileChannel.size();
		
		int M_BYTE = 1024 * 1024;
		
		int pages = (int) (size / M_BYTE);
		
		System.out.println("size : "+ size + " size : "+ pages * M_BYTE);
		
		long positionStart = 0;
		long positionEnd   = 0;
		String lastLine = "";
		
		TreeBasedTable<Integer, Long, Long> table = TreeBasedTable.<Integer, Long, Long>create();
		
		for(int i=0; i<=pages; i++){
			
			long limit = (i+1) * M_BYTE > fileChannel.size() ? fileChannel.size() : (i+1) * M_BYTE;
			
			long bufferSize = limit - (i * M_BYTE);
			
			MappedByteBuffer mmb = fileChannel.map(FileChannel.MapMode.READ_ONLY, i * M_BYTE, bufferSize);

			byte[] buffer = new byte[(int) bufferSize];
			
			mmb.get(buffer);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer)));
			
			lastLine = readLine(in);
			
			positionEnd = i == pages ? limit : limit - lastLine.getBytes().length;
			
			table.put(i, positionStart, positionEnd);
			
			System.out.println("page : "+ i +", s : "+ positionStart +", e : "+ positionEnd + ", linit : "+ limit);
			
			positionStart = positionEnd;
		}
		
		
		
		Iterator<Integer> it = table.rowKeySet().iterator();
		
		while(it.hasNext()){
			
			int pageNum = it.next();
			
			SortedMap<Long, Long> map = table.row(pageNum);
			
			Iterator<Long> itk = map.keySet().iterator();
			
			while(itk.hasNext()){
				
				long posStart = itk.next();
				
				long posEnd = map.get(posStart);
				
				long bufferSize = posEnd - posStart;
				
				MappedByteBuffer mmb = fileChannel.map(FileChannel.MapMode.READ_ONLY, posStart, bufferSize);

				byte[] buffer = new byte[(int) bufferSize];
				
				mmb.get(buffer);
				
				BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer)));
				
				lastLine = readLine(in);
				
			}
			
		}
		
		printTime("test_read_page", timeStart);
	}
	
	@Test
	public void test_mapbuffer() throws IOException {
		
		long timeStart = System.currentTimeMillis();
		
		FileInputStream fis = new FileInputStream(file);
		
		//RandomAccessFile fis = new RandomAccessFile(file, "r");
		
		FileChannel fileChannel = fis.getChannel();
		
		long size = fileChannel.size();
		
		MappedByteBuffer mmb = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, size);

		byte[] buffer = new byte[(int) size];
		
		mmb.get(buffer);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer)));

		readLine(in);
		
		printTime("test_mapbuffer", timeStart);
		
//		timeStart = System.currentTimeMillis();
//		
//		long size2 = fileChannel.size() - size;
//		
//		mmb = fileChannel.map(FileChannel.MapMode.READ_ONLY, size, size2);
//
//		buffer = new byte[(int) size2];
//		
//		mmb.get(buffer);
//		
//		in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer)));
//		
//		readLine(in);
//		
//		printTime("test_mapbuffer", timeStart);
	}
	
	private String readLine(BufferedReader in) throws IOException{
		
		String line;
		
		int pCount = 0;
		
		String firstLine = null;
		String lastLine  = null;
		
		for (line = in.readLine(); line != null; line = in.readLine()) {
			
			firstLine = firstLine == null ? line : firstLine;
			lastLine = line;
			
			boolean st = StringUtil.startsWithIgnoreCase(line, "#");
			
			if (!st) {

				boolean find = StringUtil.countText(line, "sched_switch") >= 1 ? true : false;
				
				if(find){
					
					pCount ++;
					
					if(pCount % 10000 == 0) System.out.println("readLine count : "+ pCount);
					
				}//if
				
			}//if
			
		}//for
		
		System.out.println("First Line : "+ firstLine);
		System.out.println("Last Line :  "+ lastLine);
		
		return lastLine;
	}
	
	private void printTime(String id, long timeStart){
		
		long duration = System.currentTimeMillis() - timeStart;
		
		System.out.println(id +" : "+ duration);
	}

}
