package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HrSelect {

	public static void main(String[] args) {
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 연결된 ip주소, 포트
			conn = DriverManager.getConnection(url, "hr", "hr"); // 연결 url, 아이디, 비밀번호

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = " select emp.employee_id, " + 
							"        emp.last_name, " +
							"        to_char(emp.hire_date, 'YY/MM/DD') emp_hiredate, " + // 웹에서 받는 열이름이므로, 되도록이면 쉽게 만들어서 사용하는 것이 편리함.
							"        to_char(man.hire_date, 'YY/MM/DD') man_hiredate " + 
							" from employees emp, employees man " +
							" where emp.manager_id=man.employee_id " + 
							" and emp.hire_date < man.hire_date ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery(); // 결과값으로 쿼리의 주소값을 rs에 저장하였고, rs객체가 생성되었음.

			// 4.결과처리
			while (rs.next()) { // 처음 시작시 커서가 맨 윗줄(열이름이 있는 행)에서 다음 행으로 이동, 다음 행이 있는 동안에 while문을 수행
				int employeeId = rs.getInt("employee_id");
				String lastName = rs.getString("last_name");
				String emphireDate = rs.getString("emp_hiredate");
				String manhireDate = rs.getString("man_hiredate"); // 콤마 안에는 열이름을 적어주는데, db에 출력되는 열이름을 그대로 써주어야한다.

				System.out.println(employeeId + "\t" + lastName + "\t" + emphireDate + "\t" + manhireDate); // 한 줄씩
																											// 내려가면서
																											// authorName값을
																											// 출력한다.

			}

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
	}

}
