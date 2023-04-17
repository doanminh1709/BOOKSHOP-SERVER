package project.spring.quanlysach.application.services.imp;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.mapper.TagMapper;
import project.spring.quanlysach.application.repo.TagRepository;
import project.spring.quanlysach.application.services.ITagService;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.dto.TagDTO;
import project.spring.quanlysach.domain.entity.Tag;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ImpTagService implements ITagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper = Mappers.getMapper(TagMapper.class);

    public ImpTagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> ListTag(Integer page, Integer size) {
        List<Tag> listTags;
        if (page != null) {
            listTags = tagRepository.findAll(PageRequest.of(page, size)).getContent();
        } else {
            listTags = tagRepository.findAll();
        }
        return listTags;
    }

    @Override
    public Tag createNewTag(TagDTO tagDTO) {
        Tag tagName = tagRepository.findByName(tagDTO.getName());
        if (tagName != null) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.DUPLICATE_NAME, tagName.getName()));
        } else {
            Tag newTag = tagMapper.toTag(tagDTO);
            return tagRepository.save(newTag);
        }
    }

    @Override
    public String updateTagById(TagDTO tagDTO, Integer id) {
        Optional<Tag> foundTag = tagRepository.findById(id);
        if (foundTag.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "tag", id));
        } else {
            Tag tag = tagRepository.findByName(tagDTO.getName());
            if (tag != null && !Objects.equals(tag.getName(), foundTag.get().getName())) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.EXITS_NAME, tagDTO.getName()));
            } else {
                foundTag = Optional.ofNullable(tagMapper.toTag(tagDTO));
                foundTag.get().setId(id);
                tagRepository.save(foundTag.get());
                return DevMessageConstant.Common.NOTIFICATION_UPDATE_SUCCESS;
            }
        }
    }

    @Override
    public Tag getTagById(Integer id) {
        Optional<Tag> foundTag = tagRepository.findById(id);
        if (foundTag.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "Tag", id));
        }
        return foundTag.get();
    }

    @Override
    public String deleteTagById(Integer id) {
        Optional<Tag> foundById = tagRepository.findById(id);
        if (foundById.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "tag", id));
        } else {
            tagRepository.delete(foundById.get());
        }
        return DevMessageConstant.Common.NOTIFICATION_DELETE_SUCCESS;
    }
}
