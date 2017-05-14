package models;

public class User {

	// -------------------------------------------------------------------------------------//
	// Fields
	// -------------------------------------------------------------------------------------//

	private String id;
	private String username;
	private String email;

	private static User user = new User();

	// -------------------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------------------//

	private User() {
	}

	public User getInstance() {
		return user;
	}

	// -------------------------------------------------------------------------------------//

	public User setId(String id) {
		this.id = id;
		return user;
	}

	// -------------------------------------------------------------------------------------//

	public String getId() {
		return id;
	}

	// -------------------------------------------------------------------------------------//

	public User setUsername(String username) {
		this.username = username;
		return user;
	}

	// -------------------------------------------------------------------------------------//

	public String getUsername() {
		return username;
	}

	// -------------------------------------------------------------------------------------//

	public User setEmail(String email) {
		this.email = email;
		return user;
	}

	// -------------------------------------------------------------------------------------//

	public String getEmail() {
		return email;
	}

	// -------------------------------------------------------------------------------------//

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + "]";
	}

}
