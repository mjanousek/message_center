package dk.cngroup.messagecenter.commandline;

import dk.cngroup.messagecenter.commandline.command.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CommandLineService implements CommandLineRunner {

	@Autowired
	CommandFactory commandFactory;

	@Override
	public void run(String... args) throws Exception {
		Scanner input = new Scanner(System.in);
		System.out.println("... Application is running ...");
		while (input.hasNext()) {
			String command = input.nextLine();
			if (shouldQuit(command)) {
				break;
			}
			execute(command);
		}
	}

	private boolean shouldQuit(String command) {
		return command.equalsIgnoreCase("EXIT") || command.equalsIgnoreCase("QUIT");
	}

	private void execute(String command) {
		try {
			Command c = commandFactory.createCommand(command);
			c.execute();
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}
}
