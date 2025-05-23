package Project.example.Project_1.service;

import Project.example.Project_1.enity.AbstractEntity;
import Project.example.Project_1.enity.Tag;
import Project.example.Project_1.enums.EnumStatus;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.TagRepository;
import Project.example.Project_1.request.TagCreationRequest;
import Project.example.Project_1.request.TagUpdateRequest;
import Project.example.Project_1.response.TagResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    TagRepository tagRepository;

    @Transactional
    public TagResponse createTag(TagCreationRequest tagCreationRequest){
        Tag tag = tagRepository.findByNameAndIsDeletedFalse(tagCreationRequest.getName())
                .orElseThrow(() -> new AppException(ErrorCode.TAG_NAME_NOT_FOUND ));
        tag.setIsDeleted(false);
        tag.setStatus(EnumStatus.ACTIVE);
        tagRepository.save(tag);
        return TagResponse.builder()
                .name(tag.getName())
                .description(tag.getDescription())
                .status(EnumStatus.ACTIVE)
                .build();
    }

    @Transactional
    public TagResponse update(TagUpdateRequest tagUpdateRequest, Long tagId){
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_FOUND));
        tagRepository.findByNameAndIsDeletedFalse(tagUpdateRequest.getName())
                .filter(existingTag -> !existingTag.getId().equals(tagId))
                .ifPresent(existingTag -> {
                    throw new AppException(ErrorCode.TAG_NAME_ALREADY_EXISTS);
                });
        tag.setIsDeleted(false);
        tag.setStatus(EnumStatus.ACTIVE);
        tagRepository.save(tag);
        return TagResponse.builder()
                .name(tag.getName())
                .description(tag.getDescription())
                .status(EnumStatus.ACTIVE)
                .build();
    }

    @Transactional
    public void deleteTag(Long tagId){
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_FOUND));
        tag.setIsDeleted(true);
        tagRepository.save(tag);
    }

    public TagResponse getTag(Long tagId){
        Tag tag = tagRepository.findByIdAndIsDeletedFalse(tagId).orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_FOUND));
        return TagResponse.builder()
                .name(tag.getName())
                .description(tag.getDescription())
                .status(EnumStatus.ACTIVE)
                .build();
    }

    public List<Tag> getTagList(){
        return new ArrayList<>(tagRepository.findAll())
                .stream()
                .filter(AbstractEntity::getIsDeleted)
                .toList();
    }
}
