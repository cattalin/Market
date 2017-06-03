package networking;

import java.io.Serializable;
import java.util.HashMap;

public class Request implements Serializable {
	public static final long serialVersionUID = 1;
	public static final int LOGIN = 20; // username and password
	public static final int REGISTER = 21; // all user data
	public static final int GET_PRODUCTS = 22; // no parameters
	public static final int GET_PRODUCTS_BY_CATEGORY = 23; // category
	public static final int GET_CATEGORIES = 24; // no parameters
	public static final int GET_BUYING_OFFERS = 25; // no parameters
	public static final int GET_BUYING_OFFERS_BY_CATEGORY = 26; // the category
																// of products
	public static final int GET_BUYING_OFFERS_BY_PRODUCT = 27; // the PRODUCT
	public static final int GET_SELLING_OFFERS = 28; // no parameters
	public static final int GET_SELLING_OFFERS_BY_CATEGORY = 29; // the category
																	// of
																	// products
	public static final int GET_SELLING_OFFERS_BY_PRODUCT = 30; // the PRODUCT

	public static final int CREATE_BUYING_OFFER = 31;
	public static final int CREATE_SELLING_OFFER = 32;

	public static final int DISCONNECT = 33;
	public static final int ACCEPT_BUYING_OFFER = 34;
	public static final int ACCEPT_SELLING_OFFER = 35;

	private int reqCode;
	private HashMap<String, Object> parameters;

	public Request(int reqCode) {
		this.reqCode = reqCode;
		parameters = null;
	}

	public void setParameters(HashMap<String, Object> params) {
		this.parameters = params;
	}

	public int getReqCode() {
		return reqCode;
	}

	public HashMap<String, Object> getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		String result = "";
		switch (reqCode) {
		case LOGIN:
			result += "LOGIN";
			break;
		case REGISTER:
			result += "REGISTER";
			break;
		case GET_PRODUCTS:
			result += "GET_PRODUCTS";
			break;
		case GET_PRODUCTS_BY_CATEGORY:
			result += "GET_PRODUCTS_BY_CATEGORY";
			break;
		case GET_CATEGORIES:
			result += "GET_CATEGORIES";
			break;
		case GET_BUYING_OFFERS:
			result += "GET_BUYING_OFFERS";
			break;
		case GET_BUYING_OFFERS_BY_CATEGORY:
			result += "GET_BUYING_OFFERS_BY_CATEGORY";
			break;
		case GET_BUYING_OFFERS_BY_PRODUCT:
			result += "GET_BUYING_OFFERS_BY_PRODUCT";
			break;
		case GET_SELLING_OFFERS:
			result += "GET_SELLING_OFFERS";
			break;
		case GET_SELLING_OFFERS_BY_CATEGORY:
			result += "GET_SELLING_OFFERS_BY_CATEGORY";
			break;
		case GET_SELLING_OFFERS_BY_PRODUCT:
			result += "GET_SELLING_OFFERS_BY_PRODUCT";
			break;

		case CREATE_BUYING_OFFER:
			result += "CREATE_BUYING_OFFER";
			break;
		case CREATE_SELLING_OFFER:
			result += "CREATE_SELLING_OFFER";
			break;
		}
		result += " request with the parameters ";
		if (parameters != null)
			result += parameters.toString();
		return result;
	}
}
