package models;

public class User {

	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private String email;

	public User(String username, String firstName, String lastName, String password, String email) {
		setUsername(username);
		setFirstName(firstName);
		setLastName(lastName);
		setPassword(password);
		setEmail(email);
	}


	public String getUsername() {
		return username;
	}

	public User setUsername(String userName) {
		this.username = userName;
		return this;
	}


	public String getFirstName() {
		return firstName;
	}

	public User setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}


	public String getLastName() {
		return lastName;
	}

	public User setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}


	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}



	@Override
	public String toString() {

		return "I am a user.";
	}

}
