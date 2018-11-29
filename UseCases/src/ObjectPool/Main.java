package ObjectPool;

import java.sql.Connection;

public class Main {

	public static void main(String[] args) {
		JDBCConnectionPool pool = new JDBCConnectionPool("org.hsqldb.jdbcDriver", "jdbc:hsqldb://localhost/mydb",
			      "sa", "secret");
		//get a connection
		Connection con = (Connection) pool.checkout();
		//use the connection
		//...
		// Return the connection:
		pool.checkin(con);
	}

}
