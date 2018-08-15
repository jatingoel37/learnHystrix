package learn.hystrix.commands;

import java.util.concurrent.Future;

import org.junit.Test;

import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.netflix.hystrix.exception.HystrixRuntimeException;

import rx.Observable;

public class GreetingCommandTest {

	/**
	 * execute method uses queue internally
	 * 
	 * @throws Exception
	 */
	@Test
	public void testExecute() throws Exception {
		System.out.println(Thread.currentThread().getName());
		GreetingCommand command = new GreetingCommand("JATIN",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greeting")));
		System.out.println("Result is " + command.execute());
	}

	/**
	 * When command is run twice, exception is thrown
	 */
	@Test(expected = HystrixRuntimeException.class)
	public void testQueueWithException() throws Exception {
		System.out.println(Thread.currentThread().getName());
		GreetingCommand command = new GreetingCommand("JATIN",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greeting")));
		command.queue().get();
		System.out.println("Result is " + command.execute());
	}

	/**
	 * test queue
	 * 
	 * @throws Exception
	 */
	@Test
	public void testQueue() throws Exception {
		System.out.println(Thread.currentThread().getName());
		GreetingCommand command = new GreetingCommand("JATIN",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greeting")));
		Future<String> fututre = command.queue();
		System.out.println("Result is " + fututre.get());
	}

	/**
	 * test observe, hot observable
	 * 
	 * @throws Exception
	 */
	@Test
	public void testObserve() throws Exception {
		System.out.println(Thread.currentThread().getName());
		GreetingCommand command = new GreetingCommand("JATIN",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greeting")));
		Observable<String> hotObservable = command.observe();
		hotObservable.subscribe(res -> System.out.println("Result is " + res));
	}

	/**
	 * test toObservable, cold observable --> nothing happens
	 * 
	 * @throws Exception
	 */
	@Test
	public void testToObservable() throws Exception {
		System.out.println(Thread.currentThread().getName());
		GreetingCommand command = new GreetingCommand("JATIN",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greeting")));
		command.toObservable();
	}

	/**
	 * execute method uses queue internally and semaphore- runs in same thread
	 * 
	 * @throws Exception
	 */
	@Test
	public void testExecuteWithSemaphore() throws Exception {
		System.out.println(Thread.currentThread().getName());
		GreetingCommand command = new GreetingCommand("JATIN",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greeting"))
						.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
								.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)));
		System.out.println("Result is " + command.execute());
	}

}
