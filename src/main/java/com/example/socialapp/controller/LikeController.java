package com.example.socialapp.controller;
import com.example.socialapp.dto.LikeSummaryDto;
import com.example.socialapp.dto.PostDto;
import com.example.socialapp.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;


@Tag(name = "Likes", description = "Operations related to post likes")
@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired private LikeService likeService;

    //Like a post
    @Operation(summary = "Like a post")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post liked"),
            @ApiResponse(responseCode = "404", description = "Post or user not found")
    })
    @PostMapping("/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable Long postId, Principal principal) {
        likeService.likePost(postId, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //  dislike a post
    @Operation(summary = "Dislike a post")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Like removed"),
            @ApiResponse(responseCode = "404", description = "Post or like not found")
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> dislikePost(@PathVariable Long postId, Principal principal) {
        likeService.dislikePost(postId, principal.getName());
        return ResponseEntity.noContent().build();
    }

    //get like for post
    @Operation(summary = "Get liked posts (paginated)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "List of liked posts"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @GetMapping
    public ResponseEntity<Page<PostDto>> getLikedPosts(Pageable pageable, Principal principal) {
        Page<PostDto> likedPosts = likeService.getLikedPosts(principal.getName(), pageable);
        return ResponseEntity.ok(likedPosts);
    }

    //get like summary for post
    @Operation(summary = "Get like summary for post")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Post Like Count"),
            @ApiResponse(responseCode = "404", description = "Post Like not found")
    })
    @GetMapping("/summary/{postId}")
    public ResponseEntity<LikeSummaryDto> getLikeSummary(@PathVariable Long postId, Principal principal) {
        return ResponseEntity.ok(likeService.getPostLikeSummary(postId, principal.getName()));
    }
}
