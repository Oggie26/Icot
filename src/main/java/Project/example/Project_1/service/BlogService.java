package Project.example.Project_1.service;

import Project.example.Project_1.enity.Blog;
import Project.example.Project_1.enity.User;
import Project.example.Project_1.enums.EnumStatus;
import Project.example.Project_1.enums.ErrorCode;
import Project.example.Project_1.exception.AppException;
import Project.example.Project_1.repository.BlogRepository;
import Project.example.Project_1.repository.UserRepository;
import Project.example.Project_1.request.BlogCreateRequest;
import Project.example.Project_1.request.BlogUpdateRequest;
import Project.example.Project_1.response.BlogResponse;
import Project.example.Project_1.response.PageResponse;
import lombok.AccessLevel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlogService  {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BlogRepository blogRepository;

    @Transactional(rollbackOn = Exception.class)
    public BlogResponse createBlog(BlogCreateRequest request) throws Throwable {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String username = authentication.getName();
        User user =  userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));

        if(blogRepository.findBlogByBlogNameAndIsDeletedFalse(request.getBlogName()).isPresent()){
            throw new AppException(ErrorCode.BLOG_NAME_EXISTED);
        }
        if (request.getBlogName() == null || request.getBlogName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_BLOG_NAME);
        }

        Blog blog = Blog.builder()
                .blogName(request.getBlogName())
                .image(request.getImage())
                .description(request.getDescription())
                .content(request.getContent())
                .date(LocalDateTime.now())
                .status(EnumStatus.ACTIVE)
                .createdBy(user.getFullName())
                .build();
        blog.setIsDeleted(false);
        blogRepository.save(blog);
        return BlogResponse.builder()
                .blogName(blog.getBlogName())
                .content(blog.getContent())
                .image(blog.getImage())
                .status(blog.getStatus())
                .id(blog.getId())
                .description(blog.getDescription())
                .date(LocalDate.now())
                .createdBy(user.getFullName())
                .build();
    }

    @Transactional(rollbackOn = Exception.class)
    public BlogResponse updateBlog(BlogUpdateRequest request, Long blogId) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        Blog existingBlog = blogRepository.findById(blogId)
                .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXIST));

        if (request.getBlogName() == null || request.getBlogName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_BLOG_NAME);
        }
        boolean blogNameExists = blogRepository.findBlogByBlogNameAndIsDeletedFalse(request.getBlogName())
                .filter(blog -> !blog.getId().equals(blogId))  // Exclude current blog from check
                .isPresent();

        if (blogNameExists) {
            throw new AppException(ErrorCode.BLOG_NAME_EXISTED);
        }

        existingBlog.setBlogName(request.getBlogName());
        existingBlog.setImage(request.getImage());
        existingBlog.setDescription(request.getDescription());
        existingBlog.setContent(request.getContent());
        existingBlog.setDate(LocalDateTime.now());
        existingBlog.setStatus(EnumStatus.ACTIVE);
        existingBlog.setCreatedBy(existingBlog.getCreatedBy());

        blogRepository.save(existingBlog);

        return BlogResponse.builder()
                .blogName(existingBlog.getBlogName())
                .content(existingBlog.getContent())
                .image(existingBlog.getImage())
                .status(existingBlog.getStatus())
                .id(existingBlog.getId())
                .description(existingBlog.getDescription())
                .date(existingBlog.getDate().toLocalDate())
                .createdBy(existingBlog.getCreatedBy())
                .build();
    }



    public Blog deleteBlog(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXIST));
        blog.setIsDeleted(true);
        blog.setStatus(EnumStatus.INACTIVE);
        return blogRepository.save(blog);
    }

    public BlogResponse getBlogById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXIST));
        return BlogResponse.builder()
                .date(blog.getDate().toLocalDate())
                .blogName(blog.getBlogName())
                .content(blog.getContent())
                .image(blog.getImage())
                .status(blog.getStatus())
                .id(blog.getId())
                .description(blog.getDescription())
                .build();
    }

    public List<Blog> getALlBlog() {
        List<Blog> list = blogRepository.findAll()
                .stream()
                .filter(b -> b.getStatus().equals(EnumStatus.ACTIVE))
                .toList();
        return list;
    }
    public PageResponse<BlogResponse> searchBlogs(String key, int page, int size) {
        // Xử lý key null hoặc rỗng
        if (key == null || key.trim().isEmpty()) {
            key = "";
        }

        // Đảm bảo page và size hợp lệ
        if (page < 0) {
            page = 0;
        }
        if (size < 1) {
            size = 10; // Mặc định 5 bài viết mỗi trang
        }

        // Tạo Pageable cho phân trang, sắp xếp theo createdAt giảm dần
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        // Tìm kiếm trong repository
        Page<Blog> blogPage = blogRepository.findByBlogNameContainingIgnoreCase(key, pageable);

        // Chuyển đổi Blog sang BlogResponse
        List<BlogResponse> blogResponses = blogPage.getContent().stream()
                .map(blog -> BlogResponse.builder()
                        .id(blog.getId())
                        .content(blog.getContent())
                        .content(blog.getContent())
                        .status(blog.getStatus())
                        .build())
                .collect(Collectors.toList());

        // Tạo PageResponse
        return new PageResponse<>(
                blogResponses,
                blogPage.getNumber(),
                blogPage.getTotalPages(),
                blogPage.getTotalElements()
        );
    }

    public void changeStatus(Long id, EnumStatus status) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_EXIST));
        if(blog.getStatus() == EnumStatus.ACTIVE){
            blog.setStatus(EnumStatus.INACTIVE);
        }else{
            blog.setStatus(EnumStatus.ACTIVE);
        }
        blogRepository.save(blog);
    }

}

