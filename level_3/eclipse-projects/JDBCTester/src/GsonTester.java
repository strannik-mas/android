import com.google.gson.Gson;

public class GsonTester {

	public static void main(String[] args) {
		User u = new User();
		u.setAge(33);
		u.setId(12);
		u.setName("Kudrashov");
		u.setPosition("designer");
		
		Gson gson = new Gson();
		String json = gson.toJson(u, User.class);
		
		System.out.println(json);
	}

}
