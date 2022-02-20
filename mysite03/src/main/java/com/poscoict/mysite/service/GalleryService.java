package com.poscoict.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscoict.mysite.repository.GalleryRepository;
import com.poscoict.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	
	@Autowired
	private GalleryRepository galleryRepository;
	
	public List<GalleryVo> getImages() {
		return galleryRepository.findGalleryAll();
	}

	public Boolean removeImg(Long no) {
		return galleryRepository.deleteImage(no)==1;		
	}

	public Boolean saveImage(GalleryVo galleryVo) {
		return galleryRepository.insertImage(galleryVo)==1;
		
	}

}
