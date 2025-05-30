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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    @Transactional
    public TagResponse createTag(TagCreationRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if(tagRepository.findByNameAndIsDeletedFalse(request.getName()).isPresent()){
            throw new AppException(ErrorCode.TAG_NAME_ALREADY_EXISTS );
        }
        Tag tag = new Tag();
        tag.setDescription(request.getDescription());
        tag.setName(request.getName());
        tag.setIsDeleted(false);
        tag.setStatus(EnumStatus.ACTIVE);
        tag.setIsDeleted(false);
        tagRepository.save(tag);
        return TagResponse.builder()
                .name(tag.getName())
                .description(tag.getDescription())
                .status(EnumStatus.ACTIVE)
                .build();
    }

    @Transactional
    public TagResponse update(TagUpdateRequest request, Long tagId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_FOUND));
        tagRepository.findByNameAndIsDeletedFalse(request.getName())
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
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
