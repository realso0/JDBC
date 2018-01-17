package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuthorInsertTest {

	public static void main(String[] args) {
		// 0. import java.sql.*;
		Connection conn = null; // 연결 잘됬는지 여부 때문에 필요
		PreparedStatement pstmt = null; // 쿼리문 관련
		// ResultSet rs = null; //select문 할때만 필요한 코드

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 연결된 ip주소, 포트
			conn = DriverManager.getConnection(url, "webdb", "webdb"); // 연결 url, db아이디, db비밀번호
			// null값이였던 conn 클래스 변수에 getConnection() 해줌으로써, 값을 입력하고, Connection객체를 생성하였음.

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "INSERT INTO author VALUES (seq_author_id.nextval, ?, ?)"; // 문자열로 받음, 바뀌는 값인 부분은 물음표로 지정한다.
			pstmt = conn.prepareStatement(query);
			/*
			 * Connection객체의 prepareStatement()메소드에 query문자를 매개변수로 입력하면, 메소드 안의 new연산자에 의해
			 * PrepareStatement객체가 생성된다. PrepareStatement객체안에는 setString(),excuteUpdate()등의
			 * 메소드가 있으며, 이 객체를 pstmt로 받는다.
			 */

			pstmt.setString(1, "소한준"); // 첫번째 물음표에 String(문자열) 값 소한준 입력
			pstmt.setString(2, "20년간 작가생활"); // 두 번째 물음표에 들어갈 값 입력

			int count = pstmt.executeUpdate();
			// 따로 적어준 값들을 조합하여, DB로 보내게 되며, executeUpdate()메소드는 int값을 반환하는 메소드(insert,
			// update, delete) 이다.

			// 4.결과처리
			System.out.println(count + "건 저장완료");// 현재 우리는 insert 1건을 저장 하였음

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
