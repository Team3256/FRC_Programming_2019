package frc.team3256.robot.operations;

public class Logger {
	String className;
	String method;

	public Logger(String className) {
		this.className = className;
	}

	public void moveTo(String method) {
		this.method = method;
	}

	public void log(String variable, String value) {
		System.out.println(method + " ----- " + variable + " = " + value);
	}
}
