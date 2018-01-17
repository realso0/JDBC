package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookInsertTest {

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
			String query = "INSERT INTO book VALUES (seq_book_id.nextval, ?, ?, ?, ?)"; // 문자열화 시켜줌. 바뀌는 값인 부분은 물음표로 지정
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, "26년"); // 첫번째 물음표에 String(문자열) 값 입력
			pstmt.setString(2, "재미주의"); // 두 번째 물음표에 들어갈 값 입력
			pstmt.setString(3, "2012-02-04");
			pstmt.setInt(4, 5);

			int count = pstmt.executeUpdate(); // 따로 적어준 값들을 조합해주어, DB로 날려주게 됨, int값을 반환하는 메소드(insert, update, delete)
			// 4.결과처리
			System.out.println(count + "건 저장완료");// 현재 우리는 insert 1개를 실행 하였음

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
