package com.poscoict.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscoict.mysite.vo.GalleryVo;

@Repository
public class GalleryRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<GalleryVo> findGalleryAll() {
		return sqlSession.selectList("gallery.findAll");
	}

	public int deleteImage(Long no) {
		return sqlSession.delete("gallery.deleteImage",no);
	}

	public int insertImage(GalleryVo galleryVo) {
		return sqlSession.insert("gallery.insertImage", galleryVo);
	}
}
