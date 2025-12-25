package peaksoft.instaproject.service;

import peaksoft.instaproject.dto.SimpleResponse;
import peaksoft.instaproject.dto.postDTO.request.PostRequest;
import peaksoft.instaproject.dto.postDTO.response.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse createPost(Long userID, PostRequest postRequest);
    PostResponse updatePost(Long postId, PostRequest postRequest);
    List<PostResponse> getUserFeed(Long userId);
    SimpleResponse deletePost(Long userId, Long postId);

    List<PostResponse> instFeed(Long userId);
}
