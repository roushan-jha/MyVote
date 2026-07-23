package com.voting.voteSys.controller;

import com.voting.voteSys.model.Poll;
import com.voting.voteSys.model.User;
import com.voting.voteSys.Repository.UserRepository;
import com.voting.voteSys.request.Vote;
import com.voting.voteSys.service.PollService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/polls")
public class PollController {

    private final PollService pollService;
    private final UserRepository userRepository;

    public PollController(PollService pollService, UserRepository userRepository) {
        this.pollService = pollService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public Poll createPoll(@RequestBody Poll poll) {
        User user = getCurrentUser();
        return pollService.createPoll(poll, user);
    }

    @GetMapping
    public List<Poll> getPolls() {
        return pollService.getAllPolls();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poll> getPoll(@PathVariable Long id) {
        return pollService.getPollById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/vote")
    public void vote(@RequestBody Vote vote) {
        pollService.vote(vote.getPollId(), vote.getOptionIndex());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoll(@PathVariable Long id) {
        User user = getCurrentUser();
        boolean deleted = pollService.deletePoll(id, user);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
