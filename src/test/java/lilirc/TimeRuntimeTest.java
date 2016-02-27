package lilirc;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 3D
 *
 */
public class TimeRuntimeTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
	
	@Test
	public void test_is() throws InterruptedException {
		String t1;
		LOGGER.info((t1= Time.is()));
		Thread.sleep(1000); // 59 --> 00 false
		String t2;
		LOGGER.info((t2= Time.is())+" ----- end Time.is()");
		Assert.assertEquals(1, t2.compareTo(t1));
	}
	@Test
	public void test_is_date() throws InterruptedException {
		String t1;
		Date date= new Date();
		LOGGER.info((t1= Time.is()));
		String t2;
		LOGGER.info((t2= Time.is(date))+" ----- end Time.is(date)");
		Assert.assertEquals(0, t2.compareTo(t1));
	}
	@Test
	public void test_is_date_timeStamp() throws InterruptedException {
		String t1;
		LOGGER.info((t1= Time.is(new Date(), "ss")));
		Thread.sleep(1000);
		String t2;
		LOGGER.info((t2= Time.is(new Date(), "ss"))+" ----- end Time.is(date, timeStamp)");
		Assert.assertEquals(1, t2.compareTo(t1));
	}
	@Test
	public void test_timeStamp() throws InterruptedException {
		String t1;
		LOGGER.info("["+(t1= Time.is(new Date(), "mm:ss"))+"]");
		Thread.sleep(1000);
		String t2;
		Time.timeStamp("mm:ss");
		LOGGER.info("["+(t2= Time.is())+"]  ----- end Time.timeStamp()");
		Assert.assertEquals(1, t2.compareTo(t1));
	}
}
