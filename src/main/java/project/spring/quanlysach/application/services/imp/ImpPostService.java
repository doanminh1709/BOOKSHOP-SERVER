package project.spring.quanlysach.application.services.imp;

import com.github.slugify.Slugify;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.mapper.PostMapper;
import project.spring.quanlysach.application.repo.BrandRepository;
import project.spring.quanlysach.application.repo.CateRepository;
import project.spring.quanlysach.application.repo.PostRepository;
import project.spring.quanlysach.application.repo.TagRepository;
import project.spring.quanlysach.application.services.IPostService;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.dto.PostDTO;
import project.spring.quanlysach.domain.entity.Brand;
import project.spring.quanlysach.domain.entity.Cate;
import project.spring.quanlysach.domain.entity.Post;
import project.spring.quanlysach.domain.entity.Tag;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImpPostService implements IPostService {
    private final PostRepository postRepository;
    private final CateRepository cateRepository;
    private final BrandRepository brandRepository;
    private final TagRepository tagRepository;
    private final Slugify slugify;
    private final PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    public ImpPostService(PostRepository postRepository, CateRepository cateRepository, BrandRepository brandRepository, TagRepository tagRepository, Slugify slugify) {
        this.postRepository = postRepository;
        this.cateRepository = cateRepository;
        this.brandRepository = brandRepository;
        this.tagRepository = tagRepository;
        this.slugify = slugify;
    }

    @Override
    public List<Post> listPost(Integer page, Integer size) {
        List<Post> listPost;
        if (page != null) {
            listPost = postRepository.findAll(PageRequest.of(page.intValue(), size)).getContent();
        } else {
            listPost = postRepository.findAll();
        }
        return listPost;
    }

    @Override
    public List<Post> filterPostByBrand(int brandId) {
        List<Post> foundPost = postRepository.findByBrandId(brandId);
        if (foundPost == null) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "post", brandId));
        } else {
            return foundPost;
        }
    }

    @Override
    public List<Post> filterPostByCate(String cateName) {
        Cate foundCate = cateRepository.findByName(cateName);
        if (foundCate != null) {
            List<Post> post = postRepository.findPostByCateId(foundCate.getId());
            if (post.size() > 0) {
                return post;
            } else {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT, String.format(DevMessageConstant.Common.EMPTY));
            }
        } else {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT, String.format(DevMessageConstant.Common.EMPTY));
        }
    }

    @Override
    public List<Post> showPostByLeastTime(Integer page, Integer size) {
        List<Post> listPosts;
        if (page != null) {
            //Use method 'Comparator.comparing() to soft 'POST' order by Desc , then
            // use 'Reverse' method to reverse order by posts
            listPosts = postRepository.findAll(PageRequest.of(page.intValue(), size))
                    .getContent().stream()
                    .sorted(Comparator.comparing(Post::getCreateDate).reversed())
                    .collect(Collectors.toList());
        } else {
            listPosts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
        }
        return listPosts;
    }

    @Override
    public Post createNewPost(PostDTO postDTO) {
        Post findPostByName = postRepository.findByName(postDTO.getName());
        if (findPostByName != null) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.DUPLICATE_NAME, findPostByName.getName()));
        }
        Optional<Brand> foundBrand = brandRepository.findById(postDTO.getBrandId());
        Optional<Cate> foundCate = cateRepository.findById(postDTO.getCateId());
        Optional<Tag> foundTag = tagRepository.findById(postDTO.getTagId());

        if (foundBrand.isEmpty() && foundCate.isEmpty() && foundTag.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
        } else {
            if (foundBrand.isEmpty() || foundCate.isEmpty() || foundTag.isEmpty()) {
                if (foundBrand.isEmpty()) {
                    throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                            String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "brand", postDTO.getBrandId()));
                }
                if (foundCate.isEmpty()) {
                    throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                            String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "cate", postDTO.getCateId()));
                }
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "tag", postDTO.getTagId()));
            } else {
                Post newPost = postMapper.toPost(postDTO, postDTO.getCateId(), postDTO.getBrandId(), postDTO.getTagId());
                newPost.setSeoTitle(slugify.slugify(postDTO.getSeoTitle()));
                return postRepository.save(newPost);
            }
        }
    }

    //If find object , but it duplicates name with other object not object present then shoot out error duplicate name
    @Override
    public String updatePostById(PostDTO postDTO, Integer id) {
        Optional<Post> foundPost = postRepository.findById(id);
        if (foundPost.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "post", id));
        } else {
            Optional<Brand> foundBrand = brandRepository.findById(postDTO.getBrandId());
            Optional<Cate> foundCate = cateRepository.findById(postDTO.getCateId());
            Optional<Tag> foundTag = tagRepository.findById(postDTO.getTagId());

            if (foundBrand.isEmpty() && foundCate.isEmpty() && foundTag.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
            } else {
                if (foundBrand.isEmpty() || foundCate.isEmpty() || foundTag.isEmpty()) {
                    if (foundBrand.isEmpty()) {
                        throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                                String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "brand", postDTO.getBrandId()));
                    }
                    if (foundCate.isEmpty()) {
                        throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                                String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "cate", postDTO.getCateId()));
                    }
                    throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                            String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "tag", postDTO.getTagId()));
                } else {
                    foundPost = Optional.ofNullable(postMapper.toPost(postDTO, postDTO.getCateId(), postDTO.getBrandId(), postDTO.getTagId()));
                    foundPost.get().setId(id);
                    foundPost.get().setSeoTitle(slugify.slugify(postDTO.getSeoTitle()));
                    postRepository.save(foundPost.get());
                    return "Update post by id successfully";
                }
            }
        }
    }

    @Override
    public Post getPostById(int id) {
        Optional<Post> foundPost = postRepository.findById(id);
        if (foundPost.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "post", id));
        }
        return foundPost.get();
    }

    @Override
    public String deletePostById(Integer id) {
        Optional<Post> foundPost = postRepository.findById(id);
        if (foundPost.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "post", id));
        }
        postRepository.deleteById(id);
        return "Delete post by id successfully";
    }

    @Override
    public List<Post> filterPostOfBrandAboutCateId(int brandId, int cateId) {
        Optional<Brand> foundBrand = brandRepository.findById(brandId);
        Optional<Cate> foundCate = cateRepository.findById(cateId);
        if (foundBrand.isEmpty() && foundCate.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.OBJECT_NOT_FOUND));
        } else {
            if (foundBrand.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "brand", brandId));
            }
            if (foundCate.isEmpty()) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "cate", cateId));
            }
            return postRepository.findPostByBrandIdAndCateId(brandId, cateId);
        }
    }
}
