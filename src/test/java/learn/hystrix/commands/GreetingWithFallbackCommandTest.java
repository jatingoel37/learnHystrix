package learn.hystrix.commands;

import org.junit.Test;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.HystrixCommand.Setter;

public class GreetingWithFallbackCommandTest {

	// --------------------------------------------------------------
	// Normal flow
	// --------------------------------------------------------------

	/**
	 * Fallback doesnt run in main thread
	 */
	@Test
	public void testFallbackWhenException() {
		System.out.println(Thread.currentThread().getName());
		GreetingWithFallbackCommand command = new GreetingWithFallbackCommand("JATIN",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greetingWithFallback")),
				true, -1);
		System.out.println("Result is " + command.execute());

	}

	@Test
	public void testFallbackWhenTimeout() {
		System.out.println(Thread.currentThread().getName());
		GreetingWithFallbackCommand command = new GreetingWithFallbackCommand("JATIN",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greetingWithFallback")),
				false, 1500);
		System.out.println("Result is " + command.execute());

	}

	// --------------------------------------------------------------
	// Fallback disabled
	// --------------------------------------------------------------

	@Test(expected = HystrixRuntimeException.class)
	public void testFallbackWhenExceptionButFallbackDisabled() {
		System.out.println(Thread.currentThread().getName());
		GreetingWithFallbackCommand command = new GreetingWithFallbackCommand("JATIN",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greetingWithFallback"))
						.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withFallbackEnabled(false)),
				true, -1);
		System.out.println("Result is " + command.execute());

	}

	@Test(expected = HystrixRuntimeException.class)
	public void testFallbackWhenfallbackDisabled() {
		System.out.println(Thread.currentThread().getName());
		GreetingWithFallbackCommand command = new GreetingWithFallbackCommand("JATIN",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greetingWithFallback"))
						.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withFallbackEnabled(false)),
				false, 1500);
		System.out.println("Result is " + command.execute());

	}

	// --------------------------------------------------------------
	// Timeout disabled
	// --------------------------------------------------------------

	@Test
	public void testFallbackWhenTimeoutDisabled() {
		System.out.println(Thread.currentThread().getName());
		GreetingWithFallbackCommand command = new GreetingWithFallbackCommand("JATIN",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greetingWithFallback"))
						.andCommandPropertiesDefaults(
								HystrixCommandProperties.Setter().withExecutionTimeoutEnabled(false)),
				false, 1500);
		System.out.println("Result is " + command.execute());

	}

	// ------------------------------------------------------------------
	// semaphores
	// ------------------------------------------------------------------

	// --------------------------------------------------------------
	// Normal flow
	// --------------------------------------------------------------

	/**
	 * Fallback doesnt run in main thread
	 */
	@Test
	public void testFallbackWhenExceptionSemaphores() {
		System.out.println(Thread.currentThread().getName());
		GreetingWithFallbackCommand command = new GreetingWithFallbackCommand("JATIN",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greetingWithFallback"))
						.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
								.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)),
				true, -1);
		System.out.println("Result is " + command.execute());

	}

	@Test
	public void testFallbackWhenTimeoutSemaphores() {
		System.out.println(Thread.currentThread().getName());
		GreetingWithFallbackCommand command = new GreetingWithFallbackCommand("JATIN",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greetingWithFallback"))
						.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
								.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)),
				false, 1500);
		System.out.println("Result is " + command.execute());

	}

	// --------------------------------------------------------------
	// Fallback disabled
	// --------------------------------------------------------------

	@Test(expected = HystrixRuntimeException.class)
	public void testFallbackWhenExceptionButFallbackDisabledSemaphores() {
		System.out.println(Thread.currentThread().getName());
		GreetingWithFallbackCommand command = new GreetingWithFallbackCommand("JATIN",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greetingWithFallback"))
						.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withFallbackEnabled(false)
								.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)),
				true, -1);
		System.out.println("Result is " + command.execute());

	}

	@Test(expected = HystrixRuntimeException.class)
	public void testFallbackWhenfallbackDisabledSemaphores() {
		System.out.println(Thread.currentThread().getName());
		GreetingWithFallbackCommand command = new GreetingWithFallbackCommand("JATIN",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greetingWithFallback"))
						.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withFallbackEnabled(false)
								.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)),
				false, 1500);
		System.out.println("Result is " + command.execute());

	}

	// --------------------------------------------------------------
	// Timeout disabled
	// --------------------------------------------------------------

	@Test
	public void testFallbackWhenTimeoutDisabledSemaphores() {
		System.out.println(Thread.currentThread().getName());
		GreetingWithFallbackCommand command = new GreetingWithFallbackCommand("JATIN", Setter
				.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("greetingWithFallback"))
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutEnabled(false)
						.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)),
				false, 1500);
		System.out.println("Result is " + command.execute());

	}
}
