package com.hanains.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hanains.mysite.vo.BoardListVo;
import com.hanains.mysite.vo.BoardVo;

public class BoardDao {
	
	private Connection getConnection() throws SQLException {
		Connection connection = null;
		
		try {
			//1.드라이버 로딩
			Class.forName( "oracle.jdbc.driver.OracleDriver" );
		
			//2.커넥션 만들기(ORACLE DB)
			String dbURL  = "jdbc:oracle:thin:@localhost:1521:xe";
			connection = DriverManager.getConnection( dbURL, "webdb", "webdb" );
			
		} catch( ClassNotFoundException ex ){
			System.out.println( "드라이버 로딩 실패-" + ex );
		}
		
		return connection;
	}
	
	// 검색 + 페이징 적용 글목록
	public List<BoardListVo> getList(boolean search, String kwd, Long start, Long end) {

		List<BoardListVo> list = new ArrayList<BoardListVo>();

		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
			conn=getConnection();
			
			String where = "";

			if (search) {
				where = " and (a.title like '%%"+kwd+"%%' or b.name like '%%"+kwd+"%%')" ;
			}
			
			String sql=" select * "
					+ "from (select c.*, rownum as rnum, COUNT(*) OVER() AS TOTCNT "
					+ "from (select a.no, a.title, a.member_no, b.name as member_name, a.view_cnt, to_char(a.reg_date, 'yyyy-mm-dd hh:mi:ss') as reg_date "
					+ "from board a, member b where a.member_no = b.no"+where+" order by group_no DESC, order_no ASC) c) "
					+ "where rnum>="+start+" and rnum<="+end;
			
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);

			while (rs.next()) {
				//레코드 1줄 -> BoardDTO 1개
				BoardListVo vo = new BoardListVo();
				
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setMember_no(rs.getLong(3));
				vo.setMember_name(rs.getString(4));
				vo.setView_cnt(rs.getLong(5));
				vo.setReg_date(rs.getString(6));
				vo.setRnum(rs.getLong(7));
				vo.setTotcnt(rs.getLong(8));
				
				list.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("에러 - "+e);
		} finally {
			try {
				
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 글쓰기
	public void insert(BoardVo vo){
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=getConnection();
			
			String sql="insert into board values ( board_no_seq.nextval, ?, ?, ?, 0, SYSDATE,?,?,?)";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getMember_no());
			pstmt.setLong(4, vo.getGroup_no());
			pstmt.setLong(5, vo.getOrder_no());
			pstmt.setLong(6, vo.getDepth());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("에러 - "+e);
		} finally {
			try {
				
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 글보기
	public BoardVo view(Long no){
		
		BoardVo vo=null;
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try {
			conn=getConnection();
			stmt=conn.createStatement();
			
			String sql=" select no, title, content, member_no, group_no, order_no, depth from board where no="+no;
			rs=stmt.executeQuery(sql);
			
			if(rs.next()){
				
				vo=new BoardVo();
				
				String title=rs.getString(2);
				String content=rs.getString(3);
				Long member_no=rs.getLong(4);
				Long group_no=rs.getLong(5);
				Long order_no=rs.getLong(6);
				Long depth=rs.getLong(7);
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setMember_no(member_no);
				vo.setGroup_no(group_no);
				vo.setOrder_no(order_no);
				vo.setDepth(depth);
				
			}
			
			
		} catch (SQLException e) {
			System.out.println("에러 - "+e);
		} finally {
			try {
				if (rs!=null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;
	}

	// 글삭제
	public void delete(Long no, Long member_no){
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=getConnection();
			
			String sql="delete from board where no=? and member_no=?";
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			pstmt.setLong(2, member_no);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("에러 - "+e);
		} finally {
			try {
				
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 글수정
	public void update(BoardVo vo){
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=getConnection();
			String sql="update board set title=?, content=? where no=?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("에러 - "+e);
		} finally {
			try {
				
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	// 조회수 증가
	public void viewCount(Long no){
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			
			conn=getConnection();
			String sql="update board set view_cnt=view_cnt+1 where no=?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("에러 - "+e);
		} finally {
			try {
				
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	// 최대 그룹 번호
	public Long getGroupNo() {
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		Long n = 0L;
		
		try {
			
			conn=getConnection();
			stmt=conn.createStatement();

			String sql = "select nvl(max(group_no), 0) as maxGroup_no from board";
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				n = rs.getLong(1);
			}
			
		} catch (SQLException e) {
			System.out.println("에러 - "+e);
		} finally {
			try {
				if (rs!=null) rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return n;
	}

	// 그룹 내 순서 업데이트
	public void updateOrderNo(Long orderNo) {
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		
		try {
			conn=getConnection();
			String sql="update board set order_no=order_no+1 where order_no >=?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setLong(1, orderNo);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("에러 - "+e);
		} finally {
			try {
				
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
		
}
