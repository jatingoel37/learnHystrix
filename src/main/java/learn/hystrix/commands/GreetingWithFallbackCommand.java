package learn.hystrix.commands;

import com.netflix.hystrix.HystrixCommand;

public class GreetingWithFallbackCommand extends HystrixCommand<String> {
	private final String name;
	private final boolean throwException;
	private final long threadSleep;

	protected GreetingWithFallbackCommand(String name, Setter setter, boolean throwException, long threadSleep) {
		super(setter);
		this.name = name;
		this.throwException = throwException;
		this.threadSleep = threadSleep;
	}

	@Override
	public String run() throws Exception {
		System.out.println("run method thread - " + Thread.currentThread().getName());
		if (threadSleep > 0) {
			System.out.println("Sleeping for " + threadSleep + " millis");
			Thread.sleep(threadSleep);
			System.out.println("Woke up");
		}

		if (throwException) {
			System.out.println("Exception is being thrown");
			throw new IllegalArgumentException("exception from " + this.getClass().getSimpleName());
		}

		return "Hello " + name;
	}

	@Override
	protected String getFallback() {
		System.out.println("fallback method thread - " + Thread.currentThread().getName());
		return "Hello with fallback " + name;
	}

}
