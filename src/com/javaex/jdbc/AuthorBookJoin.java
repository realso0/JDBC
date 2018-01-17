package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorBookJoin {

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
			conn = DriverManager.getConnection(url, "webdb", "webdb"); // 연결 url, 아이디, 비밀번호

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = " select bo.book_id, " + 
									" au.author_name, " + 
									" au.author_desc, " + 
									" bo.title, " + 
									" bo.pubs, " + 
									" to_char(bo.pub_date, 'YY/MM/DD') pub_date, " + // 날짜형변환 해준뒤, 열이름을 지정해준다.
									" bo.author_id " + 
							" from author au, book bo " + 
							" where au.author_id=bo.author_id "; 
			// *로 필드명을 모두 지시하면 안된다.(문자열화 시켜줌. 바뀌는 값인 부분은 물음표로 지정)
																										 
			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery(); // 결과값으로 쿼리의 주소값을 rs에 저장하였고, rs객체가 생성되었음.

			// 4.결과처리
			while (rs.next()) { // 처음 시작시 커서가 맨 윗줄(열이름이 있는 행)에서 다음 행으로 이동, 다음 행이 있는 동안에 while문을 수행
				int bookId = rs.getInt("book_id");
				String authorName = rs.getString("author_name");
				String authorDesc = rs.getString("author_desc");
				String title = rs.getString("title");
				String pubs = rs.getString("pubs"); // 콤마 안에는 열이름을 적어주는데, db에 출력되는 열이름을 그대로 써주어야한다.
				String pubDate = rs.getString("pub_date");
				int authorId = rs.getInt("author_id");

				System.out.println(bookId + "\t" + authorName + "\t" + authorDesc + "\t" + title + "\t" + pubs + "\t"
						+ pubDate + "\t" + authorId); // 한 줄씩 내려가면서 authorName값을 출력한다.
				//웹으로부터 db에 받은 값을, db로부터 값을 가져옴. while문을 한번 수행하고, 저장된 값들을 버리는 형태로 다음줄이 없을 때까지 반복하고 있다.
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
