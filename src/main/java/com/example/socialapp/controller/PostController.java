package com.example.socialapp.controller;
import com.example.socialapp.dto.PostDto;
import com.example.socialapp.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(summary = "Create a new post")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Post created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🔐 Authenticated user: " + auth.getName());
        System.out.println("🔐 Authorities: " + auth.getAuthorities());

        PostDto createdPost = postService.createPost(postDto, principal.getName());
        URI location = URI.create("/api/posts/" + createdPost.getId());
        return ResponseEntity.created(location).body(createdPost);
    }

    @Operation(summary = "Update an existing post")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post updated successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @RequestBody @Valid PostDto postDto, Principal principal) {
        return ResponseEntity.ok(postService.updatePost(postId, postDto, principal.getName()));
    }

    @Operation(summary = "Delete a post")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, Principal principal) {
        postService.deletePost(postId, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a post by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @Operation(summary = "Get home feed (paginated)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Feed retrieved successfully")
    })
    @GetMapping("/feed")
    public ResponseEntity<Page<PostDto>> getHomeFeed(Pageable pageable) {
        return ResponseEntity.ok(postService.getHomeFeed(pageable));
    }

    @Operation(summary = "Search posts by keyword")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Matching posts retrieved successfully")
    })
    @GetMapping("/search")
    public ResponseEntity<List<PostDto>> searchPosts(@RequestParam String keyword) {
        return ResponseEntity.ok(postService.searchPosts(keyword));
    }

    @Operation(summary = "Get posts sorted by field and order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sorted posts retrieved successfully")
    })
    @GetMapping("/sorted")
    public ResponseEntity<Page<PostDto>> getSortedPosts(
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String order,
            Pageable pageable
    ) {
        return ResponseEntity.ok(postService.getSortedPosts(sortBy, order, pageable));
    }

    @Operation(summary = "Get all posts (non-paginated)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All posts retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }
}
