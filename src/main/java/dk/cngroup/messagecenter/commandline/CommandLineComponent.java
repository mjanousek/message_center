package dk.cngroup.messagecenter.commandline;

import dk.cngroup.messagecenter.commandline.command.Command;
import dk.cngroup.messagecenter.exception.RegistrationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Profile("!test")
public class CommandLineComponent implements CommandLineRunner {

	@Autowired
	CommandFactory commandFactory;

	@Override
	public void run(String... args) throws Exception {
		printWelcomeMessage();
		readInput();
	}

	private void readInput() {
		Scanner input = new Scanner(System.in);
		while (input.hasNext()) {
			String command = input.nextLine();
			if (shouldQuit(command)) {
				break;
			}
			execute(command);
		}
	}

	private void printWelcomeMessage() {
		System.out.println("... Application is running ...");
	}

	private boolean shouldQuit(String command) {
		return command.equalsIgnoreCase("EXIT") || command.equalsIgnoreCase("QUIT");
	}

	private void execute(String command) {
		try {
			Command c = commandFactory.createCommand(command);
			c.execute();
		} catch (RegistrationException e) {
			System.err.println(e.getMessage());
		}
	}
}
