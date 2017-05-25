package models;

public class User {

	//-------------------------------------------------------------------------------------//
	//Fields
	//-------------------------------------------------------------------------------------//

	private String firstName;
	private String lastName;
	private String username;

	private static User user = new User();

	//-------------------------------------------------------------------------------------//
	//Constructor
	//-------------------------------------------------------------------------------------//

	private User() {
	}

	public User getInstance() {
		return user;
	}

	//-------------------------------------------------------------------------------------//

	public User setFirstName(String firstName) {
		this.firstName = firstName;
		return user;
	}

	//-------------------------------------------------------------------------------------//

	public User setLastName(String lastName) {
		this.lastName = lastName;
		return user;
	}

	//-------------------------------------------------------------------------------------//

	public User setUsername(String username) {
		this.username = username;
		return user;
	}

	//-------------------------------------------------------------------------------------//

	public String getFirstName() {
		return firstName;
	}

	//-------------------------------------------------------------------------------------//

	public String getLastName() {
		return lastName;
	}

	//-------------------------------------------------------------------------------------//

	public String getUsername() {
		return username;
	}

}
