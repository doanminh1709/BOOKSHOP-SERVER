package project.spring.quanlysach.application.services;

import project.spring.quanlysach.domain.dto.CateDTO;
import project.spring.quanlysach.domain.entity.Cate;

import java.util.List;

public interface ICateService {
    List<Cate> listCate(Integer page, Integer size);

    Cate createNewCate(CateDTO cateDTO);

    String updateCateById(CateDTO cateDTO, Integer id);

    Cate getCateById(Integer id);

    String deleteCateById(Integer id);
}
