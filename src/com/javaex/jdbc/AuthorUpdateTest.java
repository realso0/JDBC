package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthorUpdateTest {

	public static void main(String[] args) {
		// 0. import java.sql.*;
		Connection conn = null; // 연결 잘됬는지 여부 때문에 필요
		PreparedStatement pstmt = null; // 쿼리문 관련
		// ResultSet rs = null; //select문 할때만 필요

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 연결된 ip주소, 포트
			conn = DriverManager.getConnection(url, "webdb", "webdb"); // 연결 url, 아이디, 비밀번호

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "update author set author_name='강풀' where author_id=?";
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, 3); // 첫번째 물음표가 2인 것

			int count = pstmt.executeUpdate(); // 따로 적어준 값들을 조합해주어, DB로 날려주게 됨.
			// 4.결과처리
			System.out.println(count + "건 수정완료");// 현재 update 1건을 실행하였음.

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				// if (rs != null) {
				// rs.close();
				// }
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
