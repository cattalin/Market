package networking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Response implements Serializable {
	public static final long serialVersionUID = 1;
	public static final int LOGIN_APPROVED = 50;
	public static final int REGISTER_SUCCESSFUL = 51;
	public static final int GET_PRODUCTS = 52;
	public static final int GET_CATEGORIES = 53;
	public static final int GET_BUYING_OFFERS = 54;
	public static final int GET_SELLING_OFFERS = 55;

	private int resCode;
	private ArrayList<HashMap<String, Object>> parameters;

	public Response(int resCode) {
		this.resCode = resCode;
		parameters = null;
	}

	public void setParameters(ArrayList<HashMap<String, Object>> params) {
		this.parameters = params;
	}

	public int getResCode() {
		return resCode;
	}

	public ArrayList<HashMap<String, Object>> getParameters() {
		return parameters;
	}



	public String toString() {
		String result = "";
		switch (resCode) {
		case LOGIN_APPROVED:
			result += "LOGIN_APPROVED";
			break;
		case REGISTER_SUCCESSFUL:
			result += "REGISTER_SUCCESSFUL";
			break;
		case GET_PRODUCTS:
			result += "GET_PRODUCTS";
			break;
		}
		result += " request with the parameters " + parameters.toString();
		return result;
	}
}
