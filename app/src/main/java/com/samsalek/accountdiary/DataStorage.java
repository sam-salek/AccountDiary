package com.samsalek.accountdiary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStorage implements Serializable {

    private static final long serialVersionUID = 1L;

    private final transient char[] swedishAlphabet;
    private final Map<Character, List<Account>> accountMap = new HashMap<>();

    public DataStorage(char[] swedishAlphabet) {
        this.swedishAlphabet = swedishAlphabet;
        initAccountMap(swedishAlphabet);
    }

    private void initAccountMap(char[] swedishAlphabet) {
        for (char c : swedishAlphabet)
            accountMap.put(c, new ArrayList<>());
    }

    public Map<Character, List<Account>> getAccountMap() {
        return accountMap;
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

        return accountMap.get(group);
    }

    public List<Account> getAccounts(int groupIndex) {
        if (groupIndex < 0 || groupIndex >= swedishAlphabet.length) {
            throw new IndexOutOfBoundsException();
        }

        return accountMap.get(swedishAlphabet[groupIndex]);
    }
}
