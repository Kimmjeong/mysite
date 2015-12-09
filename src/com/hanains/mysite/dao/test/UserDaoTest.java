package com.hanains.mysite.dao.test;

import com.hanains.mysite.dao.UserDao;
import com.hanains.mysite.vo.UserVo;

public class UserDaoTest {

	public static void main(String[] args) {
		// insert test
		insertTest();
		
		// get test
		getTest();
	}
	
	public static void getTest() {
		UserDao dao = new UserDao();
		UserVo vo = dao.get( "gildong@gmail.com", "1234" );
		System.out.println( vo );
	}
	
	public static void insertTest() {
		UserDao dao = new UserDao();
		
		UserVo vo = new UserVo();
		vo.setName( "홍길동" );
		vo.setEmail( "gildong@gmail.com" );
		vo.setPassword( "1234" );
		vo.setGender( "male" );
		
		dao.insert(vo);
	}
		
}
