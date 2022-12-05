package com.samsalek.accountdiary;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SerializationHandler {

    private static final AccountDiary accountDiary = AccountDiary.get();

    public static boolean saveData(FileOutputStream fileOutputStream) {
        try {
            fileOutputStream.write(accountDiary.dataToBytes());
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean loadData(FileInputStream fileInputStream) {
        DataStorage loadedDataStorage = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            loadedDataStorage = (DataStorage) ois.readObject();
            ois.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (loadedDataStorage != null) {
            for (Character c : loadedDataStorage.getAccountMap().keySet()) {
                for (Account loadedAccount : loadedDataStorage.getAccounts(c)) {

                    // Don't add account if it already exists in storage
                    if (accountDiary.accountExists(loadedAccount)) continue;
                    accountDiary.addAccount(loadedAccount);
                }
            }
            return true;
        } else
            return false;
    }
}
