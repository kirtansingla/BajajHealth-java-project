package com.example.bfhl.service.impl;

import com.example.bfhl.dto.BfhlRequest;
import com.example.bfhl.dto.BfhlResponse;
import com.example.bfhl.service.BfhlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    @Value("${bfhl.user.full-name}")
    private String fullName;

    @Value("${bfhl.user.dob}")
    private String dob;

    @Value("${bfhl.user.email}")
    private String email;

    @Value("${bfhl.user.roll-number}")
    private String rollNumber;

    @Override
    public BfhlResponse processRequest(BfhlRequest request) {
        if (request == null || request.getData() == null) {
            return buildErrorResponse("Request data cannot be null");
        }

        List<String> inputData = request.getData();
        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        BigInteger sumVal = BigInteger.ZERO;
        StringBuilder alphabeticCharsSb = new StringBuilder();

        for (String element : inputData) {
            if (element == null) {
                continue;
            }
            
            String trimmedElement = element.trim();

            if (isInteger(trimmedElement)) {
                try {
                    BigInteger val = new BigInteger(trimmedElement);
                    if (val.abs().remainder(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
                        evenNumbers.add(trimmedElement);
                    } else {
                        oddNumbers.add(trimmedElement);
                    }
                    sumVal = sumVal.add(val);
                } catch (NumberFormatException e) {
                    specialCharacters.add(element);
                }
            } else if (isAlphabetic(trimmedElement)) {
                alphabets.add(trimmedElement.toUpperCase());
                alphabeticCharsSb.append(trimmedElement);
            } else {
                specialCharacters.add(element);
            }
        }

        String concatString = processConcatString(alphabeticCharsSb.toString());

        String sanitizedFullName = fullName.toLowerCase().trim().replaceAll("\\s+", "_");
        String userId = sanitizedFullName + "_" + dob.trim();

        return new BfhlResponse(
                true,
                userId,
                email.trim(),
                rollNumber.trim(),
                oddNumbers,
                evenNumbers,
                alphabets,
                specialCharacters,
                sumVal.add(BigInteger.ONE).toString(),
                concatString
        );
    }

    private boolean isInteger(String str) {
        if (str.isEmpty()) {
            return false;
        }
        return str.matches("^[+-]?\\d+$");
    }

    private boolean isAlphabetic(String str) {
        if (str.isEmpty()) {
            return false;
        }
        return str.matches("^[a-zA-Z]+$");
    }

    private String processConcatString(String text) {
        if (text.isEmpty()) {
            return "";
        }
        String reversed = new StringBuilder(text).reverse().toString();

        StringBuilder resultSb = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (i % 2 != 0) {
                resultSb.append(Character.toUpperCase(c));
            } else {
                resultSb.append(Character.toLowerCase(c));
            }
        }
        return resultSb.toString();
    }

    private BfhlResponse buildErrorResponse(String message) {
        BfhlResponse response = new BfhlResponse();
        response.setSuccess(false);
        response.setSum("0");
        response.setConcatString("");
        response.setOddNumbers(new ArrayList<>());
        response.setEvenNumbers(new ArrayList<>());
        response.setAlphabets(new ArrayList<>());
        response.setSpecialCharacters(new ArrayList<>());
        return response;
    }
}
