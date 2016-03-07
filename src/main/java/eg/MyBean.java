package eg;

import java.io.Serializable;

public class MyBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private final String firstName;
	private final String lastName;

	public MyBean(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}