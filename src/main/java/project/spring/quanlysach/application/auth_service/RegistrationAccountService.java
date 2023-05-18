package project.spring.quanlysach.application.auth_service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.spring.quanlysach.application.constants.DevMessageConstant;
import project.spring.quanlysach.application.constants.EnumRole;
import project.spring.quanlysach.application.constants.UserMessConstant;
import project.spring.quanlysach.application.repo.ConfirmationTokenRepository;
import project.spring.quanlysach.application.repo.CustomerRepository;
import project.spring.quanlysach.application.validation.EmailValidation;
import project.spring.quanlysach.config.RandomPassword;
import project.spring.quanlysach.config.exception.VsException;
import project.spring.quanlysach.domain.dto.CustomerDTO;
import project.spring.quanlysach.domain.entity.ConfirmationToken;
import project.spring.quanlysach.domain.entity.Customer;
import project.spring.quanlysach.domain.entity.Role;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationAccountService {

    private final ConfirmationTokenRepository confirmRepo;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService emailSender;
    private final RandomPassword randomPassword;
    private final EmailValidation emailValidation;

    public RegistrationAccountService(ConfirmationTokenRepository confirmRepo,
                                      CustomerRepository customerRepository,
                                      PasswordEncoder passwordEncoder,
                                      MailService emailSender,
                                      RandomPassword randomPassword,
                                      EmailValidation emailValidation) {
        this.confirmRepo = confirmRepo;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
        this.randomPassword = randomPassword;
        this.emailValidation = emailValidation;
    }

    //register

    @Transactional
    public String register(CustomerDTO customerDTO) throws ParseException {
        boolean checkEmail = emailValidation.test(customerDTO.getEmail());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        if (!checkEmail) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.EMAIL_NOT_VALID, customerDTO.getEmail()));
        } else {
            boolean checkEmailExits = customerRepository.existsByEmail(customerDTO.getEmail());
            boolean checkUsernameExits = customerRepository.existsByUsername(customerDTO.getUsername());
            if (checkEmailExits && checkUsernameExits) {
                Customer customer = customerRepository.findCustomerByUsernameAndEmail(customerDTO.getUsername(),
                        customerDTO.getEmail());
                if (customer != null) {
                    if (customer.isEnable() == Boolean.TRUE) {
                        throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                                DevMessageConstant.Common.OBJECT_IS_EXITS);
                    } else {
                        ConfirmationToken confirmationToken = confirmRepo.findByCustomer(customer);
                        if (confirmationToken.getExpressAt().isBefore(LocalDateTime.now())) {
                            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                                    DevMessageConstant.Common.CONFIRM_ACCOUNT);
                        } else {
                            confirmRepo.deleteByCustomer(customer);
                            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                                    DevMessageConstant.Common.TOKEN_IS_EXPRESS);
                        }
                    }
                }
            } else {
                if (!checkEmailExits && !checkUsernameExits) {
                    Customer newCustomer = new Customer(
                            customerDTO.getFullName(),
                            customerDTO.getPhone(),
                            customerDTO.getAddress(),
                            customerDTO.getEmail(),
                            sdf.parse(customerDTO.getBirthday()),
                            customerDTO.getUsername(),
                            passwordEncoder.encode(customerDTO.getPassword())
                    );
                    //Define when register will set role is User
                    List<Role> roles = new ArrayList<>();
                    Role role = new Role(1, EnumRole.ROLE_USER);
                    roles.add(role);
                    newCustomer.setRoles(roles);
                    customerRepository.save(newCustomer);
                    String token = generateConfirmToken(newCustomer);
                    String link = "http://localhost:8080/api/v0/no_auth/confirm?token=" + token;
                    //Sent a link confirm account to email
                    emailSender.sent(customerDTO.getEmail(), buildEmail(customerDTO.getFullName(), link));
                    return DevMessageConstant.Common.REGISTER_SUCCESS;
                } else if (!checkEmailExits) {
                    throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                            String.format(DevMessageConstant.Common.EXITS_USERNAME, customerDTO.getUsername()));
                } else {
                    //Email ton tai , nhưng username không tồn tại ,được tại 1 tài khoản mới vì 1 email có thể tạo nhiều tại khoản
                    Customer newCustomer = new Customer(
                            customerDTO.getFullName(),
                            customerDTO.getPhone(),
                            customerDTO.getAddress(),
                            customerDTO.getEmail(),
                            sdf.parse(customerDTO.getBirthday()),
                            customerDTO.getUsername(),
                            passwordEncoder.encode(customerDTO.getPassword())
                    );
                    List<Role> roles = new ArrayList<>();
                    Role role = new Role(1, EnumRole.ROLE_USER);
                    roles.add(role);
                    newCustomer.setRoles(roles);
                    customerRepository.save(newCustomer);
                    String token = generateConfirmToken(newCustomer);
                    String link = "http://localhost:8080/api/v0/no_auth/confirm?token=" + token;
                    emailSender.sent(customerDTO.getEmail(), buildEmail(customerDTO.getFullName(), link));
                    return DevMessageConstant.Common.REGISTER_SUCCESS;
                }
            }
        }
        return DevMessageConstant.Common.REGISTER_FAILED;
    }

    //Confirm token
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmRepo.findByToken(token);
        if (confirmationToken == null) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    DevMessageConstant.Common.NOT_FOUND_CONFIRM_TOKEN);
        }
        //Check confirmation token expires
        if (confirmationToken.getConfirmAt() != null) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    DevMessageConstant.Common.CONFIRMED);
        } else {
            if (confirmationToken.getExpressAt().isBefore(LocalDateTime.now())) {
                throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                        DevMessageConstant.Common.TOKEN_EXPIRED);
            }
        }
        confirmationToken.setConfirmAt(LocalDateTime.now());
        enableAccountCustomer(confirmationToken.getCustomer().getId());
        return DevMessageConstant.Common.CONFIRM_SUCCESSFUL;
    }

    //register customer return link confirm
    public String generateConfirmToken(Customer customer) {
        //Random ra 1 token
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken =
                new ConfirmationToken(token, LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        customer);
        confirmRepo.save(confirmationToken);
        return token;
    }

    //Forgot password
    @Transactional
    public String getForgotPassword(int customerId) throws MessagingException {
        Optional<Customer> foundCustomer = customerRepository.findById(customerId);
        if (foundCustomer.isEmpty()) {
            throw new VsException(UserMessConstant.ERR_NO_DATA_RESULT,
                    String.format(DevMessageConstant.Common.NOT_FOUND_OBJECT_BY_ID, "customer", customerId));
        }
        String newPassword = randomPassword.randomPassword(10);
        emailSender.sendNewPassword(foundCustomer.get().getEmail(), newPassword);
        foundCustomer.get().setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(foundCustomer.get());
        return DevMessageConstant.Common.GET_PASSWORD;
    }

    //Enable account customer
    public void enableAccountCustomer(int customerId) {
        customerRepository.enableUser(customerId);
    }

    //Build email
    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;" +
                "width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" " +
                "cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" " +
                "style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;" +
                "text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" " +
                "cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" " +
                "width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"" +
                " style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" " +
                "cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" " +
                "width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;" +
                "max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p" +
                "><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. " +
                "Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;" +
                "border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p " +
                "style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate" +
                " Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
