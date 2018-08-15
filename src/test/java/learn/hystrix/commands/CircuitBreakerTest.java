package learn.hystrix.commands;

import org.junit.Test;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommand.Setter;

public class CircuitBreakerTest {

	/**
	 * Hystrix circuit breaker is cached for command key
	 * @throws InterruptedException 
	 */
	@Test
	public void testCirrcuitBreaking() throws InterruptedException {
		HystrixCommandProperties.Setter setter = getSetter(true, 1, 2000, 50);

		GreetingWithFallbackCommand command1 = new GreetingWithFallbackCommand("command1",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greetingWithFallback"))
						.andCommandPropertiesDefaults(setter),
				false, 1500);

		System.out.println("Executing command 1");
		command1.execute();
		System.out.println("Circuit breaker - " + command1.isCircuitBreakerOpen());

		System.out.println("______________________________________________");

		GreetingWithFallbackCommand command2 = new GreetingWithFallbackCommand("command2",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greetingWithFallback"))
						.andCommandPropertiesDefaults(setter),
				false, 1500);

		System.out.println("Executing command 2");
		command2.execute();
		System.out.println("Circuit breaker - " + command2.isCircuitBreakerOpen());

		System.out.println("______________________________________________");

		GreetingWithFallbackCommand command3 = new GreetingWithFallbackCommand("command3",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greetingWithFallback"))
						.andCommandPropertiesDefaults(setter),
				true, 0);

		System.out.println("Executing command 3");
		command3.execute();
		System.out.println("Circuit breaker - " + command3.isCircuitBreakerOpen());

		System.out.println("______________________________________________");

		GreetingWithFallbackCommand command4 = new GreetingWithFallbackCommand("command4",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greetingWithFallback"))
						.andCommandPropertiesDefaults(setter),
				false, 1500);

		System.out.println("Executing command 4");
		command4.execute();
		System.out.println("Circuit breaker - " + command4.isCircuitBreakerOpen());

		System.out.println("______________________________________________");

		GreetingWithFallbackCommand command5 = new GreetingWithFallbackCommand("command5",
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("basic"))
						.andCommandKey(HystrixCommandKey.Factory.asKey("greetingWithFallback"))
						.andCommandPropertiesDefaults(setter),
				false, 0);

		Thread.sleep(2200);
		System.out.println("Executing command 5");
		command5.execute();
		System.out.println("Circuit breaker - " + command5.isCircuitBreakerOpen());

		System.out.println("______________________________________________");
	}

	private HystrixCommandProperties.Setter getSetter(boolean circuitBreakerEnabled, int requestVolume, int sleepTime,
			int errorPercentage) {
		return HystrixCommandProperties.Setter().withCircuitBreakerEnabled(circuitBreakerEnabled)//
				.withCircuitBreakerErrorThresholdPercentage(errorPercentage)//
				.withCircuitBreakerRequestVolumeThreshold(requestVolume)//
				.withCircuitBreakerSleepWindowInMilliseconds(sleepTime).withMetricsRollingPercentileWindowInMilliseconds(30_000);
	}
}
