package com.samsalek.accountdiary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDiary {

    private final int numLetters = 29;
    private final char[] swedishAlphabet = new char[numLetters];
    private final Map<Character, List<Account>> accountMap = new HashMap<>();

    private AccountDiary() {
        populateAlphabet();
        initAccountMap();
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

    private void initAccountMap() {
        for (char c : swedishAlphabet)
            accountMap.put(c, new ArrayList<>());
    }

    public char[] getSwedishAlphabet() {
        return swedishAlphabet;
    }

    public Map<Character, List<Account>> getAccountMap() {
        return Collections.unmodifiableMap(accountMap);
    }

    public void addAccount(Account account) {
        char accountGroup = account.getName().toLowerCase().charAt(0);
        if (!accountMap.containsKey(accountGroup))
            throw new IllegalArgumentException("No such key in map!");

        accountMap.get(accountGroup).add(account);
    }

    public List<Account> getAccounts(char group) {
        if (!accountMap.containsKey(group))
            throw new IllegalArgumentException("No such key in map!");

        return Collections.unmodifiableList(accountMap.get(group));
    }

    public List<Account> getAccounts(int groupIndex) {
        if (groupIndex < 0 || groupIndex >= numLetters) {
            throw new IndexOutOfBoundsException();
        }

        return Collections.unmodifiableList(accountMap.get(swedishAlphabet[groupIndex]));
    }

    public Account getAccount(int groupIndex, int childIndex) {
        return getAccounts(groupIndex).get(childIndex);
    }
}
