package com.voting.voteSys.service;

import com.voting.voteSys.Repository.PollRepository;
import com.voting.voteSys.model.OptionVote;
import com.voting.voteSys.model.Poll;
import com.voting.voteSys.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    private final PollRepository pollRepository;

    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Poll createPoll(Poll poll, User user) {
        poll.setCreatedBy(user);
        return pollRepository.save(poll);
    }

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Optional<Poll> getPollById(Long id) {
        return pollRepository.findById(id);
    }

    public void vote(Long pollId, int optionIndex) {

        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Poll not found"));

        List<OptionVote> options = poll.getOptions();

        if(optionIndex < 0 || optionIndex >= options.size()) {
            throw new IllegalArgumentException("Invalid option index");
        }

        OptionVote selectedOption = options.get(optionIndex);
        selectedOption.setVoteCount(selectedOption.getVoteCount() + 1);

        pollRepository.save(poll);
    }

    public boolean deletePoll(Long id, User user) {
        Optional<Poll> pollOpt = pollRepository.findById(id);
        if (pollOpt.isEmpty()) {
            return false;
        }
        Poll poll = pollOpt.get();
        if (!poll.getCreatedBy().getId().equals(user.getId())) {
            throw new RuntimeException("You can only delete your own polls");
        }
        pollRepository.deleteById(id);
        return true;
    }
}
