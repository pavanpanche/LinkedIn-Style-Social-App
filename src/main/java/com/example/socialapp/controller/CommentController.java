package com.example.socialapp.controller;
import com.example.socialapp.dto.CommentDto;
import com.example.socialapp.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Tag(name = "6-Comments", description = "Comments on post")
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Write a comment on post
    @Operation(summary = "Add a comment to a post")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Comment added successfully"),
            @ApiResponse(responseCode = "404", description = "Post or user not found")
    })
    @PostMapping("/{postId}")
    public ResponseEntity<CommentDto> addComment(@Valid @PathVariable Long postId, @RequestBody CommentDto commentDto, Principal principal) {
        CommentDto created = commentService.addComment(postId, principal.getName(), commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //edit the post
    @Operation(summary = "Update a comment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "403", description = "Unauthorized update attempt")
    })
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto, Principal principal) {
        CommentDto updated = commentService.updateComment(commentId, principal.getName(), commentDto);
        return ResponseEntity.ok(updated);
    }

    //Delete comment from post
    @Operation(summary = "Delete a comment")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Comment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "403", description = "Unauthorized delete attempt")
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, Principal principal) {
        commentService.deleteComment(commentId, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all comments on a post (no pagination)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId));
    }

    // View Per Page with limitation
    @Operation(summary = "Get comments on a post (paginated)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @GetMapping("/post/{postId}/paged")
    public ResponseEntity<Page<CommentDto>> getPagedCommentsForPost(@PathVariable Long postId, Pageable pageable) {
        return ResponseEntity.ok(commentService.getCommentsByPost(postId, pageable));
    }
}
