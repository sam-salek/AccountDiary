package com.samsalek.accountdiary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AccountDiary {

    private final int numLetters = 29;
    private final char[] swedishAlphabet = new char[numLetters];

    private final DataStorage dataStorage;
    private final Map<Character, List<Account>> accountMap;

    private AccountDiary() {
        populateAlphabet();
        dataStorage = new DataStorage(swedishAlphabet);
        accountMap = dataStorage.getAccountMap();
    }

    private static class SingletonHolder {
        private static final AccountDiary INSTANCE = new AccountDiary();
    }

    public static AccountDiary get() {
        return SingletonHolder.INSTANCE;
    }

    private void populateAlphabet() {
        int index = 0;
        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            swedishAlphabet[index] = alphabet;
            index++;
        }

        swedishAlphabet[index++] = 'å';
        swedishAlphabet[index++] = 'ä';
        swedishAlphabet[index] = 'ö';
    }

    public byte[] dataToBytes() {

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(dataStorage);
            out.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }

    public char[] getSwedishAlphabet() {
        return swedishAlphabet;
    }

    public Map<Character, List<Account>> getAccountMap() {
        return Collections.unmodifiableMap(accountMap);
    }

    public void addAccount(Account account) {
        dataStorage.addAccount(account);
    }

    public List<Account> getAccounts(char group) {
        return Collections.unmodifiableList(dataStorage.getAccounts(group));
    }

    public List<Account> getAccounts(int groupIndex) {
        return Collections.unmodifiableList(dataStorage.getAccounts(groupIndex));
    }

    public Account getAccount(int groupIndex, int childIndex) {
        return getAccounts(groupIndex).get(childIndex);
    }

    public boolean accountExists(Account account) {
        char group = account.getName().toLowerCase().charAt(0);     // Get group where this account would exist
        for (Account a : dataStorage.getAccounts(group)) {
            if (a.getId().equals(account.getId())) return true;
        }
        return false;
    }

    public boolean accountExists(String accountName) {
        char group = accountName.toLowerCase().charAt(0);       // Get group where this account would exist
        for (Account a : dataStorage.getAccounts(group)) {
            if (a.getId().equals(Account.nameToId(accountName))) return true;
        }
        return false;
    }
}
