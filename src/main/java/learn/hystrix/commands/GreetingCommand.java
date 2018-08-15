package learn.hystrix.commands;

import com.netflix.hystrix.HystrixCommand;

public class GreetingCommand extends HystrixCommand<String> {

	private final String name;

	protected GreetingCommand(String name, Setter setter) {
		super(setter);
		this.name = name;
	}

	@Override
	protected String run() throws Exception {
		System.out.println(Thread.currentThread().getName());
		return "Hello " + name;
	}

}
