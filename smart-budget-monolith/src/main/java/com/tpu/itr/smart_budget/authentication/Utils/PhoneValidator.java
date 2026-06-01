package com.tpu.itr.smart_budget.authentication.Utils;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class PhoneValidator {

    private static final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    public static boolean isValid(String phone, String region) {
        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(phone, region);
            return phoneUtil.isValidNumber(number);
        } catch (Exception e) {
            return false;
        }
    }
}