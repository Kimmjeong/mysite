package com.hanains.mysite.dao.test;

import java.util.List;

import com.hanains.mysite.dao.GuestBookDao;
import com.hanains.mysite.vo.GuestBookVo;

public class GuestBookDaoTest {

	public static void main(String[] args) {

		//insert();
		delete();
		getList();
		
		
	}
	
	// 방명록 리스트 출력
	public static void getList(){
		
		GuestBookDao dao=new GuestBookDao();
		
		List<GuestBookVo> list=dao.getList();
		
		for(GuestBookVo vo:list){
			System.out.println(vo);
		}
		
	}
	
	// 방명록 쓰기
	public static void insert(){
		
		GuestBookVo vo=new GuestBookVo();
		GuestBookDao dao=new GuestBookDao();
		
		vo.setName("홍길동");
		vo.setPassword("1111");
		vo.setMessage("홍홍홍");
		
		dao.insert(vo);
	}
	
	// 방명록 삭제
	public static void delete(){
		GuestBookDao dao=new GuestBookDao();
		
		dao.delete(1L,"1234");
	}

}
