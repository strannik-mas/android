

import java.io.IOException;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import com.google.gson.Gson;

/**
 * Servlet implementation class UsersAll3
 */
@WebServlet("/UsersAll3")
public class UsersAll3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersAll3() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try (
				SessionFactory factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
				Session session = factory.openSession();
				)
		{
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> query = builder.createQuery(User.class);
			
			Root<User> root = query.from(User.class);
			
			query.select(root);
			Query<User> q  = session.createQuery(query);
			List<User> users = q.getResultList();
			Gson gson = new Gson();
			String json = gson.toJson(users);
			/*for(User u : users) {
				System.out.println("user: " + u.getName());
			}*/
			
			response.setContentType("text/json;charset=UTF-8");
			response.getWriter().println(json);
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
