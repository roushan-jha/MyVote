package com.voting.voteSys.service;

import com.voting.voteSys.Repository.PollRepository;
import com.voting.voteSys.model.Poll;
import org.springframework.stereotype.Service;

@Service
public class PollService {

    private final PollRepository pollRepository;

    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public Poll createPoll(Poll poll) {
        return pollRepository.save(poll);
    }
}
