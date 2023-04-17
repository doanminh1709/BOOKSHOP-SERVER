package project.spring.quanlysach.application.auth_service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.repo.ConfirmationTokenRepository;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.entity.ConfirmationToken;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmRepo;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmRepo.save(confirmationToken);
    }

    public ConfirmationToken getByToken(String token) {
        ConfirmationToken confirmationToken = confirmRepo.findByToken(token);
        if (confirmationToken == null) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    DevMessageConstant.Common.NO_DATA_SELECTED);
        }
        return confirmationToken;
    }

    public int setConfirmationAt(String token) {
        return confirmRepo.updateConfirmationAt(LocalDateTime.now(), token);
    }
}
