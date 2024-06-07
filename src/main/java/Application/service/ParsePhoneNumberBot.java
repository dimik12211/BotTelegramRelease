package Application.service;

import org.springframework.stereotype.Service;

import java.io.StringReader;

@Service
public class ParsePhoneNumberBot {

    private String num = "+0123456789";

    public Boolean parseMessage(String phoneNumber) {
        if (!(phoneNumber.length() >= 11 && phoneNumber.length() <= 16)) {
            return false;
        }
        char[] phone = phoneNumber.toCharArray();
        for (int i = 0; i < phone.length; i++) {
            int j = num.indexOf(phone[i]);
            if (j == -1) {
                return false;
            }
        }
        return true;
    }
}
