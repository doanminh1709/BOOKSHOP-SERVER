package project.spring.quanlysach.application.services;

import project.spring.quanlysach.domain.dto.TagDTO;
import project.spring.quanlysach.domain.entity.Tag;

import java.util.List;

public interface ITagService {
    List<Tag> ListTag(Integer page, Integer size);

    Tag createNewTag(TagDTO tagDTO);

    String updateTagById(TagDTO tagDTO, Integer id);

    Tag getTagById(Integer id);

    String deleteTagById(Integer id);
}
